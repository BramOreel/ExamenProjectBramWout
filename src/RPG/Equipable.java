package RPG;

import java.util.UUID;

/**
 * An abstract class of equipable items of heroes and monsters
 *
 * @invar Each equipable item must have a valid identification number.
 *        | canHaveAsId(getId())
 *
 * @invar Each equipable item must have a valid weight
 *        | canHaveAsWeight(getWeight())
 *
 * @invar Each equipable item must have a allowed value
 *        | isValidValue(getValue())
 *
 */
public abstract class Equipable {


    /**
     * Variable listing the Identification Number of the item.
     */
    private long Id;


    /**
     * CONSTRUCTORS
     */

    /**
     * Initialize a new Equipable item with a given weight and holder
     */
    protected Equipable(int weight){
        if(weight > 0)
        this.weight = weight;
        else this.weight = 5;
    }

    /***********************
     * Identification
     */

    /**
     *
     * Return the Id of the equipable item
     */
    public long getId() {
        return Id;
    }

    /**
     * Sets the id of this equipable item to the given id.
     * @param id
     *        the unique id for an equipable item
     * @post If the given id is valid, which it should be at this point in the code, this equipable item is set to the given id,
     *       otherwise the id is set to zero. At this point uniqueness cannot be guaranteed.
     *       | if (canHaveAsId(id))
     * 	     | then new.getId().equals(id)
     * 	     | else new.getId().equals(0)
     *
     *
     */
    protected final void setId(long id) {
        if(canHaveAsId(id))
            Id = id;
        else Id = 0;


    }

    /**
     * Check whether the equipable item can have the given id as its id
     * @param id
     *        the id to be checked
     * @return False if the id has a negative value or is greater then the maximum integer value.
     *         | if(id < 0 || id > Integer.MAX_VALUE)
     *         | then result == false
     * @note This checker only checks the common rule between all the subclasses. Whether or not the id is unique is resolved by the subclasses.
     *
     */
    protected boolean canHaveAsId(long id){
       return(id >= 0 && id < Integer.MAX_VALUE);

    }


    /*********************************
     * Weight
     */

    /**
     * a final variable containing the weight of an Equipable item in kilograms
     */
    private final int weight;

    /**
     *
     * Returns the weight of this equipable
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Checks if an equipable item can have this value as its weight.
     *
     * @param weight
     *        the weight of the equipable item
     * @return True if the value is greater than zero
     *         |if(weight > 0) then result == True
     */
    public boolean canHaveAsWeight(long weight){
        return(weight > 0);
    }

    /************
     * Value
     */

    /**
     * A variable containing the weight of an Equipable item in 'dukaten'
     */
    private int value = 1;

    /**
     *
     * Returns the value of the equipable item
     */
    public int getValue() {
        return value;
    }

    /**
     *
     * @param value
     *        the new value for this equipable item
     */
    protected void setValue(int value) {
        this.value = value;
    }

    /**
     * Checks whether an equipable item can have this value as its value.
     *
     * @param value
     *        the value to be checked
     *
     * @return False if the given value is smaller than one.
     *        |if(value < 1) then result == False
     */
    protected boolean isValidValue(int value){
        return(value >= 1);
    }
}



