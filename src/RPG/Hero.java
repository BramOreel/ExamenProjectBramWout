package RPG;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import java.util.Random;

/**
 *
 *
 *
 *
 * @invar a hero may only carry up to 2 armors at a time.
 *       |canCarryArmor
 */
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
     * @param armor
     *        The armor that the hero wears at birth.
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
     *@effect The anchor gets equiped on the LICHAAM anchor.
     *        |pickUp(armor, AnchorType.LICHAAM)
     */

    public Hero(String name, int maxHitPoints, double strength, int protection, Armor armor) {
        super(name, maxHitPoints, (int) (20*strength));
        setMaxCapacity(calculateMaxCapacity(strength));
        setProtection(protection);
        setStrength(strength);
        initialiseAnchors();
        try {
            pickUp(armor, AnchorType.LICHAAM);
        } catch (ItemAlreadyobtainedException e) {
            throw new RuntimeException(e);
        } catch (AnchorslotOquipiedException e) {
            throw new RuntimeException(e);
        } catch (CarryLimitReachedException e) {
            throw new RuntimeException(e);
        }
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
    public Hero(String name, int maxHitPoints, double strength, int protection,Armor armor, Equipable... items) throws CarryLimitReachedException,ItemAlreadyobtainedException,AnchorslotOquipiedException {
        this(name, maxHitPoints, strength, protection, armor);
        for(Equipable item : items){
            for(Anchor anchor : getAnchors()){
                if(anchor.getItem() == null){
                    try {
                        pickUp(item, anchor.getAnchorType());
                    } catch (ItemAlreadyobtainedException e) {
                        throw new RuntimeException(e);
                    } catch (AnchorslotOquipiedException e) {
                        throw new RuntimeException(e);
                    } catch (CarryLimitReachedException e) {
                        throw new RuntimeException(e);
                    }
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
     * @return The intrinsic strength + the damage values of weapons in left and right hand minus ten and then this number divided in half
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
     * @return The total protection stat is the intrinsic protection in addition with the currentpotection that the
     *         equiped armor gives if the armor is equiped in the LICHAAM anchor.
     *         |for anchor in getAnchors()
     *         |    if anchor.getAnchortype == AnchorType.LICHAAM
     *         |    then armor = anchor.getItem()
     *         | result == getProtection() + armor.getCurrentArmor
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
    protected void LootAndHeal(ArrayList<Equipable> items) {
        for(Anchor anchor: getAnchors()){
            for(Equipable item: items){
                if(item.isValidAnchor(anchor)){
                    if(item.getValue() > anchor.getItem().getValue()){
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
     *        the name of the anchor where the item has to be equipped to.
     *
     * @effect The item gets picked up and equipped it in the anchorslot with the given type.
     *         super.pickUp(item, anchortype)
     *
     * @throws ItemAlreadyobtainedException
     *         The item already has a holder which means it can't be picked up.
     *         |item.getHolder == null
     * @throws IllegalArgumentException
     *         The item is not effective
     *         |item == null
     * @throws AnchorslotOquipiedException
     *         The creature is already holding an item in the anchor with the given anchortype
     *         |anchor.getItem() != null
     * @throws CarryLimitReachedException
     *         The given item is can't be picked up because the creature cannot carry it anymore
     *         because the maximum carry capacity has been reached. In case the user wants to pick up a backpack,
     *         the contents of this backpack are also considered for the calculation of the weight of the item.
     *         If the user tries to pick up more than 2 armors this error is also thrown.
     *         |item.getTotalWeight > getCapacity || (item instanceof Armor && this.getNbOfArmors = 2)
     * @throws BeltAnchorException
     *         The user wants to equip an item that isn't a purse to the belt anchorslot of the hero.
     *         |anchortype.getName() == "Riem" && item not instanceof Purse
     */
    @Override @Raw
    public void pickUp(Equipable item, AnchorType anchortype) throws ItemAlreadyobtainedException,IllegalArgumentException,
            AnchorslotOquipiedException, CarryLimitReachedException, BeltAnchorException{
        if(!canPickUpArmor() && item instanceof Armor)
            throw new CarryLimitReachedException(item);
        super.pickUp(item, anchortype);
    }

    /**
     * Stores an item, equipped in an anchorslot, away in a specified backpack.
     *
     * @param item
     *        The item to be stored away.
     * @param backpack
     *        The backpack to store the item away in.
     *
     * @effect The specified item is added to the contents of the specified backpack
     *         |backpack.addEquipable(item);
     * @effect The anchoritem of the anchor that the item was previously being stored in, is set to null
     *         |itemanchor.setItem(null);
     *
     * @throws IllegalArgumentException
     *         The backpack is not effective or the specified backpack is not a backpack of this hero
     *         |(backpack.getHolder() != this || backpack == null)
     * @throws IllegalArgumentException
     *         The item is not effective or the specified item is not a item of this hero
     *         |(item.getHolder() != this || item == null)
     * @throws IllegalArgumentException
     *         The backpack or the item is currently not being stored in an anchor, meaning that they are being stored in a backpack.
     *         Because backpacks in backpacks may not contain items and items already in a backpack cannot be stored away, an exception
     *         is thrown.
     */
    @Raw
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

    /**
     * Picks an item of the ground and immediately stores it away in a specified backpack.
     *
     * @param item
     *        the item to be picked up.
     * @param backpack
     *        the backpack to store the item away in.
     * @effect The item in the left or right hand that isn't the specified backpack is saved and temporarily set to null, the
     *         specified item gets picked up.
     *         |Equipable currholding = getAnchorItemAt(i);
     *         |getAnchorAt(i).setItem(null);
     *         |pickUp(item, getAnchorAt(i).getAnchorType());
     * @effect The newly picked up item gets stored away in the specified backpack. Finally, the saved item is equiped again in the original anchorslot.
     *         |store(item,backpack)
     *
     * @throws IllegalArgumentException
     *         the item or the backpack is not effective
     *         |(item == null || backpack == null)
     * @throws ItemAlreadyobtainedException
     *         This hero already has this item
     *         |item.getholder == this
     * @throws OtherPlayersItemException
     *        the backpack or the item belongs the another player.
     *        |backpack.getHolder() != this || item.getHolder() != null
     * @throws CarryLimitReachedException
     *         The item cannot be picked up because the specified backpack doesn't have enough capacity left
     *         |backpack.getCapacity() < backpack.getTotalWeight() + item.getWeight()
     * @throws IllegalArgumentException
     *         The item we want to pick up is a backpack which isn't empty.
     *         |((Backpack) item).getTotalWeight() != item.getWeight()
     * @throws IllegalArgumentException
     *         The specified backpack is already being stored within another backpack.
     *         |backpack.getParentbackpack() != null
     */
    @Raw
    public void pickUpAndStore(Equipable item, Backpack backpack) throws OtherPlayersItemException, CarryLimitReachedException,
            ItemAlreadyobtainedException, IllegalArgumentException{

        //checkers invoeren i suppose om error te voorkomen in try catch blok
        // is item of backapck niet al van iemand anders
        if(item == null || backpack == null)
            throw new IllegalArgumentException();
        if(item.getHolder() == this)
            throw new ItemAlreadyobtainedException();
        if(backpack.getHolder() != this || item.getHolder() != null)
            throw new OtherPlayersItemException();
        if(backpack.getCapacity() < backpack.getTotalWeight() + item.getWeight())
            throw new CarryLimitReachedException(item);
        if(item instanceof Backpack)
            if(((Backpack) item).getTotalWeight() != item.getWeight())
                throw new IllegalArgumentException();
        if(backpack.getParentbackpack() != null)
            throw new IllegalArgumentException();

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
            store(item, backpack);
        }  catch (AnchorslotOquipiedException e) {
            throw new RuntimeException(e);
        }finally {
            //Het wapen wordt weer vastgenomen
            getAnchorAt(i).setItem(currholding);
        }
    }

    /**
     * Takes an item out of the backpack that the item is stored in and moves it to the specified Anchor if that anchor is empty.
     *
     * @param item
     *        The item to be taken out of its backpack
     * @param location
     *        The anchorpoint of the hero to move the item to
     *
     * @effect The item is removed from the content of the backpack
     *         |item.getParentbackpack().removeEquipable(item);
     * @effet The item of the specified anchor is set to the given item
     *        |anchor.setItem(item)
     *
     * @throws IllegalArgumentException
     *         the given item is currently not being stored in a backpack.
     *         |item.getParentbackpack() = null
     * @throws OtherPlayersItemException
     *         the item is being stored in a backpack that isn't a backpack of this hero
     *         |item.getParent.getHolder() != this
     * @throws BeltAnchorException
     *         The user wants to equip an item that isn't a purse to the belt anchorslot of the hero.
     *         |anchortype.getName() == "Riem" && item not instanceof Purse
     * @throws AnchorslotOquipiedException
     *         The anchor location does not exist or already has an item equiped in this slot
     *         |anchor.getItem() != null || getAnchors.contains(anchortype) == false
     */
    @Raw
    public void Equip(Equipable item, AnchorType location) throws IllegalArgumentException, OtherPlayersItemException, AnchorslotOquipiedException, BeltAnchorException{

        Backpack parent = item.getParentbackpack();
        if(parent == null)
            throw new IllegalArgumentException();
        if(parent.getHolder() != this)
            throw new OtherPlayersItemException();
        if (location.getName() == "Riem" && !(item instanceof Purse))
            throw new BeltAnchorException();

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
     *
     * @param start
     *        the anchor to move the item from
     * @param end
     *        the anchor to move the item to
     *
     * @effect the item in the start anchor is set to null
     *         |start.setItem(null)
     * @effect the item in the end anchor is set to the item in the start anchor
     *         |end.setItem(start.getItem())
     *
     * @throws AnchorslotOquipiedException
     *         The endAnchor already has an item equiped to it
     *         |end.getItem() != null
     * @throws BeltAnchorException
     *         The user wants to equip an item that isn't a purse to the belt anchorslot of the creature.
     *         |anchortype.getName() == "Riem" && item not instanceof Purse
     */
    public void moveAnchorItemtoAnchor(AnchorType start, AnchorType end) throws AnchorslotOquipiedException, BeltAnchorException{

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
            if (end.getName() == "Riem" && !(startitem instanceof Purse))
                throw new BeltAnchorException();

            startanchor.setItem(null);
            endanchor.setItem(startitem);
             }
    }

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

    private boolean canPickUpArmor(){
        return (getNbOfArmors() < 2);
    }



    /**
     * Swaps the postion of the two armors that a hero is currently carrying with him
     */
    public void swapArmors(Armor armor) throws CantFindArmortoSwapException, CarryLimitReachedException, OtherPlayersItemException {
        if(getNbOfArmors() != 2)
            throw new CantFindArmortoSwapException();
        Equipable bodyitem = getAnchorItemAt(3);
        if(!(bodyitem instanceof Armor))
            throw new CantFindArmortoSwapException();


        Backpack parent = armor.getParentbackpack();
        if(parent != null){ //armor zit in een rugzak
            int weight = parent.getTotalWeight() - armor.getWeight() + bodyitem.getWeight();
            if(weight > parent.getCapacity())
                throw new CarryLimitReachedException(bodyitem);

            drop(armor);
            store(bodyitem,parent);

            try {
                pickUp(bodyitem,AnchorType.LICHAAM);
            } catch (ItemAlreadyobtainedException e) {
                throw new RuntimeException(e);
            } catch (AnchorslotOquipiedException e) {
                throw new RuntimeException(e);
            }
        }
        //armor zit in een Anchor

        Anchor curranchor = null;
        for (int i = 0; i < getAnchors().size(); i++) {
            curranchor = getAnchorAt(i);
            if(curranchor.getItem() == armor)
                break;
        }
        drop(bodyitem);
        try {
            moveAnchorItemtoAnchor(curranchor.getAnchorType(), AnchorType.LICHAAM);
        } catch (AnchorslotOquipiedException e) {
            throw new RuntimeException(e);
        }
        try {
            pickUp(bodyitem,curranchor.getAnchorType());
        } catch (ItemAlreadyobtainedException e) {
            throw new RuntimeException(e);
        } catch (AnchorslotOquipiedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Gives the closest positive prime number to a number that isn't bigger than a given maximum.
     * @param number
     *        The number that you want the closest prime of.
     * @param max
     *        The maximum, the returned prime number can't be bigger than this value.
     * @return The closest prime number.
     * @pre    The given maximum must be 2 or higher, otherwise there will be no prime number.
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
     *         |if (number <= 1)
     *         |        return false
     *         |
     *         |for (int i = 2; i <= Math.sqrt(number); i++)
     *         |        if (number % i == 0)
     *         |                return false
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


