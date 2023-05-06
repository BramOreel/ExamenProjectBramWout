package RPG;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public abstract class Creature {

    /**
     * Creates a new creature with a name, amount of hitpoints and capactity.
     * @param name
     *        The name of the new creature.
     * @param maxHitPoints
     *        The maximum amount of hitpoints of the creature.
     * @param maxCapacity
     *        The maximum capacity of the creature in kg.
     * @effect The new name is set to the name of the creature, if the name is not valid
     *         an IllegalArgumentException is thrown.
     *         |setName(name)
     * @effect The maximum capacity is set to the given maxCapacity.
     *         |setMaxCapacity(maxCapacity)
     * @effect The remaining capacity is set to the given maxCapacity,
     *          because the creature still has an empty inventory.
     *         |setCapacity(maxCapacity)
     * @effect The maximum amount of hitpoints is set to the given maxHitPoints.
     *         |setMaxHitPoints(maxHitPoints)
     * @effect The amount of hitpoints is set to the given maxHitPoints,
     *          so the creature has full health.
     *         |setHitPoints(maxHitPoints)
     */
    protected Creature(String name, int maxHitPoints, int maxCapacity) {
        setName(name);
        setMaxCapacity(maxCapacity);
        setCapacity(maxCapacity);
        setMaxHitPoints(maxHitPoints);
        setHitPoints(maxHitPoints);

    }



    protected ArrayList<Anchor> anchors = new ArrayList<Anchor>();
    /**
     * variable containing the name of the Creature
     */
    protected String name;

    /**
     * variable containing maximum amount of hitpoints
     */
    protected int maxHitPoints;

    /**
     * vatiable containing the amount of hitpoints
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
     * a list containing all valid characters for the name.
     */
    protected final static String validCharacters = "[a-zA-Z': ]+";

    /**
     * @return  the name of the creature
     */
    public String getName() {
        return name;
    }

    /**
     * @return  the maximum hitpoints of the creature.
     */
    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    /**
     * @return  the hitpoints of the creature.
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * @return the current remaining capacity of the creature.
     */
    public int getCapacity() {
        return capacity;
    }



    /**
     * @return the maximum capacity of the creature.
     */
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
    protected void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
    }
    /**
     * Sets a new given amount of hitpoints.
     * @param hitPoints
     *        the given amount.
     * @pre   the given amount must be a valid amount.
     *        | isValidHitPoints(hitPoints)
     * @post  the amount of hitpoints is now the given amount.
     *        | this.hitPoints == hitPoints
     */
    protected void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
    /**
     * Sets a new given remaining capacity.
     * @param capacity
     *        the given amount.
     * @pre   the given amount must be a valid amount.
     *        | isValidCapacity(capacity)
     * @post  the amount of remaining capacity is now the given amount.
     *        | this.capacity == capacity
     */
    protected void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Adds a remaining capacity.
     * @param capacity
     *        the given amount, can be negative.
     * @pre   the given amount must make the new capacity a valid amount.
     *        | isValidCapacity(getCapacity()-capacity)
     * @effect  the amount of remaining capacity is now the previous amount plus the given weigt.
     *        | setCapacity(getCapacity()+capacity)
     */
    protected void ChangeCapacity(int capacity) {
        setCapacity(getCapacity()+capacity);
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
    public boolean isValidMaxCapacity(int maxCapacity){
        return maxCapacity > -1;
    }

    /**
     * checks if the remaining capacity is a valid amount.
     * @param   capacity
     *          the remaining capacity that needs to be checked.
     * @return  True if the amount is a non-negative integer smaller than the maximum capacity, false if not.
     *          | getMaxCapacity() > capacity &&  capacity > -1
     */
    public boolean isValidCapacity(int capacity){
        return  getMaxCapacity() > capacity &&  capacity > -1;
    }

    /**
     * checks if the maxHitPoints is a valid amount.
     * @param   maxHitPoints
     *          the maximum amount of hitpoints that needs to be checked.
     * @return  True if the amount is a non-negative integer, false if not.
     *          | maxHitPoints > -1
     */
    public boolean isValidMaxHitPoints(int maxHitPoints){
        return maxHitPoints > -1;
    }

    /**
     * checks if the remaining amount of hitpoints is a valid amount.
     * @param   hitPoints
     *          the remaining amount of hitpoints that needs to be checked.
     * @return  True if the amount is a non-negative integer smaller than the maximum amount, false if not.
     *          | getMaxHitPoints() > hitPoints &&  hitPoints > -1
     */
    public boolean isValidHitpoints(int hitPoints){
        return  getMaxHitPoints() > hitPoints &&  hitPoints > -1;
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
    public boolean isValidName(String name){
        return name.matches(validCharacters) && name != null && name.matches("^[A-Z].*");
    }

    /**
     * Sets a new name.
     * @param  name
     *         The new name.
     * @throws IllegalArgumentException
     *         If the given name is not valid this exception is thrown.
     *         | !isValidName(name)
     * @post   The new name is set as the given name.
     *         | this.name == name
     */
    protected void setName(String name) throws IllegalArgumentException{
        if(!isValidName(name)){
            throw new IllegalArgumentException();
        }
        else {
            this.name = name;
        }
    }
    /********
     * Anchors
     */

    public ArrayList<Anchor> getAnchors() {
        return anchors;
    }

    protected void setAnchors(ArrayList<Anchor> anchors) {
        this.anchors = anchors;
    }

    /**
     * Gives the list of anchors
     * @return the list of anchors
     */


    public Anchor getAnchorAt(int i){
        return getAnchors().get(i);
    }

    public Equipable getAnchorItemAt(int i){
        return getAnchorAt(i).getItem();
    }

    /**
     * Mss nog compacter maken voor checker
     * @param item
     * @param anchortype
     * @throws ItemAlreadyobtainedException
     * @throws IllegalArgumentException
     * @throws AnchorslotOquipiedException
     * @throws CarryLimitReachedException
     */

    public void pickUp(Equipable item, AnchorType anchortype) throws ItemAlreadyobtainedException,IllegalArgumentException,
            AnchorslotOquipiedException, CarryLimitReachedException {
        if (item.getHolder() != null) {
            throw new ItemAlreadyobtainedException();
        }
        if (item == null) {
            throw new IllegalArgumentException();
        }

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
        setCapacity(getCapacity()-item.getWeight());
    }



    /**
     * Drops an equiped item
     * @param equipable
     */
    public void drop(Equipable equipable) throws IllegalArgumentException, OtherPlayersItemException{
        if(equipable == null)
            throw new IllegalArgumentException();
        if(equipable.getHolder() != this)
            throw new OtherPlayersItemException();


        Anchor itemanchor = null;
        boolean hasItem = false;
        for(int i =0; i<getAnchors().size();i++){

            Equipable currItem = getAnchorItemAt(i);

            if(currItem == equipable){
                itemanchor = getAnchorAt(i);
                hasItem = true;
                break;}

            else if(currItem instanceof Backpack){
                if(((Backpack) currItem).contains(equipable)){
                    ((Backpack) currItem).removeEquipable(equipable);
                    hasItem = true;
                    break;
                }
            }
        }
        if(hasItem == false)
            throw new IllegalArgumentException();

        if(itemanchor != null)
            itemanchor.setItem(null);

        equipable.setHolder(null);

        int totalWeight = equipable.getWeight();
        if(equipable instanceof Backpack)
            totalWeight = ((Backpack) equipable).getTotalWeight();

        ChangeCapacity(-totalWeight);


    }
    /**
     * Drops the item currently stored in the specified Anchor.
     */
    public void dropItemAtAnchor(Anchor anchor) throws OtherPlayersItemException {
        drop(anchor.getItem());
    }




    /**
     * Drops all items
     */
    public void dropAllItems() throws OtherPlayersItemException {
        for(int i=0; i < getAnchors().size();i++){
            dropItemAtAnchor(getAnchorAt(i));
        }
    }












    /**
     * Generates a value to see if the attack will hit.
     * @return A random integer between 0 and 100.
     *         | return rand.nextInt(101)
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
                items.add(anchor.getItem());
                anchor.getItem().unequip(anchor);
            }
        }
        return items;
    }

    /**
     * Makes a creature loot and/or heal after a won battle.
     * @param items
     *        The items that can be looted.
     */
    protected abstract void LootAndHeal(ArrayList<Equipable> items);

    public void Hit(Creature creature){
        if(getHitValue() >= getTotalProtection()){
            creature.setHitPoints(creature.getHitPoints() - getTotalDamage());
            }
        if(creature.getHitPoints() <= 0){
            creature.setHitPoints(0);
            LootAndHeal(creature.die());
        }
    }





}
