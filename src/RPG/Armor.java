package RPG;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.lang.Math;

/**
 * A class for Armors within an RPG
 */
public class Armor extends Equipable{


    /**
     * CONSTRUCTORS
     */


    public Armor(long id){

    }




    /**
     * Static variable containing the set of previous Armor id's.
     *
     * @invar Each element in the set must be prime
     *        | for each long in set:
     *        | isPrime(set) == true
     */
    private static Set<Long> primeid = new HashSet<>();


    /**
     * checks if the armor can have the given id as its id
     * @param id
     *        the id to be checked
     * @return False if the idcounter is negative, greater than the maximum integer value, not prime or already an existant id for another armor.
     *         |if(idcounter < 0 || idcounter > Integer.MAX_VALUE || !isPrime(id) || primeid.contains(id) )
     *         | then result == false
     *
     */
    @Override
    protected boolean canHaveAsId(long id){
        return(super.canHaveAsId(id) && isPrime(id) && !primeid.contains(id));
    }

    /**
     * Checks if a number is prime or not
     *
     * @param num
     * @return
     */

    static  boolean isPrime(long num)
    {
        if(num<=1)
        {
            return false;
        }
        for(int i=2;i<=num/2;i++)
        {
            if((num%i)==0)
                return  false;
        }
        return true;
    }


    private void configurePrime(long id){
        if(canHaveAsId(id))
            setId(id);
        else{
            double maxvalue = Math.pow(id, 0.535);
            while(id < maxvalue){
                id++;
                if(canHaveAsId(id))
                    setId(id);
            }

        }

    }


}
