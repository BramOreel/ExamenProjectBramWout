package RPG;

import java.util.HashSet;
import java.util.Set;

/**
 * A class for backpacks within an RPG
 */
public class Backpack extends Equipable{

    /**
     * CONSTRUCTORS
     */


    public Backpack(long id, int weight, int value) throws IllegalArgumentException{
        super(weight);
        configure(id);
        if(!isValidValue(value))
            throw new IllegalArgumentException();
        setValue(value);

    }


    /**
     * Static variable containing the set of previous Backpack id's.
     *
     */
    private static Set<Long> idSet = new HashSet<>();


    /**
     * checks if the backpack can have the given id as its id
     * @param id
     *        the id to be checked
     * @return False if the idcounter is negative, greater than the maximum integer value or already an existant id for another backpack.
     *         |if(idcounter < 0 || idcounter > Integer.MAX_VALUE || idSet.contains(id))
     *         | then result == false
     *
     */
    @Override
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

    private void configure(long id){

        while(!canHaveAsId(id)){
            id++;}

        setId(id);
        idSet.add(id);
    }

    /***************
     * Content
     */

    /***********
     *Weight
     */


    /**
     *
     * Returns the total weight of this backpack
     */
    public int getTotalWeight(){
        return 1;
    }

    /**************
     * Value
     */


    /**
     *
     * Returns the total value of this backpack.
     */
    public int getTotalValue(){
        return 1;
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
    @Override
    public boolean isValidValue(int value){
        return(super.isValidValue(value) && value <= 500);
    }



}




