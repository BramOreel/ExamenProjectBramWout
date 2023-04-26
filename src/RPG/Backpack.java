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


    public Backpack(long id, int weight){
        super(weight);
        configure(id);
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
    public int getTotalWeight(){
        return 1;
    }


}




