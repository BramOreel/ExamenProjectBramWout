package RPG;

import java.util.ArrayList;
import java.util.Random;

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
     * @effect The Hero is generated as a creature with a given name, maxHitPoints and the given maxCapacity.
     *         | super(name, maxHitPoints, maxCapacity)
     * @effect A new armor with the given type is generated and set as the naturalProtection.
     *         The weight, value and ID of the new armor is set to 0.
     *         | this.naturalProtection == new Armor(0,0, protectionType, 0 )
     * @effect A new weapon with the given damage value is generated with 0 weight and 0 value.
     *         | this.damage == new Weapon(0, damage, 0)
     * @effect The given amount of Anchors are initialized.
     *         | initialiseAnchors(nbofanchors)
     * @effect The items are equiped in a random anchor of the monster.
     *         |for(Equipable item : items)
     *         |   item.equip(this)
     */
    public Monster(String name, int maxHitPoints, int maxCapacity, ArmorType protectionType, int damage, int nbofanchors, Equipable... items) throws ItemAlreadyobtainedException, CarryLimitReachedException,AnchorslotOquipiedException {
        super(name, maxHitPoints, maxCapacity);
        this.naturalProtection = new Armor(0,0, protectionType, 0 );
        this.damage = new Weapon(0, damage, 0);
        initialiseAnchors(nbofanchors);
        for(Equipable item : items){
            pickUp(item, AnchorType.OTHER);
        }
    }


    /**
     * variable stating how much protection the monster has, how easily he can dodge or deflect attacks.
     */
    protected Armor naturalProtection;
    /**
     * Variable stating how much damage the monster does.
     */
    protected Weapon damage;

    /**
     * @return the natural protection of the monster.
     */

    public Armor getNaturalProtection() {
        return naturalProtection;
    }

    /**
     * @return the natural damage of the monster
     */

    public Weapon getDamage() {
        return damage;
    }

    /**
     * Sets a new given armor as the natural protection.
     * @param naturalProtection
     *        the armor that acts as the natural protection.
     */
    protected void setNaturalProtection(Armor naturalProtection) {
        this.naturalProtection = naturalProtection;
    }

    /**
     * Sets a new damage stat.
     * @param damage
     *        the new damage stat.
     */

    protected void setDamage(Weapon damage) {
        this.damage = damage;
    }

    /**
     * Gives a new list of empty anchors and sets it as the anchors of the monster.
     * @param nbofanchors
     *        The amount of anchors that the monster gets.
     * @effect Sets the given amount of new anchors with type OTHER and the owner as the Hero in a list as the anchors.
     *          | ArrayList<Anchor> list = new ArrayList<Anchor>()
     *          | for(int i=0; i < nbofanchors; i++)
     *          |     list.add(new Anchor(AnchorType.OTHER,this))
     *          |setAnchors(list)
     */
    private void initialiseAnchors(int nbofanchors){
        ArrayList<Anchor> list = new ArrayList<Anchor>();
        for(int i=0; i < nbofanchors; i++){
            list.add(new Anchor(AnchorType.OTHER,this));
        }
        setAnchors(list);
    }

    @Override
    public void pickUp(Equipable item, AnchorType anchortype) throws ItemAlreadyobtainedException,IllegalArgumentException,
            AnchorslotOquipiedException, CarryLimitReachedException, BeltAnchorException{
        if(anchortype.getName() == "Lichaam" && item instanceof Armor)
            throw new IllegalArgumentException();
        super.pickUp(item,anchortype);
    }

    @Override
    protected void LootAndHeal(ArrayList<Equipable> items) {
        for(Anchor anchor: getAnchors()){
            for(Equipable item: items){
                if(item.getShinyValue() > anchor.getItem().getShinyValue()){
                    if(anchor.getItem().getWeight()-item.getWeight() <= getCapacity()) {
                        try {
                            drop(anchor.getItem());
                        } catch (OtherPlayersItemException e) {
                            throw new RuntimeException(e);
                        }
                        item.equip(anchor);
                    }
                }
            }
        }
    }
}
