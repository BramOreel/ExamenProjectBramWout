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
 *          | canHaveAsHitPoints(getHitPoints())
 * @invar   Each creature must always have a valid maximum amount of hitpoints.
 *          | isValidMaxHitPoints(getMaxHitPoints())
 * @invar   Each creature must always have a valid amount of remaining capacity.
 *          | canHaveAsCapacity(getCapacity())
 * @invar   Each creature must always have a valid maximum capacity.
 *          | isValidMaxCapacity(getMaxCapacity())
 * @invar   Each creature must have proper anchors
 *          | hasProperAnchors(getAnchors)
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
     * variabele referencing if the creature is still alive
     */
    protected boolean isAlive = true;

    public boolean isAlive() {
        return isAlive;
    }

    private void setAlive(boolean alive) {
        isAlive = alive;
    }

    /**
     * a list containing all valid characters for names of creatures.
     */
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
     *        the given amount of weight to be added, can be negative.
     * @pre   the given amount must make the new capacity a valid amount.
     *        | canHaveAsCapacity(getCapacity() - capacity)
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
     *          | result == (maxCapacity > -1)
     */
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
     *          |result == (getMaxHitPoints() > hitPoints &&  hitPoints > -1)
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
    @Raw
    public void setName(String name) throws IllegalArgumentException{
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
     * Checks if the anchors are correctly set
     * @param anchors
     *        the list cotaining the anchors of the Hero.
     * @return True only if each of the anchors has this creature as the owner. False otherwise.
     *         |for (currentanchor in anchors)
     *         |    if(curranchor.getOwner() != this)
     *         |        then result == false
     *         |else:
     *         |result == true
     */
    public boolean hasProperAnchors(ArrayList<Anchor> anchors){
        for(Anchor curranchor : anchors){
            if (curranchor.getOwner() != this) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the list of anchors of this creature
     */
    @Basic @Model
    protected ArrayList<Anchor> getAnchors() {
        return anchors;
    }

    /**
     * Sets the list of anchors to the given list.
     * @param anchors
     *        The new anchors for this creature
     * @post  The anchors are set to the given list.
     *        | this.anchors == anchors
     */
    @Model @Raw
    protected void setAnchors(ArrayList<Anchor> anchors) {
        this.anchors = anchors;
    }

    /**
     *Returns the number of anchors for this creature
     */
    @Basic @Raw
    public int getNbOfAnchors(){
        return getAnchors().size();
    }


    /**
     * returns the anchor with index i in the arraylist of anchors.
     * @param i
     *        The given index for the anchor in the arraylist of anchors
     * @throws IllegalArgumentException
     *         the index i is out of range
     *         |i > getNbOfAnchors()
     * @return The anchor at the given index.
     *         |result == getAnchors().get(i)
     */
    @Basic @Raw
    public Anchor getAnchorAt(int i) throws  IllegalArgumentException{
        if(i >= getNbOfAnchors())
            throw new IllegalArgumentException();
        return getAnchors().get(i);
    }

    /**
     * Returns the item being stored in the anchor with given index i in the arraylist of anchors.
     * @param i
     *        The given index for the anchor in the arraylist of anchors.
     * @return The item of the anchor at the given index.
     *         |result == getAnchorAt(i).getItem()
     */
    @Raw @Basic
    public Equipable getAnchorItemAt(int i){
        return getAnchorAt(i).getItem();
    }

    /**
     * Picks an item up from the ground and equips it in an empty anchor with the given anchortype.
     *
     * @param item
     *        the item that will be picked up.
     * @param anchortype
     *        the type of the anchor where the item has to be equipped to.
     *
     * @effect The remaining capacity for this creature is updated to account for the extra weight of the item that was picked up.
     *         In case the item is a backpack, the contents of this backpack are also considered for the calculation
     *         of the weight of the item.
     *         |ChangeCapacity(-item.totalweight)
     * @effect The item gets picked up and the unidirectional relation between the item and the given anchor gets set up.
     *         The holder of the item is also set to the owner of the anchor and if it's a backpack this
     *         also happens for all the items within the backpack.
     *         |item.equip(anchor)
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
     * @throws AnchorslotOccupiedException
     *         The creature is already holding an item in the anchor with the given anchortype
     *         |anchor.getItem() != null
     * @throws CarryLimitReachedException
     *         The given item is can't be picked up because the creature cannot carry it anymore
     *         because the maximum carry capacity has been reached. In case the user wants to pick up a backpack,
     *         the contents of this backpack are also considered for the calculation of the weight of the item.
     *         |item.getTotalWeight > getCapacity
     * @throws IllegalCallerException
     *         the creature trying to pick up this item is dead.
     *         |this.isAlive() == false.
     */
    @Raw @Model
    protected void pickUp(Equipable item, AnchorType anchortype) throws ItemAlreadyobtainedException,IllegalArgumentException,
            AnchorslotOccupiedException, CarryLimitReachedException, BeltAnchorException, IllegalCallerException {
        if(!isAlive())
            throw new IllegalCallerException();
        if (item.getHolder() != null) {
            throw new ItemAlreadyobtainedException(item);
        }
        if (item == null)
            throw new IllegalArgumentException();

        if (anchortype == AnchorType.RIEM && !(item instanceof Purse))
            throw new BeltAnchorException(item);

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
            throw new AnchorslotOccupiedException(anchor);

        int weight = item.getWeight();
        if(item instanceof Backpack)
            weight = ((Backpack) item).getTotalWeight();

        if (weight > getCapacity())
            throw new CarryLimitReachedException(item);

        item.equip(anchor);
        ChangeCapacity(-weight);
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
     *         |if equipable.getParentbackpack != null
     *         |    then equipable.getParentback.removeEquipable(equipable)
     *         |else equipable.getAnchor().setItem(null)
     * @effect The holder of the item is set to null.
     *         |equipable.setHolder(null)
     * @effect The maximum carry capacity for this creature is updated to account for the removed weight of the item.
     *         If the removed item is a backpack with content,
     *         the contents of this backpack are also considered for the calculation of the removed weight for the item.
     *         |if(equipable instanceof Backpack)
     *         |then totalWeight = ((Backpack) equipable).getTotalWeight()
     *         |else totalWeight = equipable.getWeight()
     *         |ChangeCapacity(totalWeight)
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
            throw new OtherPlayersItemException(equipable);

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
        if(equipable instanceof Backpack) {
            totalWeight = ((Backpack) equipable).getTotalWeight();
            //all the items in the backpack also have no owner anymore.
            for (Equipable item : ((Backpack) equipable).getAllItems()) {
                item.setHolder(null);
            }
        }

        ChangeCapacity(totalWeight);
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
     *         The backpack is not effective or the specified backpack is not a backpack of this creature
     *         |(backpack.getHolder() != this || backpack == null)
     * @throws IllegalArgumentException
     *         The item is not effective or the specified item is not an item of this hero
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
     *         This creature already has this item
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
            throw new ItemAlreadyobtainedException(item);
        if(backpack.getHolder() != this)
            throw new OtherPlayersItemException(backpack);
        if(item.getHolder() != null)
            throw new OtherPlayersItemException(item);
        if(backpack.getCapacity() < backpack.getTotalWeight() + item.getWeight())
            throw new CarryLimitReachedException(item);
        if(item instanceof Backpack)
            if(((Backpack) item).getTotalWeight() != item.getWeight())
                throw new IllegalArgumentException();
        if(backpack.getParentbackpack() != null)
            throw new IllegalArgumentException();

        // het item in onze hand dat niet de rugzak is wordt even op de grond gelegd om een item op te pakken.
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
        }  catch (AnchorslotOccupiedException e) {
            throw new RuntimeException(e);
        }finally {
            //Het wapen wordt weer vastgenomen
            getAnchorAt(i).setItem(currholding);
        }
    }

    /**
     * Drops the item currently stored in the specified Anchor.
     *
     * @param i
     *        the index of the anchor of which we want to remove the item.
     *
     * @effect The item in the given anchor gets dropped
     *         |drop(anchor.getItem())
     *
     * @throws IllegalArgumentException
     *         The equipable item is not effective
     *         |anchor.getItem() == null
     * @throws IllegalArgumentException
     *         The given i is out of range
     *         |i>= this.getNbOfAnchors
     */
    @Raw
    public void dropItemAtAnchor(int i) throws IllegalArgumentException {
        if(i >= getNbOfAnchors())
            throw new IllegalArgumentException();
        Anchor anchor = getAnchorAt(i);
        if(anchor.getItem() == null)
            throw new IllegalArgumentException();

        try {
            drop(anchor.getItem());
        } catch (OtherPlayersItemException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Drops all the items that this creature currently has equipped in its anchors.
     *
     * @effect Each anchor that has a non-null item, will drop its item.
     *         |for(int i=0; i < getAnchors().size();i++)
     *         |    if (getAnchorAt(i).getItem() != null)
     *         |        then dropItemAtAnchor(i)
     */
    @Raw
    public void dropAllItems() {
        for(int i=0; i < getAnchors().size();i++){
            if (getAnchorAt(i).getItem() != null)
                dropItemAtAnchor(i);
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
     * @throws AnchorslotOccupiedException
     *         The anchor location does not exist or already has an item equiped in this slot
     *         |anchor.getItem() != null || getAnchors.contains(anchortype) == false
     */
    @Raw
    public void Equip(Equipable item, AnchorType location) throws IllegalArgumentException, OtherPlayersItemException, AnchorslotOccupiedException, BeltAnchorException{

        Backpack parent = item.getParentbackpack();
        if(parent == null)
            throw new IllegalArgumentException();
        if(parent.getHolder() != this)
            throw new OtherPlayersItemException(parent);
        if (location.getName() == "Riem" && !(item instanceof Purse))
            throw new BeltAnchorException(item);

        Anchor anchor = null;
        Anchor curranchor = getAnchorAt(0);
        for (int i = 0; i < getAnchors().size(); i++) {
            curranchor = getAnchorAt(i);
            if (curranchor.getAnchorType() == location) {
                if(curranchor.getItem() == null){
                    anchor = curranchor;
                    break;
                }
            }
        }
        if(anchor == null)
            throw new AnchorslotOccupiedException(curranchor);

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
     * @throws AnchorslotOccupiedException
     *         The endAnchor already has an item equiped to it
     *         |end.getItem() != null
     * @throws BeltAnchorException
     *         The user wants to equip an item that isn't a purse to the belt anchorslot of the creature.
     *         |anchortype.getName() == "Riem" && item not instanceof Purse
     * @throws IllegalArgumentException
     *         The user wants to move an item from or to an anchorpoint that is out of range for this creature.
     *         |start >= getNbOfAnchors() || end >= getNbOfAnchors()
     * @throws NullPointerException
     *         The item in the startanchor is null
     *         |startAnchor.getItem == null
     *
     */
    @Raw
    public void moveAnchorItemtoAnchor(int start, int end) throws AnchorslotOccupiedException, BeltAnchorException, IllegalArgumentException, NullPointerException{


        if(start >= getNbOfAnchors() || end >= getNbOfAnchors())
            throw new IllegalArgumentException();

        Anchor startanchor = getAnchorAt(start);
        Anchor endanchor = getAnchorAt(end);

        if(startanchor.getItem() == null)
            throw new NullPointerException();

        if(startanchor != endanchor){
            Equipable startitem = startanchor.getItem();


            if(endanchor.getItem() != null)
                throw new AnchorslotOccupiedException(endanchor);
            if (endanchor.getAnchorType().getName() == "Riem" && !(startitem instanceof Purse))
                throw new BeltAnchorException(startitem);

            startanchor.setItem(null);
            endanchor.setItem(startitem);
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
     * returns the total damage including weapons and intrinsic damage.
     */
    public abstract int getTotalDamage();

    /**
     * returns the total protection stat including armor and intrinsic protection.
     */
    @Model
    public abstract int getTotalProtection();

    /**
     * The creature dies and unequips all his items that can now be looted.
     * @return  a list that contains all the items of the dead creature.
     * @effect  all the items that the dead creature owned will be dropped.
     *          Backpacks will also be dropped but the items in the backpack will
     *          remain inside the backpack.
     *          |for(anchor in anchor.getAnchors)
     *          |        drop(anchor.getItem)
     * @effect The creature is marked as dead.
     *         |setAlive(false)
     * @return  All the items that the creature owned in a list. If he has a backpack equiped
     *          then the backpack as well as all the items within the backpack are returned in the list.
     *          |ArrayList<Equipable> items = new ArrayList<Equipable>()
     *          |for anchor in anchor.getAnchors
     *          |      if(anchor.getItem() instanceof Backpack)
     *          |          then for item in anchor.getItem()).getAllItems()
     *          |              items.add(item)
     *          |      items.add(anchor.getItem())
     *          | result == items
     */
    @Model
    protected ArrayList<Equipable> die(){
        setAlive(false);
        ArrayList<Equipable> items = new ArrayList<Equipable>();
        for(Anchor anchor : getAnchors()){
            if(anchor.getItem() != null){
                if(anchor.getItem() instanceof Backpack){
                    ((Backpack) anchor.getItem()).getAllItems();
                    for(Equipable item : ((Backpack) anchor.getItem()).getAllItems()){
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
    @Model
    protected abstract void LootAndHeal(ArrayList<Equipable> items) throws ItemNotEquipedException, ItemAlreadyobtainedException, CarryLimitReachedException, AnchorslotOccupiedException;

    /**
     * Hit another creature and if it kill the creature, loot the creature.
     * @param creature
     *        The creature that is getting hit
     * @effect A hit value is generated, if this is bigger than the protection of the creature getting
     *         hit then the damage is calculated and the remaining hitpoints of the creature is reduced with this value. However if after this the
     *         remaining hitpoints is less than
     *         |if(getHitValue() >= creature.getTotalProtection())
     *         |then creature.setHitPoints(creature.getHitPoints() - getTotalDamage())
     *         |if  creature.getHitPoints() <= 0
     *         |then creature.setHitPoints(0)
     * @throws IllegalArgumentException
     *         The given creature is already dead.
     *         |creature.isAlive() == false
     * @throws IllegalCallerException
     *         The creature trying to hit the given creature is dead.
     *         |this.isAlive() == false
     */
    public void Hit(Creature creature) throws IllegalArgumentException, IllegalCallerException {
        if(!this.isAlive())
            throw new IllegalCallerException();
        if(!creature.isAlive())
            throw new IllegalArgumentException();


        int remainingHP = creature.getHitPoints();
        if(getHitValue() >= creature.getTotalProtection()) {
            remainingHP = creature.getHitPoints() - getTotalDamage();
        }
        if(remainingHP <= 0){
            creature.setHitPoints(0);

            try {
                LootAndHeal(creature.die());
            } catch (ItemNotEquipedException e) {
                throw new RuntimeException(e);
            } catch (ItemAlreadyobtainedException e) {
                throw new RuntimeException(e);
            } catch (CarryLimitReachedException e) {
                throw new RuntimeException(e);
            } catch (AnchorslotOccupiedException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            creature.setHitPoints(remainingHP);
        }
    }
}
