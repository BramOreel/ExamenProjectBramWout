package RPG;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import be.kuleuven.cs.som.annotate.Raw;

import java.text.DecimalFormat;

public class Hero extends Creature{

    /**
     * Generates a new Hero with a name, a maximum amount of hitpoints, a given strength stat and a given protetection stat.
     * Also initialises five Anchors of the anchor class
     *
     * @param name
     *        The given name of the new Hero
     * @param maxHitPoints
     *        The given maximum amount of hitpoints the Hero can have
     * @param strength
     *        The given strength the hero has
     * @param protection
     *        The given protection stat the hero has.
     *@effect The maxcapacity is calculated with the given strength and then set as the strength.
     *        |setMaxCapacity(calculateMaxCapacity(strength));
     *@effect The Hero is generated as a creature with a given name, maxHitPoints and the calculated maxCapacity.
     *        | super(name, maxHitPoints, maxCapacity)
     *@effect The protection is set as the protection.
     *        | setProtection(protection)
     *@effect The strength is set as the strength.
     *        | setStrength(strength)
     *@effect five empty anchors are initialised and set as the anchors of this hero. One left hand, One right hand, one back, one chest and one belt.
     *        |initialiseAnchors();
     */

    public Hero(String name, int maxHitPoints, double strength, int protection) {
        super(name, maxHitPoints, (int) (20*strength));
        setMaxCapacity(calculateMaxCapacity(strength));
        setProtection(protection);
        setStrength(strength);
        initialiseAnchors();
    }

    /**
     * Generates a new hero with items equiped.
     * @param name
     *        The given name of the new Hero
     * @param maxHitPoints
     *        The given maximum amount of hitpoints the Hero can have
     * @param strength
     *        The given strength the hero has
     * @param protection
     *        The given protection stat the hero has.
     * @param items
     *        The given items that the Hero has to equip.
     * @effect The Hero gets generated with the given name, maxhipoints, strength and protection.
     *        | this(name, maxHitPoints, strength, protection)
     * @effect All the given items get equiped in the free anchor slots, if there is no empty
     *         compatible anchor left the item does not get equiped.
     *         |for(Equipable item : items)
     *         |   for(Anchor anchor : getAnchors())
     *         |       if(item.isValidAnchor(anchor) && anchor.getItem() == null)
     *         |           item.equip(item)
     */
    public Hero(String name, int maxHitPoints, double strength, int protection, Equipable... items) {
        this(name, maxHitPoints, strength, protection);
        for(Equipable item : items){
            for(Anchor anchor : getAnchors()){
                if(item.isValidAnchor(anchor) && anchor.getItem() == null){
                    item.equip(anchor);
                }
            }
        }
    }

    /**
     * Generates a new Hero with a name, a maximum amount of hitpoints, a given strength stat and the default protetection stat.
     * @param name
     *        The given name of the new Hero
     * @param maxHitPoints
     *        The given maximum amount of hitpoints the Hero can have
     * @param strength
     *        The given strength the hero has
     *@effect The hero is generated as a hero with the default protection stat.
     *        | this(name,maxHitPoints,strength,getDefaultProtection())
     */
    public Hero(String name, int maxHitPoints, double strength){
        this(name,maxHitPoints,strength,getDefaultProtection());
    }





    /**
     * variable stating how much protection the hero has, how easily he can dodge or deflect attacks.
     */
    protected int protection;
    /**
     * the default amount of protection for a hero
     */
    protected static final int defaultProtection = 10;
    /**
     * this static states how many decimal places a strenth variable can have.
     */
    private static final int decimalPlacesStrength = 2;
    /**
     * A variable stating the intrinsic strength of a Hero.
     */
    protected double Strength;

    /**
     * @return the intrinsic protection of the hero
     */
    public int getProtection() {
        return protection;
    }

    /**
     * @return the default protection of heroes
     */
    public static int getDefaultProtection() {
        return defaultProtection;
    }

    /**
     * @return the strenth of the hero
     */
    public double getStrength() {
        return Strength;
    }

    /**
     * Set the protection to a given number
     * @param protection
     *        the given protection
     * @post  If the given protection is a strictly positive number, then it will be set as the protection.
     *        If it is not, then the protection will be set to the default amount.
     *        | if(protection > 0):
     *        |     this.protection = protection
     *        | else:
     *        |     this.protection = getDefaultProtection()
     */
    public void setProtection(int protection) {
        if(protection > 0){
            this.protection = protection;
        }
        else{
            this.protection = getDefaultProtection();
        }
    }

    /**
     * Set a new strength and rounds it to the right amount of decimals.
     * @param strength
     *        the given strength
     * @post  The strength of the hero is set to the given number rounded off to the amount of decimal
     *        places given by decimalPlacesStrength.
     *        |this.Strength == Math.round(strength * Math.pow(10, decimalPlacesStrength)) / Math.pow(10, decimalPlacesStrength)
     */
    public void setStrength(double strength) {
        Strength = Math.round(strength * Math.pow(10, decimalPlacesStrength)) / Math.pow(10, decimalPlacesStrength);
    }
    /**
     * Calculates the maximum capacity with the given strength.
     * @param strength
     *        the strength of the character.
     * @return The maximum capacity of the hero based on the rules,
     *         the maximum capacity is 20 times the strength, rounded off an integer.
     *         | maxcapacity == Math.round(strength * 20)
     *
     */
    public int calculateMaxCapacity(double strength){
        return (int) Math.round(strength * 20);
    }
    /**
     * Checks if a given name is valid.
     * @param name
     *        the given name that gets checked.
     * @return True if all the characters in the given name are valid characters, the name is not null
     *          ,the first character is a capital letter, it does not include more than two apostrophes and
     *          every colon is followed by a whitespace.
     *        | name.matches(validCharacters) && name != null && name.matches("^[A-Z].*") && apostrophecount < 3  && allColonsFollowedBySpace
     */
    @Raw
    @Override
    public boolean isValidName(String name){
        int apostrophecount = 0;
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == '\'') {
                apostrophecount++;
            }
        }
        Pattern pattern = Pattern.compile(":\\S");
        Matcher matcher = pattern.matcher(name);
        boolean allColonsFollowedBySpace = !matcher.find() && !(name.charAt(name.length() - 1) == ':');
        return (name.matches(validCharacters) && name != null && name.matches("^[A-Z].*") && apostrophecount < 3  && allColonsFollowedBySpace);
    }

    /**
     * Anchors
     */
    private void initialiseAnchors(){
        ArrayList<Anchor> list = new ArrayList<Anchor>();
        list.add(new Anchor(AnchorType.LINKERHAND,this));
        list.add(new Anchor(AnchorType.RECHTERHAND,this));
        list.add(new Anchor(AnchorType.RUG,this));
        list.add(new Anchor(AnchorType.LICHAAM,this));
        list.add(new Anchor(AnchorType.RIEM,this));
        setAnchors(list);
    }

    /**
     * Returns the damage that the hero will do if it hits.
     * @return The intrinsic strength + the damage values of both weapons minus ten and then this number divided in half
     *         and rounded down.
     *         |return Math.floor((getStrength() + leftWeapon.getDamage(). + RightWeapon.getDamage - 10)/2)
     */
    @Override
    protected int getTotalDamage(){
        double damage = getStrength();
        for(Anchor anchor : getAnchors()){
            if(anchor.getAnchorType() == AnchorType.LINKERHAND || anchor.getAnchorType() == AnchorType.RECHTERHAND){
                if(anchor.getItem() != null){
                    Weapon weapon = (Weapon) anchor.getItem();
                    damage += weapon.getDamage();
                }
            }
        }
        return (int) Math.floor((damage-10)/2);
    }

    /**
     * Gives the total protection stat of the hero.
     * @return The total protection stat that is the intrinsic protection with the currentpotection that the
     *         equiped armor gives.
     *         |return getProtection() + armor.getCurrentArmor()
     */

    @Override
    protected int getTotalProtection(){
        int protection = getProtection();
        for(Anchor anchor : getAnchors()){
            if(anchor.getAnchorType() == AnchorType.LICHAAM){
                if(anchor.getItem() != null){
                    Armor armor = (Armor) anchor.getItem();
                    protection += armor.getCurrentArmor();
                    break;
                }
            }
        }
        return protection;
    }
    @Override
    protected void LootAndHeal(ArrayList<Equipable> items){
        for(Anchor anchor: getAnchors()){
            for(Equipable item: items){
                if(item.isValidAnchor(anchor)){
                    if(item.getValue() > anchor.getItem().getValue()){
                        if(anchor.getItem().getWeight()-item.getWeight() <= getCapacity())
                            anchor.getItem().unequip(anchor);
                            item.equip(anchor);
                    }
                }
            }
        }
    }

    /**
     * Stores an in item that has already been pickup in away in a specified backpack
     * @param item
     * @param backpack
     * @throws IllegalArgumentException
     */
    public void store(Equipable item, Backpack backpack) throws IllegalArgumentException{

        if(backpack.getHolder() != this || backpack == null)
            throw new IllegalArgumentException();

        if(item.getHolder() != this || item == null)
            throw new IllegalArgumentException();

        Anchor backpackanchor = null;
        Anchor itemanchor = null;
        //als rugzak niet in een anchor, throw error
        for(int i= 0; i < getAnchors().size(); i++){
            if(getAnchorItemAt(i) == backpack){
                backpackanchor = getAnchorAt(i);
            }
            else if(getAnchorItemAt(i) == item)
                itemanchor = getAnchorAt(i);
        }
        if(backpackanchor == null || itemanchor == null){
            throw new IllegalArgumentException();
        }

        try {
            backpack.addEquipable(item);
        } catch (BackPackNotEmptyException e) {
            throw new RuntimeException(e);
        } catch (CarryLimitReachedException e) {
            throw new RuntimeException(e);
        } catch (OtherPlayersItemException e) {
            throw new RuntimeException(e);
        } catch (ItemAlreadyobtainedException e) {
            throw new RuntimeException(e);
        }
        itemanchor.setItem(null);
    }


    public void pickUpAndStore(Equipable item, Backpack backpack){

        /**
         * het item in onze hand dat niet de rugzak is wordt even op de grond gelegd om een item op te pakken.
         */
        int i = 0;
        Equipable currholding = getAnchorItemAt(0);
        if(currholding == backpack){
            i++;
            currholding = getAnchorItemAt(i);
        }
        getAnchorAt(i).setItem(null);

        //We proberen het item op te pakken
        try {
            pickUp(item, getAnchorAt(i).getAnchorType());
            //Het item wordt weggestoken in de rugzak
            store(item, backpack);
        } catch (ItemAlreadyobtainedException e) {
            throw new RuntimeException(e);
        } catch (AnchorslotOquipiedException e) {
            throw new RuntimeException(e);
        } catch (CarryLimitReachedException e) {
            throw new RuntimeException(e);
        } finally {
            //Het wapen wordt weer vastgenomen
            getAnchorAt(i).setItem(currholding);
        }
    }

    /**
     * Takes an item out of the backpack that the item is stored in and moves it to the specified Anchor.
     *
     * @param item
     * @param location
     */
    public void Equip(Equipable item, AnchorType location) throws IllegalArgumentException, OtherPlayersItemException, AnchorslotOquipiedException{

        Backpack parent = item.getParentbackpack();
        if(parent == null)
            throw new IllegalArgumentException();
        if(parent.getHolder() != this)
            throw new OtherPlayersItemException();

        Anchor anchor = null;

        for (int i = 0; i < getAnchors().size(); i++) {
            Anchor curranchor = getAnchorAt(i);
            if (curranchor.getAnchorType() == location) {
                if(curranchor.getItem() == null){
                    anchor = curranchor;
                    break;
                }
            }
        }
        if(anchor == null)
            throw new AnchorslotOquipiedException();

        parent.removeEquipable(item);
        anchor.setItem(item);



    }

    /**
     * Moves an item from the first specified anchor to the second anchor if the second Anchor is empty.
     * @param start
     * @param end
     */
    public void moveAnchorItemtoAnchor(AnchorType start, AnchorType end) throws AnchorslotOquipiedException{

        Anchor startanchor = null;
        Anchor endanchor = null;

        for (int i = 0; i < getAnchors().size(); i++) {
            Anchor curranchor = getAnchorAt(i);
            if (curranchor.getAnchorType() == start)
                startanchor = curranchor;
            if(curranchor.getAnchorType() == end)
                endanchor = curranchor;
        }
        if(startanchor != endanchor){
            Equipable startitem = startanchor.getItem();
            if(endanchor.getItem() != null)
                throw new AnchorslotOquipiedException();
            startanchor.setItem(null);
            endanchor.setItem(startitem);
             }
    }

    public void swapArmors(){}




}


