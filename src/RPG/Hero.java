package RPG;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import java.util.Random;

/**
 * A class of Heroes
 *
 * @invar	Each Hero must have a properly spelled name.
 * 			| canHaveAsName(getName())
 * @invar   Each Hero must have a valid strength stat.
 *          | isValidStrength(getStrength)
 * @invar   Each creature must always have a valid protection stat.
 *          | isValidProtection(getProtection())
 * @invar   a hero may only carry up to 2 armors at a time.
 *          | this.getNbOfArmors() <= 2
 * @invar   Each creature must have proper anchors
 *          | hasProperAnchors(getAnchors())
 * @author 	Wout Thiers & Bram Oreel
 * @version 1.0
 */
public class Hero extends Creature{

    /**
     * Generates a new Hero with a name, a maximum amount of hitpoints, a given strength stat, a given protection stat and a piece of armor.
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
     * @param armor
     *        The armor that the hero wears at birth.
     *@effect The maximum capacity is calculated with the given strength and then set as the strength.
     *        |setMaxCapacity(calculateMaxCapacity(strength))
     *@effect The Hero is generated as a creature with a given name, maxHitPoints and the calculated maxCapacity.
     *        | super(name, maxHitPoints, maxCapacity)
     *@effect The protection is set as the protection.
     *        | setProtection(protection)
     *@effect The strength is set as the strength.
     *        | setStrength(strength)
     *@effect five empty anchors are initialised and set as the anchors of this hero. One left hand, One right hand, one back, one chest and one belt.
     *        | initialiseAnchors()
     *@effect The armor gets equiped on the LICHAAM anchor.
     *        | pickUp(armor, AnchorType.LICHAAM)
     */
    @Raw
    public Hero(String name, int maxHitPoints, double strength, int protection, Armor armor) {
        super(name, maxHitPoints, (int) (20*strength));
        setMaxCapacity(calculateMaxCapacity(strength));
        setProtection(protection);
        setStrength(strength);
        initialiseAnchors();
        try {
            pickUp(armor, AnchorType.LICHAAM);
        } catch (CarryLimitReachedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a new hero with items equipped.
     * @param name
     *        The given name of the new Hero
     * @param maxHitPoints
     *        The given maximum amount of hitpoints the Hero can have
     * @param strength
     *        The given strength the hero has
     * @param protection
     *        The given protection stat the hero has.
     * @param armor
     *        The armor that the hero wears at birth.
     * @param items
     *        The given items that the Hero has to equip in random anchor slots.
     * @effect The Hero gets generated with the given name, maxhitpoints, strength, protection and armor.
     *        | this(name, maxHitPoints, strength, protection, armor)
     * @effect All the given items get equipped in the free anchor slots, if there is no empty
     *         anchor left the item does not get equipped.
     *         |for each item in items
     *         |   for each anchor in getAnchors()
     *         |       if the anchor is empty
     *         |       then pickUp(item, anchor.getAnchorType())
     */
    @Raw
    public Hero(String name, int maxHitPoints, double strength, int protection,Armor armor, Equipable... items){
        this(name, maxHitPoints, strength, protection, armor);
        for(Equipable item : items){
            for(Anchor anchor : getAnchors()){
                if(anchor.getItem() == null){
                    try {
                        pickUp(item, anchor.getAnchorType());
                    } catch (CarryLimitReachedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    /**
     * Generates a new Hero with a name, a maximum amount of hitpoints, a given strength stat and the default protection stat.
     * @param name
     *        The given name of the new Hero
     * @param maxHitPoints
     *        The given maximum amount of hitpoints the Hero can have
     * @param strength
     *        The given strength the hero has
     * @param armor
     *        the armor that the hero needs to wear
     *@effect The hero is generated as a hero with the default protection stat.
     *        | this(name,maxHitPoints,strength,getDefaultProtection(), armor)
     */
    @Raw
    public Hero(String name, int maxHitPoints, double strength, Armor armor){
        this(name,maxHitPoints,strength,getDefaultProtection(), armor);
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
     * this static states how many decimal places a strength variable can have.
     */
    private static final int decimalPlacesStrength = 2;
    /**
     * A variable stating the intrinsic strength of a Hero.
     */
    protected double Strength;

    /**
     * return the intrinsic protection of the hero
     */
    @Basic
    public int getProtection() {
        return protection;
    }

    /**
     * return the default protection of heroes
     */
    @Basic
    public static int getDefaultProtection() {
        return defaultProtection;
    }

    /**
     * checks if the protection stat is valid.
     * @param protection
     *        the protection stat
     * @return true if it is positive, false otherwise
     *         |result == (protection > -1)
     */
    public static boolean isValidProtection(int protection){
        return protection > -1;
    }

    /**
     * return the strength of the hero
     */
    @Basic
    public double getStrength() {
        return Strength;
    }

    /**
     * Set the protection to a given number
     * @param protection
     *        the given protection
     * @post  If the given protection is valid, then it will be set as the protection.
     *        If it is not, then the protection will be set to the default amount.
     *        | if(isValidProtection(protection)):
     *        |     this.protection = protection
     *        | else:
     *        |     this.protection = getDefaultProtection()
     */
    @Raw
    public void setProtection(int protection) {
        if(isValidProtection(protection)){
            this.protection = protection;
        }
        else{
            this.protection = getDefaultProtection();
        }
    }

    /**
     * Set a new strength and rounds it to the right amount of decimals and makes sure its positive.
     * @param strength
     *        the given strength
     * @post  The strength of the hero is set to the given number rounded off to the amount of decimal
     *        places given by decimalPlacesStrength and made positive.
     *        |this.Strength == Math.abs(Math.round(strength * Math.pow(10, decimalPlacesStrength)) / Math.pow(10, decimalPlacesStrength))
     */
    @Raw
    public void setStrength(double strength) {
        this.Strength = Math.abs(Math.round(strength * Math.pow(10, decimalPlacesStrength)) / Math.pow(10, decimalPlacesStrength));
    }

    /**
     * Checks if the strength stat is valid.
     * @param strength
     *        | the strength stat that needs to be checked.
     * @return True if the strength stat is a positive number that does not have more decimals than allowed, false otherwise.
     *         |double roundedNumber = Math.round(strength *  Math.pow(10,decimalPlacesStrength))/ Math.pow(10,decimalPlacesStrength)
     *         |double difference = Math.abs(strength - roundedNumber)
     *         |result == ((difference < Math.pow(10,-decimalPlacesStrength)) && strength >= 0)
     */
    @Raw
    public static boolean isValidStrength(double strength){
        double roundedNumber = Math.round(strength *  Math.pow(10,decimalPlacesStrength))/ Math.pow(10,decimalPlacesStrength);
        double difference = Math.abs(strength - roundedNumber);
        return (difference < Math.pow(10,-decimalPlacesStrength)) && strength >= 0;
    }


    /**
     * Calculates the maximum capacity with the given strength.
     * @param strength
     *        the strength of the character.
     * @return The maximum capacity of the hero based on the rules,
     *         the maximum capacity is 20 times the strength, rounded off to an integer.
     *         | result == Math.round(strength * 20)
     */
    @Model
    @Raw
    protected int calculateMaxCapacity(double strength){
        return (int) Math.round(strength * 20);
    }
    /**
     * Checks if a given name is valid.
     * @param name
     *        the given name that gets checked.
     * @return True if all the characters in the given name are valid characters, the name is not null
     *          ,the first character is a capital letter, it does not include more than two apostrophes and
     *          every colon is followed by a whitespace.
     *        | result == name.matches(validCharacters) && name != null && name.matches("^[A-Z].*")
     *        |           && apostrophecount < 3  && allColonsFollowedBySpace
     */
    @Raw
    @Override
    public boolean canHaveAsName(String name){
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
     * Checks if the anchors are correctly set
     * @param anchors
     *        the list cotaining the anchors of the Hero.
     * @return True only if the Hero has exactly 5 anchors containing one with each of these types: LINKERHAND, RECHTERHAND, RIEM, LICHAAM and RUG
     *         and each of the anchor has this hero as the owner. Returns False if these things are not the case.
     *         |result == anchortypes.contains(AnchorType.LINKERHAND) && anchortypes.contains(AnchorType.RECHTERHAND)
     *         |         && anchortypes.contains(AnchorType.RIEM) && anchortypes.contains(AnchorType.LICHAAM)
     *         |         && anchortypes.contains(AnchorType.RUG) && anchortypes.size() == 5 && for anchor in anchors: anchor.getOwner() == this
     */
    @Override
    @Raw
    public boolean hasProperAnchors(ArrayList<Anchor> anchors) {
        ArrayList<AnchorType> anchortypes = new ArrayList<>();
        for (Anchor curranchor : anchors) {
            anchortypes.add(curranchor.getAnchorType());
            if (curranchor.getOwner() != this) {
                return false;
            }
        }
        return anchortypes.contains(AnchorType.LINKERHAND) && anchortypes.contains(AnchorType.RECHTERHAND)
                && anchortypes.contains(AnchorType.RIEM) && anchortypes.contains(AnchorType.LICHAAM)
                && anchortypes.contains(AnchorType.RUG) && anchortypes.size() == 5;
    }

    /**
     * Initializes the anchors of the hero
     * @effect The anchors are set to a list of new anchors with the following anchorTypes:
     *         LINKERHAND, RECHTERHAND, RIEM, LICHAAM and RUG.
     *         | setAnchors(new ArrayList<Anchor>(new Anchor(AnchorType.LINKERHAND,this), new Anchor(AnchorType.RECHTERHAND,this),
     *         |             new Anchor(AnchorType.RUG,this), new Anchor(AnchorType.LICHAAM,this), new Anchor(AnchorType.RIEM,this)))
     *
     */
    @Model
    @Raw
    private void initialiseAnchors(){
        ArrayList<Anchor> list = new ArrayList<>();
        list.add(new Anchor(AnchorType.LINKERHAND,this));
        list.add(new Anchor(AnchorType.RECHTERHAND,this));
        list.add(new Anchor(AnchorType.RUG,this));
        list.add(new Anchor(AnchorType.LICHAAM,this));
        list.add(new Anchor(AnchorType.RIEM,this));
        setAnchors(list);
    }

    /**
     * Returns the damage that the hero will do if it hits.
     * @return The intrinsic strength + the damage values of weapons in left and right hand minus ten and then this number divided in half
     *         and rounded down.
     *         |result == Math.floor((getStrength() + leftWeapon.getDamage(). + RightWeapon.getDamage - 10)/2)
     */
    @Override @Model
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
     * @return The total protection stat is the intrinsic protection in addition with the current protection that the
     *         equipped armor gives if the armor is equipped in the LICHAAM anchor.
     *         | result == getProtection() + armor.getCurrentArmor
     */
    @Override @Model
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

    /**
     * Loots items from a list of items. Every item in the anchors gets replaced by a more valuable item for that specific anchortype
     *             if there is such an item.
     * @param items
     *        The items that can be looted.
     * @effect For every item in the lootable items, if this item is valid for an empty anchor and there is enough
     *         remaining capacity, then the item will get equipped. The first anchors in the getAnchors() will get filled first
     *         and the first items in items will get equipped first for these anchors.
     *         | for every anchor in getAnchors(), for every item in items:
     *         |       if item.isValidAnchor(anchor) && item.getWeight() <= getCapacity() && anchor.getItem() == null
     *         |       then item.equip(anchor)
     * @effect A random percentage gets generated between 0% and 100% this percentage then gets multiplied with the difference
     *         between the current hit points and the max hit points. This added with the current amount of hit points will be the new hit points
     *         but fist it needs to be rounded to the nearest prime number that isn't bigger than the max hit points. This is then set
     *         as the new remaining amount of hit points.
     *         |setHitPoints(findClosestPrime(Math.round((getMaxHitPoints() - getHitPoints()) * random.nextDouble() + getHitPoints()),getMaxHitPoints()))
     */
    @Override @Model
    protected void LootAndHeal(ArrayList<Equipable> items) {
        for(Anchor anchor: getAnchors()){
            for(Equipable item: items){
                if(item.isValidAnchor(anchor) && item.getWeight() <= getCapacity() && anchor.getItem() == null){
                    if(item.getParentbackpack() != null){
                        item.getParentbackpack().removeEquipable(item);
                    }
                            item.equip(anchor);
                        }
                    }
                }
        Random random = new Random();
        double percentage = random.nextDouble();
        int hp = (int) Math.round((getMaxHitPoints() - getHitPoints()) * percentage + getHitPoints());
        setHitPoints(findClosestPrime(hp,getMaxHitPoints()));
    }

    /**
     * Lets a hero pick up an equipable item of the ground and equip it in one of its anchor points.
     *
     * @param item
     *        the item that will be picked up.
     * @param anchortype
     *        the type of the anchor where the item has to be equipped to.
     *
     * @effect The item gets picked up and equipped it in the anchorslot with the given type.
     *         | super.pickUp(item, anchortype)
     * @throws CarryLimitReachedException
     *         If the user tries to pick up more than 2 armors this exception is thrown.
     *         | item instanceof Armor && !canPickUpArmor== 2
     */
    @Override @Raw
    public void pickUp(Equipable item, AnchorType anchortype) throws CarryLimitReachedException{
        if(!canPickUpArmor() && item instanceof Armor)
            throw new CarryLimitReachedException(item);
        try {
            super.pickUp(item, anchortype);
        } catch (ItemAlreadyobtainedException e) {
            throw new RuntimeException(e);
        } catch (AnchorslotOccupiedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Returns the total number of armors that a hero is currently carrying in his anchors and backpacks.
     */
    @Model
    private int getNbOfArmors(){
       int total = 0;
        for (int i = 0; i < getAnchors().size(); i++){
            Equipable currItem = getAnchorItemAt(i);
            if(currItem instanceof Armor)
                total++;
            else if (currItem instanceof Backpack) {
                total= total +((Backpack) currItem).getNbOfArmors();
            }
        }
        return total;
    }

    /**
     * returns True if the total number of armors that this hero is currently carrying is smaller than 2. False otherwise.
     */
    @Raw
    public boolean canPickUpArmor(){
        return (getNbOfArmors() < 2);
    }


    /**
     * Swaps the position of the armor in the body anchorslot with a given armor.
     *
     * @param armor
     *        the armor to swap with.
     *
     * @effect if the given armor is being stored in a backpack. then the given armor is removed from the backpack and set as the
     *         item stored in the body anchorslot of this hero. Lastly the armor that was previously being stored in the body anchorslot,
     *         gets added to the content of the backpack.
     *         |parent.removeEquipable(armor);
     *         |parent.addEquipable(bodyitem);
     *         |getAnchorAt(3).setItem(armor);
     * @effect if the given armor is being stored in an anchorslot. then the items stored in the anchorslot and the body anchorslot are swapped.
     *         |curranchor.setItem(bodyitem);
     *         |getAnchorAt(3).setItem(armor);
     * @effect if the given anchor was still on the ground. Then the holder of the given armor gets set to this hero and the armor gets equiped in the body anchorslot
     *         of this hero. The holder of the armor that was previously being stored in the body anchorslot gets set to null.
     *         Lastly the carry capacity for this hero is updated to account for the weight of the given armor.
     *         |getAnchorAt(3).setItem(armor);
     *         |armor.setHolder(this);
     *         |ChangeCapacity(bodyitem.getWeight() - armor.getWeight());
     *         |bodyitem.setHolder(null);
     *
     * @post the item stored in the body anchorslot for this hero is the given armor
     *       |this.getItemAt(3) == armor
     *
     * @throws CantFindArmortoSwapException
     *         the equipable in the body anchorslot is not an instance of armor.
     *         |bodyitem not instanceof Armor || bodyitem == null
     * @throws IllegalArgumentException
     *         the armor to swap with is not effective
     *         |armor == null
     * @throws CantFindArmortoSwapException
     *         the given armor is being stored in our inventory but the hero isn't carrying another armor to swap with
     *         |getNbOfArmors() != 2
     * @throws OtherPlayersItemException
     *         the given armor belongs to another player
     *         |armor.getHolder != null && armor.getHolder != this
     * @throws CarryLimitReachedException
     *         The armor to swap to is being stored in a backpack. we can't swap however because the weight of armor on the hero's body
     *         exceeds the maximum carry capacity of the backpack.
     *         |parent.getTotalWeight() - armor.getWeight() + bodyitem.getWeight() > parent.getCapacity()
     * @throws CarryLimitReachedException
     *         The armor to swap to is on the ground. We cant swap however because the weight of the armor to be picked up exceeds the
     *         maximum carry capacity for this hero.
     *         |getCapacity() - bodyitem.getWeight() + armor.getWeight() > getMaxCapacity()
     */
    @Raw
    public void swapArmors(Armor armor) throws CantFindArmortoSwapException, IllegalArgumentException, OtherPlayersItemException, CarryLimitReachedException {
        Equipable bodyitem = getAnchorItemAt(3);
        if (!(bodyitem instanceof Armor) || bodyitem == null)
            throw new CantFindArmortoSwapException();
        if (armor == null)
            throw new IllegalArgumentException();

        Creature swapholder = armor.getHolder();
        if (swapholder == this) { //armor in onze inventory

            if (getNbOfArmors() != 2)
                throw new CantFindArmortoSwapException();

            Backpack parent = armor.getParentbackpack();
            if (parent != null) { //armor zit in een rugzak

                int weight = parent.getTotalWeight() - armor.getWeight() + bodyitem.getWeight();
                if (weight > parent.getCapacity())
                    throw new CarryLimitReachedException(bodyitem);

                parent.removeEquipable(armor);
                try {
                    parent.addEquipable(bodyitem);
                } catch (BackPackNotEmptyException e) {
                    throw new RuntimeException(e);
                } catch (ItemAlreadyobtainedException e) {
                    throw new RuntimeException(e);
                }
                getAnchorAt(3).setItem(armor);
            }
            else{ //armor in een anchor
                Anchor curranchor = null;
                for (int i = 0; i < getAnchors().size(); i++) {
                    curranchor = getAnchorAt(i);
                    if (curranchor.getItem() == armor)
                        break;
                }
                curranchor.setItem(bodyitem);
                getAnchorAt(3).setItem(armor);
            }

        }
        else if (swapholder == null) { //armor op de grond
            if (getCapacity() - bodyitem.getWeight() + armor.getWeight() > getMaxCapacity())
                throw new CarryLimitReachedException(armor);

            getAnchorAt(3).setItem(armor);
            armor.setHolder(this);
            ChangeCapacity(bodyitem.getWeight() - armor.getWeight());
            bodyitem.setHolder(null);
        }
        else throw new OtherPlayersItemException();
    }

    /**
     * Gives the closest positive prime number to a number that isn't bigger than a given maximum.
     * @param number
     *        The number that you want the closest prime of.
     * @param max
     *        The maximum, the returned prime number can't be bigger than this value.
     * @return The closest prime number.
     * @pre    The given maximum must be 2 or higher, otherwise there will be no prime number.
     *         |max => 2
     */
    @Model
    private static int findClosestPrime(int number, int max) {
        if (isPrime(number)) {
            return number;  // If the number itself is prime, return it
        }
        int smaller = number - 1;
        int larger = number + 1;
        while (true) {
            if (isPrime(smaller)) {
                return smaller;
            }
            if (isPrime(larger)) {
                return larger;
            }
            if(smaller > 0){
                smaller--;}
            if(larger < max){
                larger++;}
        }
    }

    /**
     * Function to determine if a number is prime.
     * @param number
     *        the number
     * @return True if it is prime and false if it is not. Prime means that it is only divisible by 1 and itself.
     *         |if number <= 1
     *         |then result == false
     *         |or if number % i == 0 for every integer i from 2 to sqrt(number)
     *         |then result == false
     *         |else result == true
     */
    @Model
    private static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

}


