package RPG;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.ArrayList;
import java.util.Random;
/**
 * A class of Monsters
 *
 * @invar   Each Hero must have a valid damage.
 *          | isValidDamage(getDamage())
 * @invar   Each creature must always have a valid natural protection.
 *          | isValidProtection(getProtection())
 * @author 	Wout Thiers & Bram Oreel
 * @version 1.0
 */
public class Monster extends Creature{

    /**
     * Generates a new monster.
     * @param name
     *        The name of the monster.
     * @param maxHitPoints
     *        The maximum amount of hitpoints the monster can have.
     * @param maxCapacity
     *        The maximum capacity the monster can have.
     * @param protectionType
     *        The type of natural armor the monster should have.
     * @param damage
     *        The damage stat of the monster.
     * @param nbofanchors
     *        The amount of anchors that the monster gets.
     * @param items
     *        Items that the monster needs to equip.
     * @pre   The amount of anchors nbofanchors must be equal or higher than the amount of items that are given.
     *        | nboofanchors => items.size()
     * @effect The Hero is generated as a creature with a given name, maxHitPoints and the given maxCapacity.
     *         | super(name, maxHitPoints, maxCapacity)
     * @effect A new armor with the given type is generated and set as the naturalProtection.
     *         The weight and ID of the new armor is set to 0 and the value to 1.
     *         | this.naturalProtection == new Armor(0,0, protectionType, 1 )
     * @effect A new weapon with the given damage value is generated with 0 weight and 1 value.
     *         | this.damage == new Weapon(0, damage, 1)
     * @effect The given amount of Anchors is initialized.
     *         | initialiseAnchors(nbofanchors)
     * @effect The items are equipped in a random anchor of the monster.
     *         |for(Equipable item : items)
     *         |   pickUp(item, AnchorType.OTHER)
     */
    @Raw
    public Monster(String name, int maxHitPoints, int maxCapacity, ArmorType protectionType, int damage, int nbofanchors, Equipable... items){
        super(name, maxHitPoints, maxCapacity);
        this.naturalProtection = new Armor(0,0, protectionType, 1 );
        this.damage = new Weapon(0, damage, 1);
        initialiseAnchors(nbofanchors);
        for(Equipable item : items){
            try {
                pickUp(item, AnchorType.OTHER);
            } catch (ItemAlreadyobtainedException e) {
                throw new RuntimeException(e);
            } catch (AnchorslotOccupiedException e) {
                throw new RuntimeException(e);
            } catch (CarryLimitReachedException e) {
                throw new RuntimeException(e);
            }

        }
    }


    /**
     * variable stating how much protection the monster has, how easily he can dodge or deflect attacks.
     */
    protected final Armor naturalProtection;
    /**
     * Variable stating how much damage the monster does.
     */
    protected final Weapon damage;

    /**
     * @return the natural protection of the monster.
     */
    @Basic @Immutable
    public Armor getNaturalProtection() {
        return naturalProtection;
    }

    /**
     * Checks if a given name is valid.
     * @param name
     *        the given name that gets checked.
     * @return True if the name is valid for a creature and the name does not contain a colon.
     *        | result == (super.canHaveAsName(name) && for character in name: character != ':')
     */
    @Raw @Override
    public boolean canHaveAsName(String name){
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == ':') {
                return false;
            }
        }
        return super.canHaveAsName(name);
    }

    /**
     * @return the natural damage of the monster
     */
    @Basic @Immutable
    public Weapon getDamage() {
        return damage;
    }

    /**
     * checks if the damage is valid
     * @param damage
     *        the damage that is a hidden weapon
     * @return if it is a weapon that is not null than true, false otherwise.
     *         |result == (damage != null)
     */
    @Raw
    public static boolean isValidDamage(Weapon damage){
        return damage != null;
    }

    /**
     * checks if the damage is valid
     * @param naturalProtection
     *        the natural Protection that is a hidden armor.
     * @return if it is a piece of armor that is not null then true, false otherwise.
     *         |result == (naturalProtection != null)
     */
    @Raw
    public static boolean isValidProtection(Armor naturalProtection){
        return naturalProtection != null;
    }

    /**
     * Gives a new list of empty anchors and sets it as the anchors of the monster.
     * @param nbofanchors
     *        The amount of anchors that the monster gets.
     * @effect Sets the given amount of new anchors with type OTHER and the owner as the Hero in a list as the anchors.
     *         |setAnchors(new ArrayList<Anchor>(nbofanchors * (new Anchor(AnchorType.OTHER,this)))
     */
    @Raw @Model
    private void initialiseAnchors(int nbofanchors){
        ArrayList<Anchor> list = new ArrayList<Anchor>();
        for(int i=0; i < nbofanchors; i++){
            list.add(new Anchor(AnchorType.OTHER,this));
        }
        setAnchors(list);
    }

    /**
     * Picks an item up and places it in an anchor.
     * @param item
     *        the item that will be picked up.
     * @throws IllegalArgumentException
     *         gets thrown if the item is a piece of armor
     *         |item instanceof Armor
     * @effect The item gets picked up and placed in an anchor of the monster.
     *         |super.pickUp(item,AnchorType.OTHER)
     */
    public void pickUp(Equipable item) throws IllegalArgumentException{
        if(item instanceof Armor)
            throw new IllegalArgumentException();
        try {
            super.pickUp(item,AnchorType.OTHER);
        } catch (ItemAlreadyobtainedException | CarryLimitReachedException | AnchorslotOccupiedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Gives the total damage that the monster does.
     * @return the damage stat of the hidden weapon.
     *         | result == getDamage().getDamage()
     */
    @Override
    public int getTotalDamage() {
        return getDamage().getDamage();
    }

    /**
     * Gives the total protection of the monster.
     * @return the current armor stat of the natural protection armor
     *         | result == getNaturalProtection().getCurrentArmor
     */
    @Override
    public int getTotalProtection() {
        return getNaturalProtection().getCurrentArmor();
    }
    /**
     * Generates a value to see if the attack will hit.
     * @return A random integer between 0 and 100 however if this integer however is higher than the current
     *         amount of hit points then the amount of hit points is returned.
     *         |int randomNum = rand.nextInt(101)
     *         |if randomNum > getHitPoints()
     *         |    then result == getHitPoints()
     *         |else result == randomNum
     */
    @Model @Override
    protected int getHitValue(){
        Random rand = new Random();
        int randomNum = rand.nextInt(101);
        if(randomNum > getHitPoints()){
            return getHitPoints();
        }
        return randomNum;
    }

    /**
     * Loots items from a list of items
     * @param items
     *        The items that can be looted.
     * @effect For every anchor it checks if there is an item with a higher shiny value then the item that
     *         is currently equipped if this is the case or if the anchor is empty than a new item gets equipped
     *         if the remaining capacity of the monster allows it. The first items in items will get used first and µ
     *         the first anchors in getAnchors() will get filled first. If the item that gets equipped was in a backpack it also gets
     *         removed.
     *         | for every item in items, for every anchor in getAnchors():
     *         |         if anchor.getItem() == null && item.getWeight() <= getCapacity()
     *         |         then item.equip(anchor) and item.getParentbackpack().removeEquipable(item)
     *         |         else if item.getShinyValue() > anchor.getItem().getShinyValue() && anchor.getItem().getWeight()-item.getWeight() <= getCapacity()
     *         |              then drop(anchor.getItem()) and item.equip(anchor) and item.getParentbackpack().removeEquipable(item)
     */
    @Override @Model
    protected void LootAndHeal(ArrayList<Equipable> items) {
        for(Equipable item: items){
            for(Anchor anchor : getAnchors()){
                if(anchor.getItem() == null && item.getWeight() <= getCapacity()){
                        item.equip(anchor);
                    if(item.getParentbackpack() != null){
                        item.getParentbackpack().removeEquipable(item);
                    }
                    break;
                }
                else if(item.getShinyValue() > anchor.getItem().getShinyValue() && anchor.getItem().getWeight()-item.getWeight() <= getCapacity()){
                        try {
                            drop(anchor.getItem());
                        } catch (OtherPlayersItemException e) {
                            throw new RuntimeException(e);
                        }
                        if(item.getParentbackpack() != null){
                            item.getParentbackpack().removeEquipable(item);
                        }
                        item.equip(anchor);
                        break;
                }
            }
        }
    }
}
