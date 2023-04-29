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
     *        the number to be checked
     * @return False if the number is not prime
     *         |for(i< num/2)
     *         | if(num%i ! 0)
     *         |then result == False
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

    /**
     * A final parameter referencing the maximum protectionvalue of an armorpiece
     */
    private final ArmorType maxprotection;

    /**
     * A variabele referencing the current protection a armorpiece provides.
     * This value changes with time.
     */
    private int actualarmor = 1;

    /**
     *
     * Returns the armortype of this armor
     */
    @Immutable
    private ArmorType getArmorType(){
        return maxprotection;
    }

    /**
     *
     * Returns the maximum protectionvalue of the armortype of this armor.
     */
    private int getMaxProtection(){return getArmorType().getMaxvalue();}

    /**
     * Change the protection of this armor to the given delta.
     *
     * @param delta
     *        the amount of protection by which the armors protecion must be increased or decreased
     *
     * @pre The given delta must not be 0
     *      |delta !=0
     * @effect The protectionvalue of this armor is adapted with the given delta.
     *         | setActualarmor(getActualarmor()+delta)
     * @effect The time when this armor was last checked is updated.
     *         The effects of wear in time are accounted for here.
     *         |checkarmor()
     */
    private void changeArmor(int delta){
        setActualarmor(getActualarmor()+delta);
        checkarmor();
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
     *          | changeArmor(-delta)
     */
    public void DecrementArmor(int delta){
        changeArmor(-delta);
    }

    /**
     * Checks if the actual armorvalue should be updated to account for degradation
     * through time.
     *
     * if the new armorvalue is valid, the armorvalue is updated to the new armorvalue.
     * if the new armorvalue is not valid and greater then the maximum armorvalue, the
     * new armorvalue is set to the maximum protectionvalue for this armor. If
     * the new armorvalue is smaller then 1, the new armorvalue is set to 1. Rendering
     * it basically useless.
     *
     * |if(!isValidArmorValue(newactualarmorvalue))
     * |  if(newactualarmorvalue > getMaxProtection())
     * |      then actualarmorvalue = setActualarmor(getMaxProtection.value)
     * |  else actualarmorvalue = setActualarmor(1)
     * |then setActualarmor(newactualarmorvalue)
     */
    private void checkarmor() {
        int newactualarmorvalue = (int) (getActualarmor() - getWear());
        if(!isValidArmorValue(newactualarmorvalue))
            if(newactualarmorvalue > getMaxProtection())
                setActualarmor(getMaxProtection());
            else setActualarmor(1);
        else setActualarmor(newactualarmorvalue);
    }


    /**
     * Set the actual armorvalue to the given value
     *
     * @param actualarmor
     *        the new actual armorvalue
     */

    private void setActualarmor(int actualarmor)  {
        this.actualarmor = actualarmor;
    }

    /**
     *
     * Returns the old actual armorvalue
     */

    private int getActualarmor(){
        return this.actualarmor;}

    /**
     *
     * Returns the actual armorvalue, taking the effects of wear through time into account.
     */

    public int getCurrentArmor(){
        checkarmor();
        return getActualarmor();
    }

    /**
     * Checks whether this armor can have the given value as its protectionvalue.
     * @param value
     *        The value to be checked
     * @return False if the given value is smaller than 1 or
     *               if the given value is greater than the maximum protection value for this armor.
     *        if(value < 1 || value > getMaxProtection()) then result == False
     */


    public boolean isValidArmorValue(int value){
        return((value >= 1) && (value <= getMaxProtection()));
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

    /**
     * Set the time the armor was last checked to the given value;
     *
     * @param date
     *        the new date .
     */
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

    /**
     * Returns the amount of hours that have passed since this method was last called.
     * If the amount of hours is greater than one, the old time is updated to the new time.
     */
    private long getWear(){
        Date now = new Date();

        long difference = (now.getTime() - checkTime.getTime()) / (1000*60*60);

        if(difference >= 1)
            setCheckTime(now);

        return difference;
    }

}



