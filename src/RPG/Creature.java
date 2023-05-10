package RPG;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import java.util.Random;
import java.util.ArrayList;

/**
 * An abstract class of creatures.
 *
 * @invar	Each creature must have a properly spelled name.
 * 			| canHaveAsName(getName())
 * @invar   Each creature must always have a valid amount of hitpoints.
 *          | canHaveAsHitPoints()
 * @invar   Each creature must always have a valid maximum amount of hitpoints.
 *          | isValidMaxHitPoints()
 * @invar   Each creature must always have a valid amount of remaining capacity.
 *          | canHaveAsCapacity()
 * @invar   Each creature must always have a valid maximum capacity.
 *          | isValidMaxCapacity()
 * @author 	Wout Thiers & Bram Oreel
 * @version 1.0
 *
 * @note 	The name invariant has become object-dependent, so the name of
 * 			the checker changed to canHaveAsX (instead of isValidX).
 * 			Although we do not rely on properties/attributes of the object itself
 * 			to determine the validity of the name, it still is important
 * 			which subclass the object belongs to.
 * 			Hence the object-dependency of the checker.
 */
public abstract class Creature {
    /**
     * Creates a new creature with a name, amount of hitpoints and capactity.
     * @param name
     *        The name of the new creature.
     * @param maxHitPoints
     *        The maximum amount of hitpoints of the creature.
     * @param maxCapacity
     *        The maximum capacity of the creature in kg.
     * @effect The new name is set to the name of the creature.
     *         |setName(name)
     * @effect The maximum capacity is set to the given maxCapacity.
     *         |setMaxCapacity(maxCapacity)
     * @effect The remaining capacity is set to the given maxCapacity,
     *          because the creature still² has an empty inventory.
     *         |setCapacity(maxCapacity)
     * @effect The maximum amount of hitpoints is set to the given maxHitPoints.
     *         |setMaxHitPoints(maxHitPoints)
     * @effect The amount of hitpoints is set to the given maxHitPoints,
     *          so the creature has full health.
     *         |setHitPoints(maxHitPoints)
     * @throws IllegalArgumentException
     *         if the name is not a valid name for the creature.
     *         |!canHaveAsName(name)
     */
    @Raw @Model
    protected Creature(String name, int maxHitPoints, int maxCapacity) throws IllegalArgumentException{
        if(!canHaveAsName(name)){
            throw new IllegalArgumentException();
        }
        setName(name);
        setMaxCapacity(maxCapacity);
        setCapacity(maxCapacity);
        setMaxHitPoints(maxHitPoints);
        setHitPoints(maxHitPoints);

    }

    /**
     * variable containing the name of the Creature
     */
    protected String name;

    /**
     * variable containing maximum amount of hitpoints
     */
    protected int maxHitPoints;

    /**
     * variable containing the amount of hitpoints
     */
    protected int hitPoints;

    /**
     * variable containing the current remaining capacity of the creature in kg
     */
    protected int capacity;

    /**
     * variable containing the maximum capacity the creature can carry in kg
     */
    protected int maxCapacity;


    /**
     * a list containing all valid characters for names of creatures.
     */
    @Basic
    protected final static String validCharacters = "[a-zA-Z': ]+";

    /**
     * returns the name of the creature.
     */
    @Basic
    public String getName() {
        return this.name;
    }

    /**
     * returns the maximum amount of hitpoints of the creature.
     */
    @Basic
    public int getMaxHitPoints() {
        return this.maxHitPoints;
    }

    /**
     * returns the remaining hitpoints of the creature.
     */
    @Basic
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * returns the current remaining capacity of the creature.
     */
    @Basic
    public int getCapacity() {
        return capacity;
    }



    /**
     * returns the maximum capacity of the creature.
     */
    @Basic
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Sets a new given max hitpoints.
     * @param maxHitPoints
     *        the given amount.
     * @pre   the given amount must be a valid amount.
     *        | isValidMaxHitpoints(capacity)
     * @post  the amount of maxhitpoints is now the given amount.
     *        | this.maxHitPoints == maxHitPoints
     */
    @Raw @Model
    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
    }
    /**
     * Sets a new given amount of hitpoints.
     * @param hitPoints
     *        the given amount.
     * @pre   the given amount must be a valid amount.
     *        | canHaveAsHitpoints(hitPoints)
     * @post  the amount of hitpoints is now the given amount.
     *        | this.hitPoints == hitPoints
     */
    @Raw @Model
    protected void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
    /**
     * Sets a new given remaining capacity.
     * @param capacity
     *        the given amount.
     * @pre   the given amount must be a valid amount.
     *        | canHaveAsCapacity(capacity)
     * @post  the amount of remaining capacity is now the given amount.
     *        | this.capacity == capacity
     */
    @Raw @Model
    protected void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Adds a remaining capacity.
     * @param capacity
     *        the given amount, can be negative.
     * @pre   the given amount must make the new capacity a valid amount.
     *        | canHaveAsCapacity(getCapacity()-capacity)
     * @effect  the amount of remaining capacity is now the previous amount plus the given weight.
     *        | setCapacity(getCapacity() + capacity)
     */
    @Model
    protected void ChangeCapacity(int capacity) {
        setCapacity(getCapacity() + capacity);
    }
    /**
     * Sets a new given maximum capacity.
     * @param maxCapacity
     *        the given amount.
     * @pre   the given amount must be a valid amount.
     *        | isValidMaxCapacity(maxCapacity)
     * @post  the maximum capacity is now the given amount.
     *        | this.maxCapacity == maxCapacity
     */
    @Model @Raw
    protected void setMaxCapacity(int maxCapacity) {
            this.maxCapacity = maxCapacity;
    }

    /**
     * checks if the maxCapacity is a valid amount.
     * @param   maxCapacity
     *          the maxCapacity that needs to be checked.
     * @return  True if the amount is a non-negative integer, false if not.
     *          | maxCapacity > -1
     */
    @Raw
    public static boolean isValidMaxCapacity(int maxCapacity){
        return maxCapacity > -1;
    }

    /**
     * checks if the remaining capacity is a valid amount.
     * @param   capacity
     *          the remaining capacity that needs to be checked.
     * @return  True if the amount is a non-negative integer smaller than the maximum capacity, false if not.
     *          | result == getMaxCapacity() > capacity &&  capacity > -1
     */
    @Raw
    public boolean canHaveAsCapacity(int capacity){
        return  getMaxCapacity() > capacity &&  capacity > -1;
    }

    /**
     * checks if the remaining amount of hitpoints is a valid amount.
     * @param   hitPoints
     *          the remaining amount of hitpoints that needs to be checked.
     * @return  True if the amount is a non-negative integer smaller than the maximum amount, false if not.
     *          |result == getMaxHitPoints() > hitPoints &&  hitPoints > -1
     */
    @Raw
    public boolean canHaveAsHitpoints(int hitPoints){
        return  (getMaxHitPoints() > hitPoints &&  hitPoints > -1);
    }
    /**
     * checks if the maximum amount of hitpoints is a valid amount.
     * @param   maxHitPoints
     *          the max amount of hitpoints that needs to be checked.
     * @return  True if the amount is a non-negative integer, false if not.
     *          |result == maxHitPoints > -1
     */
    public static boolean isValidMaxHitpoints(int maxHitPoints){
        return  (maxHitPoints > -1);
    }


    /**
     * Checks if a given name is valid.
     * @param name
     *        the given name that gets checked.
     * @return True if all the characters in the given name are valid characters, the name is not null
     *          and the first character is a capital letter.
     *        | name.matches(validCharacters) && name != null && name.matches("^[A-Z].*")
     */
    @Raw
    public boolean canHaveAsName(String name){
        return name.matches(validCharacters) && name != null && name.matches("^[A-Z].*");
    }

    /**
     * Sets a new name.
     * @param  name
     *         The new name.
     * @throws IllegalArgumentException
     *         If the given name is not valid this exception is thrown.
     *         | !canHaveAsName(name)
     * @post   The new name is set as the given name.
     *         | this.name == name
     */
    protected void setName(String name) throws IllegalArgumentException{
        if(!canHaveAsName(name)){
            throw new IllegalArgumentException();
        }
        else {
            this.name = name;
        }
    }
    /********
     * Anchors
     */

    /**
     * A list containing 'anchors' referencing the anchors for this creature.
     */
    protected ArrayList<Anchor> anchors = new ArrayList<Anchor>();

    /**
     * Returns the list of anchors of this creature
     */
    @Basic @Model
    protected ArrayList<Anchor> getAnchors() {
        return anchors;
    }

    /**
     * Sets the list of anchors to the given list.
     *
     * @param anchors
     *        The new anchors for this creature
     */
    @Model
    protected void setAnchors(ArrayList<Anchor> anchors) {
        this.anchors = anchors;
    }

    /**
     *Returns the number of anchors for this creature
     */
    @Basic
    public int getNbOfAnchors(){
        return getAnchors().size();
    }


    /**
     * returns the anchor with index i in the arraylist of anchors.
     *
     * @param i
     *        The given index for the anchor in the arraylist of anchors
     * @throws IllegalArgumentException
     *         the index i is out of range
     *         |i > getNbOfAnchors()
     */
    public Anchor getAnchorAt(int i) throws  IllegalArgumentException{
        if(i > getNbOfAnchors())
            throw new IllegalArgumentException();
        return getAnchors().get(i);
    }

    /**
     * Returns the item being stored in the anchor with given index i in the arraylist of anchors.
     *
     * @param i
     *        The given index for the anchor in the arraylist of anchors.
     */
    public Equipable getAnchorItemAt(int i){
        return getAnchorAt(i).getItem();
    }

    /**
     * Picks an item up from the ground and equips it in a specified anchorslot.
     *
     * @param item
     *        the item that will be picked up.
     * @param anchortype
     *        the name of the anchor where the item has to be equiped to.
     *
     * @effect The carry capacity for this creature is updated to account for the extra weight of the item that was picked up.
     *         In case the item is a backpack, the contents of this backpack are also considered for the calculation
     *         of the weight of the item.
     *         |ChangeCapacity(item.totalweight);
     * @effect The item gets picked up and the unidirectional relation between the item and the given anchor gets set up.
     *         |item.equip(anchor)
     *
     * @throws ItemAlreadyobtainedException
     *         The item already has a holder which means it can't be picked up.
     *         |item.getHolder == null
     * @throws IllegalArgumentException
     *         The item is not effective
     *         |item == null
     * @throws BeltAnchorException
     *         The user wants to equip an item that isn't a purse to the belt anchorslot of the creature.
     *         |anchortype.getName() == "Riem" && item not instanceof Purse
     * @throws IllegalArgumentException
     *         The creature does not have an anchor with the given type as its type.
     *         |Anchors.contains(anchortype) == false
     * @throws AnchorslotOquipiedException
     *         The creature is already holding an item in the anchor with the given anchortype
     *         |anchor.getItem() != null
     * @throws CarryLimitReachedException
     *         The given item is can't be picked up because the creature cannot carry it anymore
     *         because the maximum carry capacity has been reached. In case the user wants to pick up a backpack,
     *         the contents of this backpack are also considered for the calculation of the weight of the item.
     *         |item.getTotalWeight > getCapacity
     */
    @Raw @Model
    protected void pickUp(Equipable item, AnchorType anchortype) throws ItemAlreadyobtainedException,IllegalArgumentException,
            AnchorslotOquipiedException, CarryLimitReachedException, BeltAnchorException {
        if (item.getHolder() != null) {
            throw new ItemAlreadyobtainedException();
        }
        if (item == null)
            throw new IllegalArgumentException();

        if (anchortype.getName() == "Riem" && !(item instanceof Purse))
            throw new BeltAnchorException();

        Anchor anchor = null;

        ArrayList<Anchor> list = getAnchors();
        for (int i = 0; i < list.size(); i++) {
            Anchor curranchor = getAnchorAt(i);
            if (curranchor.getAnchorType() == anchortype) {
                if(curranchor.getItem() == null){
                    anchor = curranchor;
                    break;
                }
            }
        }

        if(anchor == null)
            throw new IllegalArgumentException();

        if (anchor.getItem() != null)
            throw new AnchorslotOquipiedException();

        int weight = item.getWeight();
        if(item instanceof Backpack)
            weight = ((Backpack) item).getTotalWeight();

        if (weight > getCapacity())
            throw new CarryLimitReachedException(item);

        item.equip(anchor);
        ChangeCapacity(weight);
    }

    /**
     * Drops an item that has been picked up back on the ground.
     *
     * @param equipable
     *        The equipable to be dropped
     *
     * @effect if the given equipable is currently being stored in a backpack then
     *         the item is removed from the content of that backpack. Else, if the item is being stored
     *         in an anchor, the item is removed from the anchor.
     *         |if(equipable.getParentbackpack != null){
     *         |  equipable.getParentback.removeEquipable(equipable)
     *         |}
     *         |else{
     *         |    itemanchor.setItem(null)
     *         |}
     * @effect The holder of the item is set to null.
     *         |equipable.setHolder(null)
     * @effect The maximum carry capacity for this creature is updated to account for the removed weight of the item.
     *         If the removed item is a backpack with content,
     *         the contents of this backpack are also considered for the calculation of the removed weight for the item.
     *         |totalWeight = equipable.getWeight()
     *         |if(equipable instanceof Backpack)
     *         |    totalWeight = ((Backpack) equipable).getTotalWeight();
     *         |ChangeCapacity(-totalWeight);
     *
     * @throws IllegalArgumentException
     *         The equipable item is not effective
     *         |equipable == null
     * @throws OtherPlayersItemException
     *         The holder of the item isn't the creature that executes this method.
     *         |equipable.getHolder() != this
     */
    @Raw
    public void drop(Equipable equipable) throws IllegalArgumentException, OtherPlayersItemException{
        if(equipable == null)
            throw new IllegalArgumentException();
        if(equipable.getHolder() != this)
            throw new OtherPlayersItemException();


        Anchor itemanchor = null;

        for(int i =0; i<getAnchors().size();i++){

            Equipable currItem = getAnchorItemAt(i);

            if(currItem == equipable){
                itemanchor = getAnchorAt(i);
                break;}

            else if(currItem instanceof Backpack){
                if(((Backpack) currItem).contains(equipable)){
                    ((Backpack) currItem).removeEquipable(equipable);
                    break;
                }
            }
        }

        if(itemanchor != null)
            itemanchor.setItem(null);

        equipable.setHolder(null);

        int totalWeight = equipable.getWeight();
        if(equipable instanceof Backpack)
            totalWeight = ((Backpack) equipable).getTotalWeight();
            //all the items in the backpack also have no owner anymore.
            for(Equipable item : ((Backpack) equipable).getAllItems()){
                item.setHolder(null);
            }

        ChangeCapacity(-totalWeight);
    }

    /**
     * Drops the item currently stored in the specified Anchor.
     *
     * @param anchor
     *        the anchor of which we want to remove the item.
     *
     * @effect The item in the given anchor gets dropped
     *         |drop(anchor.getItem())
     *
     * @throws IllegalArgumentException
     *         The equipable item is not effective
     *         |equipable == null
     * @throws OtherPlayersItemException
     *         The specified anchor is an anchor of another player or the item in the anchor belongs to another player
     *         |(anchor.getowner != this) || (anhor.getItem.getHolder != this)
     */
    @Raw
    public void dropItemAtAnchor(Anchor anchor) throws IllegalArgumentException, OtherPlayersItemException {
        drop(anchor.getItem());
    }

    // dit mag eigenlijk weg.
    /**
     * Drops all the items that this creature currently has equiped in its anchors.
     *
     * @effect Each anchor that has a non-null item, will drop its item.
     *         |if (getAnchorAt(i).getItem() != null)
     *         |    dropItemAtAnchor(getAnchorAt(i));
     */
    @Raw
    public void dropAllItems() {
        for(int i=0; i < getAnchors().size();i++){
            if (getAnchorAt(i).getItem() != null) {
                try {
                    dropItemAtAnchor(getAnchorAt(i));
                } catch (OtherPlayersItemException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Generates a value to see if the attack will hit.
     * @return A random integer between 0 and 100.
     *         | result == rand.nextInt(101)
     */
    @Model
    protected int getHitValue(){
        Random rand = new Random();
        int randomNum = rand.nextInt(101);
        return randomNum;
    }
    /**
     * @return The total damage including weapons and intrinsic damage.
     */
    protected abstract int getTotalDamage();

    /**
     * @return The total protection stat including armor and intrinsic protection.
     */
    protected abstract int getTotalProtection();

    /**
     * The creature dies and unequips all his items that can now be looted.
     * @return  a list that contains all the items of the dead creature.
     * @effect  all the items that the dead creature owned will be unequiped.
     *          |for(item in items of creature)
     *          |        unequip(item)
     */
    protected ArrayList<Equipable> die(){
        ArrayList<Equipable> items = new ArrayList<Equipable>();
        for(Anchor anchor : getAnchors()){
            if(anchor.getItem() != null){
                if(anchor.getItem() instanceof Backpack){
                    ArrayList<Equipable> itemsInBackpack = ((Backpack) anchor.getItem()).getAllItems();
                    for(Equipable item : itemsInBackpack){
                        items.add(item);
                    }
                }
                items.add(anchor.getItem());
                try {
                    drop(anchor.getItem());
                } catch (OtherPlayersItemException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return items;
    }

    /**
     * Makes a creature loot and/or heal after a won battle.
     * @param items
     *        The items that can be looted.
     */
    protected abstract void LootAndHeal(ArrayList<Equipable> items) throws ItemNotEquipedException, ItemAlreadyobtainedException, CarryLimitReachedException, AnchorslotOquipiedException;

    public void Hit(Creature creature) throws ItemNotEquipedException, ItemAlreadyobtainedException, CarryLimitReachedException, AnchorslotOquipiedException {
        if(getHitValue() >= getTotalProtection()){
            creature.setHitPoints(creature.getHitPoints() - getTotalDamage());
            }
        if(creature.getHitPoints() <= 0){
            creature.setHitPoints(0);
            LootAndHeal(creature.die());
        }
    }
}
