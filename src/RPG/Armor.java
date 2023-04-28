package RPG;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.*;
import java.lang.Math;

/**
 * A class for Armors within an RPG
 *
 *
 * @invar Each armor must have a valid actual armorvalue
 *        |isValidArmorValue(getactualarmor());
 *
 * @invar Each armor must have a valid creationtime
 *        |isValidCreationTime(getCreationtime());
 *
 *
 */
public class Armor extends Equipable{


    /**
     * CONSTRUCTORS
     */


    public Armor(long id, int weight, ArmorType armortype){
        super(weight);
        configurePrime(id);
        this.maxprotection = armortype;
        setActualarmor(getMaxProtection());
    }




    /**
     * Static variable containing the set of previous Armor id's.
     *
     * @invar Each element in the set must be prime
     *        | for each long in set:
     *        | isPrime(set) == true
     */
    private static Set<Long> primeidSet = new HashSet<>();


    /**
     * checks if the armor can have the given id as its id
     * @param id
     *        the id to be checked
     * @return False if the idcounter is negative, greater than the maximum integer value, not prime or already an existant id for another armor.
     *         |if(idcounter < 0 || idcounter > Integer.MAX_VALUE || !isPrime(id) || primeidSet.contains(id) )
     *         | then result == false
     *
     */
    @Override
    protected boolean canHaveAsId(long id){
        return(super.canHaveAsId(id) && isPrime(id) && !primeidSet.contains(id));
    }

    /**
     * Checks if a number is prime or not
     *
     * @param num
     * @return
     */

    private boolean isPrime(long num)
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

    /**
     * Sets the id to the specified value.
     * @param id
     *        the value of the given id
     * @post If the given id is unique and prime, the armor id is set to the specified id,
     *       otherwise the id is set to the closest, bigger next prime integer.
     *       | if(canHaveAsId(id))
     * 	     | then new.getId().equals(id)
     * 	     | else new.getId().equals(closestBiggerPrime(id))
     */

    private void configurePrime(long id){

        while(!canHaveAsId(id)){
                id++;}

        setId(id);
        primeidSet.add(id);
        }

    /***********
     * Protection
     ***********/

    private final ArmorType maxprotection;

    private int actualarmor = 0;


    public ArmorType getArmorType(){
        return maxprotection;
    }

    private int getMaxProtection(){return getArmorType().getMaxvalue();}

    /**
     * Change the damage of the weapon to the given delta.
     *
     * @param delta
     *        the amount of damage by which the weapon damage must be increased or decreased
     *
     * @pre The given delta must not be 0
     *      |delta !=0
     * @effect The damage of this weapon is adapted with the given delta.
     *         | setDamage(getDamage()+delta)
     */
    private void changeArmor(int delta){
        setActualarmor(checkarmor()+delta);
    }

    /**
     * Increases the actualarmor of this armor with the given delta.
     *
     * @param   delta
     *          The amount of armor by which the actualarmor of this armor
     *          must be increased.
     * @pre     The given delta must be strictly positive.
     *          | delta > 0
     * @effect  The actualarmor of this armor is increased with the given delta.
     *          | changeArmor(delta)
     */
    public void RepairArmor(int delta){
        changeArmor(delta);
    }
    /**
     * Decreases the actualarmor of this armor with the given delta.
     *
     * @param   delta
     *          The amount of damage by which the actualarmor of this armor
     *          must be decreased.
     * @pre     The given delta must be strictly positive.
     *          | delta > 0
     * @effect  The actualarmor of this armor is decreased with the given delta.
     *          | changeArmor(delta)
     */
    public void DecrementArmor(int delta){
        changeArmor(-delta);
    }

    public int checkarmor() {
        int newactualarmorvalue = (int) (getActualarmor() - getWear());
        if(isValidArmorValue(newactualarmorvalue))
            setActualarmor(1);
        else setActualarmor(newactualarmorvalue);

        return getActualarmor();
    }

    private void setActualarmor(int actualarmor) {
        this.actualarmor = actualarmor;
    }

    private int getActualarmor(){return this.actualarmor;}

    public boolean isValidArmorValue(int value){
        return((value >= 1) && (value <= 100));
    }





    /**
     * Variable referencing the time the armor was last checked.
     */
    private Date checkTime = new Date();

    /**
     * Return the time at which this disk item was last checked.
     */
    @Raw
    @Basic
    public Date getCheckTime() {
        return checkTime;
    }


    private void setCheckTime(Date date){checkTime = date;}



    /**
     * Check whether the given date is a valid creation time.
     *
     * @param  	date
     *         	The date to check.
     * @return 	True if and only if the given date is effective and not
     * 			in the future.
     *         	| result ==
     *         	| 	(date != null) &&
     *         	| 	(date.getTime() <= System.currentTimeMillis())
     *
     * @note	This checker is object-independent (and thus static).
     *
     */
    public static boolean isValidChecktime(Date date) {
        return 	(date!=null) &&
                (date.getTime()<=System.currentTimeMillis());
    }

    private long getWear(){
        Date now = new Date();

        long difference = (now.getTime() - checkTime.getTime()) / (1000*60*60);

        if(difference >= 1)
            setCheckTime(now);

        return difference;
    }










}



