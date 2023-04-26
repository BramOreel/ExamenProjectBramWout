package RPG;

import java.util.UUID;

/**
 * An abstract class of equipable items of heroes and monsters
 *
 * @invar Each equipable item must have a valid identification number.
 *        | canHaveAsId(getId())
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

    public int getWeight() {
        return weight;
    }
}



