package RPG;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * A class for backpacks within an RPG
 *
 * @invar Each backpack must have a valid capacity
 *        |isValidCapacity(capacity)
 *
 * @author Bram Oreel
 * @version 1.2.
 *
 */
public class Backpack extends Equipable{

    /**
     * Initialize a new backpack with given identification number, weight, value and capacity
     *
     * @param id
     *        The identification number for this backpack
     * @param weight
     *        The weight in kilograms of this new backpack
     * @param value
     *        The value in 'dukaten' of this new backpack
     * @param capacity
     *        The maximum capacity for this backpack in kilograms.
     * @effect The new backpack is an equipable with the given weight as its weight.
     *         |super(weight)
     * @effect The identification number is configured so that it is unique and positive
     *         |configure(id)
     * @effect If the given value is valid, the value for this backpack is set to the given value, else an exception is thrown.
     *         |if(isValidValue(value)) then this.value = value
     * @effect If the given maximum capacity is valid, the maximum capacity for this backpack is set to the given maximum capacity,
     *         else the capacity for this backpack is set to 30 kilograms.
     *         |if(canHaveAsCapacity(capacity))
     *         |    then this.capacity = capacity
     *         |else this.capacity = 30
     *
     * @throws IllegalArgumentException
     *         The given value is smaller than one or greater than 500.
     *         |value < 1 || value > 500
     */

    @Raw
    public Backpack(long id, int weight, int value, int capacity) throws IllegalArgumentException{
        super(weight);
        configure(id);
        if(!isValidValue(value))
            throw new IllegalArgumentException();
        setValue(value);
        if(!canHaveAsCapacity(capacity))
            this.capacity = 30;
        else this.capacity = capacity;
    }


    /**
     * Static variable containing the set of previous Backpack id's.
     */
    private static Set<Long> idSet = new HashSet<>();


    /**
     * checks if the backpack can have the given id as its id
     * @param id
     *        the id to be checked
     * @return False if the idcounter is negative, greater than the maximum integer value or already an existant id for another backpack.
     *         |if(idcounter < 0 || idcounter > Integer.MAX_VALUE || idSet.contains(id))
     *         | then result == false
     */
    @Override @Raw @Model
    protected boolean canHaveAsId(long id){
        return(super.canHaveAsId(id) && !idSet.contains(id));
    }

    /**
     * Sets the id to the specified value.
     * @param id
     *        the value of the given id
     * @post If the given id is unique, the backpack id is set to the specified id,
     *       otherwise the id is set to the closest, bigger integer.
     *       | if(canHaveAsId(id))
     * 	     | then new.getId().equals(id)
     * 	     | else new.getId().equals(closestBiggerInteger(id))
     */
    @Model @Raw
    private void configure(long id){

        while(!canHaveAsId(id)){
            id++;}

        setId(id);
        idSet.add(id);
    }

    /***************
     * Content
     **************/

    /**
     * A variable referencing a hashmap which contains the equipable items
     * contained by this backpack. The key of the hashmap references the id of the
     * equipable item. Because different equipable items can have the same id, the values in this hashmap are lists of equipables items
     * with the same id. Content can only be added in the constructor of Monster and through
     * the pickup method in the hero class and the drop and transfer methods in the creature class.
     *
     * @invar The total weight of the backpacks content cannot exceed its carrying capacity.
     *        |item.getWeight() != ((Backpack) item).getTotalWeight()
     * @invar content references an effective hashmap.
     *        |content != null
     * @invar Every key in the hashmap references an effective id
     *        |for each key in content
     *        |isValidId(key) && key !=null
     * @invar Every value in the hashmap references an effective arraylist
     *        |for each value in content
     *        |value != null
     * @invar The id of in every equipable item in the arraylist of each value in the hashmap is the correct
     *        id of the equipable item
     *        |for each list in content
     *        |  for each equipable in list
     *        |    equipable.getId() == content.key(listAtIndex(i))
     *
     * @note in this implementation, Creature or hero will be the controlling class for calling these methods
     */
    private HashMap<Long, ArrayList<Equipable>> content = new HashMap<Long, ArrayList<Equipable>>();

    /**
     * Returns the content of the hashmap
     */
    @Model
    @Basic
    protected HashMap<Long, ArrayList<Equipable>> getContent() {
        return content;
    }

    /**
     * Gives all the items in the backpack in a list.
     */
    public ArrayList<Equipable> getAllItems(){
        ArrayList<Equipable> AllItems = new ArrayList<Equipable>();
        for(ArrayList<Equipable> list : getContent().values()){
           for(Equipable item:list){
               AllItems.add(item);
           }
        }
        return AllItems;
    }






    /**
     * Checks if the content of this backpack contains an equipable item with the given id as its id.
     * @param id
     *        the id to be checked
     * @return True if the given id is stored as one of the keys in the content hashmap.
     *         |getContent().containsKey(id)
     */
    public boolean containsID(long id){
        return getContent().containsKey(id);
    }

    /**
     * Checks if the content of this backpack contains this equipable item.
     * @param item
     *        the item to be checked
     * @return True if the given item is stored in one of the arraylists, which are the values of the content hashmap.
     *         False if the id of the item is not stored as one of the keys in the content hashmap.
     *         else False
     *         |if(getContent().get(id).contains(item))
     *         |   return true;
     *         |else return false
     */
    public boolean contains(Equipable item){
        long id = item.getId();
        if(!containsID(id))
            return false;

        else if(getContent().get(id).contains(item))
            return true;

        else return false;

    }

    /**
     * Returns the total number of items stored in this backpack
     */
    public int getNbOfItems() {
        int total = 0;
        for (ArrayList<Equipable> list : getContent().values()) {
            total = total + list.size();
        }
        return total;
    }


    /**
     * Returns the number of armors stored in a backpack
     */
    public int getNbOfArmors(){
        int total = 0;
        for(ArrayList<Equipable> list : getContent().values()){
            for(int i=0; i<list.size(); i++){
                 if(list.get(i) instanceof Armor)
                     total++;
            }
        }
      return total;
    }

    /**************
     *Weight
     **************/

    /**
     * Returns the total weight of this backpack
     *
     * @return the sum of the total weight for each equipable item stored in this backpack plus the weight of the backpack itself.
     *          |result == sum {for(int i 0: content.size)
     *                              getItemAt(i).getTotalWeight} + this.getWeight()
     */
    public int getTotalWeight(){
        int sum = 0;
        for(ArrayList<Equipable> list : getContent().values()){
            for(int i=0; i<list.size(); i++){
                sum += list.get(i).getWeight();
            }
        }
        sum += this.getWeight();
        return sum;
    }

    /**************
     * Value
     **************/


    /**
     * Returns the total value of this backpack
     *
     * @return the sum of the total value for each equipable item stored in this backpack plus the value of the backpack itself.
     *          |result == sum {for(int i 0: content.size)
     *                              getItemAt(i).getTotalValue} + this.getValue()
     */
    public int getTotalValue(){
        int sum = 0;
        for(ArrayList<Equipable> list : getContent().values()){
            for(int i=0; i<list.size(); i++){
                sum += list.get(i).getValue();
            }
        }
        sum += this.getValue();
        return sum;
    }

    /**
     * Checks whether the given value is a valid value for this backpack
     * @param value
     *        the value to be checked
     *
     * @return False if the given value for this backpack is less than 1 or
     *               if the given value for this backpack is greater than 500.
     *         |if(value < 1 || value > 500) then result == False
     */
    @Override @Raw
    public boolean isValidValue(int value){
        return(super.isValidValue(value) && value <= 500);
    }


    /*********
     * Capacity
     */

    /**
     * A final variable referencing the maximum weight of all things this backpack can store in kilograms.
     */
    private final int capacity;

    /**
     * Checks if the given capacity is a valid capacity for this backpack.
     *
     * @param capacity
     *        the capacity value to be checked
     * @return True if the capacity is a positive value.
     */
    @Raw
    public boolean canHaveAsCapacity(int capacity){
        return(capacity >= 0);
    }

    /**
     * Returns the capacity for this backpack
     */
    @Basic @Immutable
    public int getCapacity() {
        return capacity;
    }

    /**
     * Add the given equipable item to the items registered in this backpack
     * and sets the indentification of the item as the key in the hashmap.
     * @param item
     *        The equipable item to be added.
     *
     * @effect The holder of the item is set to the current holder of this backpack.
     *         |item.setHolder(this.getHolder())
     * @effect The parent backpack of the equipable item is set to this backpack
     *         |item.setParentbackpack(this)
     * @post if the identification number is already used as an identification number,
     *       the list linked to the key is appended with the given item.
     *       if the identification number hasn't been used yet, a new entry is created.
     *       |  if(!containsID(id)){
     *       |      getContent().put(id, new ArrayList<Equipable>())
     *       |  getContent().get(id).add(item);
     *
     * @throws BackPackNotEmptyException
     *         The item to be added is a backpack that hasn't been emptied.
     *         |(Backpack) item.getWeight != (Backpack) item.getTotalWeight()
     * @throws CarryLimitReachedException
     *         The item cannot be added because the maximum carrying capacity has been reached.
     *         |this.getCapacity() < this.getTotalWeight()+item.getWeight()
     * @throws ItemAlreadyobtainedException
     *         The item is already being stored in this backpack
     *         |this.contains(item) == True
     * @throws OtherPlayersItemException
     *         The item is already being stored in another backpack
     *         |item.getHolder != null
     * @throws NullPointerException
     *         This backpack is not equiped by anyone
     *         |this.getHolder == null
     *
     */
    @Model
    protected void addEquipable(Equipable item) throws BackPackNotEmptyException, CarryLimitReachedException, OtherPlayersItemException, ItemAlreadyobtainedException,
            NullPointerException{
        if(item instanceof Backpack){
            if(item.getWeight() != ((Backpack) item).getTotalWeight())
                throw new BackPackNotEmptyException((Backpack) item);
        }
        if(getCapacity() < getTotalWeight()+item.getWeight())
            throw new CarryLimitReachedException(item);

        if(contains(item))
            throw new ItemAlreadyobtainedException();

        if(getHolder() == null)
            throw new NullPointerException();

        //Bij pickup in creature class kijken of creature niet al dood is.
        item.setHolder(this.getHolder());
        long id = item.getId();

        if(!containsID(id)){
            getContent().put(id, new ArrayList<Equipable>());
        }

        getContent().get(id).add(item);
        item.setParentbackpack(this);
    }

    /**
     * Remove the given equipable item from the content of this backpack.
     *
     * @param item
     *        The item to remove.
     * @effect The holder of the given item is set to null
     *         |item.setHolder(null)
     * @effect The parent backpack of the equipable item is set to null
     *         |item.setParentbackpack(null)
     * @post If the size of the arraylist, belonging to the id key of the content hashmap is equal to one,
     *       the entire entry is deleted, else only the item at the index where the equipable item was located is deleted,
     *       decrementing the size of the arraylist by one
     *       |getContent().get(id).remove(item);
     *       |if(getContent().get(id).size() == 0) {getContent().remove(id)}
     *
     * @throws IllegalArgumentException
     *         The item to be removed is not contained within the contents of content.
     *         |(this.contains(item) == False
     */
    @Model
    protected void removeEquipable(Equipable item) throws IllegalArgumentException{
        if(!contains(item))
            throw new IllegalArgumentException();
        item.setHolder(null);

        long id = item.getId();
        getContent().get(id).remove(item);

        if(getContent().get(id).size() == 0){
            getContent().remove(id);
        }
        item.setParentbackpack(null);
    }
}




