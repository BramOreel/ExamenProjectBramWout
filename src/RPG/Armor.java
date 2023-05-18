package RPG;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import java.util.*;


/**
 * A class for Armors within an RPG
 *
 * @invar Each armor must have a valid protection value.
 *        |isValidArmorValue(getactualarmor());
 *
 * @invar Each armor must have a valid creationtime.
 *        |isValidCheckTime(getChecktime());
 *
 * @author Bram Oreel
 * @version 1.2.
 */
public class Armor extends Equipable{


    /**
     * CONSTRUCTORS
     */
    /**
     * Initialize a new armor with given identification number, weight, armortype and maximum value.
     * @param id
     *        The identification number for this armor
     * @param weight
     *        The weight in kilograms of the new armor
     * @param armortype
     *        The type of armor, picked from the available list for this armor
     * @param value
     *        The maximum value in dukaten for this armor.
     *
     * @effect The new armor is an equipable item with the given weight
     *         |super(weight)
     * @effect The identification number is configured so that it is unique, positive and prime
     *         |configurePrime(id)
     * @effect The actual armorvalue for this armor is set to the maximum armorvalue that the armortype provides.
     *         |setActualarmor(getMaxProtection())
     * @effect If the given value is valid, the value for this armor is set to the given value, else an exception is thrown
     *         |if(canHaveAsValue(value)) then this.value = value
     *
     * @post The armor has the given armortype as its maxprotection
     *
     * @throws IllegalArgumentException
     *         the given maximum value is smaller than 1 or bigger than 1000
     *         value < 1 || value > 1000
     */
    @Raw
    public Armor(long id, int weight, ArmorType armortype, int value) throws IllegalArgumentException{
        super(weight);
        configurePrime(id);
        this.maxprotection = armortype;
        setActualarmor(getMaxProtection());
        if(!canHaveAsValue(value))
            throw new IllegalArgumentException();
        this.maxvalue = value;
    }

    /**
     * A static final value referencing the maximum allowed value in dukaten for armors.
     */
    private static final int MAXSELLVALUE = 1000;

    /**
     * Returns the maximum sell value for this armor.
     */
    @Basic
    @Override
    @Immutable
    public int getMAXSELLVALUE(){
        return MAXSELLVALUE;
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
    @Override @Model @Raw
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
    @Model
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
    @Model @Raw
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
    @Immutable @Model @Basic
    private ArmorType getArmorType(){
        return maxprotection;
    }

    /**
     *
     * Returns the maximum protectionvalue of the armortype of this armor.
     */
    @Immutable @Model @Basic
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
    @Model
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
     * Checks if the actual protection value should be updated to account for degradation
     * through time and updates the value for this armor if needed.
     * if the new protection value is valid, the protection value is updated to the new protection value.
     * if the new protection value is not valid and greater than the maximum protection value, the
     * new protection value is set to the maximum protection value for this armor. If
     * the new protection value is smaller than 1, the new protection value is set to 1. Rendering
     * it basically useless.
     * |if(!isValidArmorValue(newactualarmorvalue))
     * |  if(newactualarmorvalue > getMaxProtection())
     * |      then actualarmorvalue = setActualarmor(getMaxProtection.value)
     * |  else actualarmorvalue = setActualarmor(1)
     * |then setActualarmor(newactualarmorvalue)
     *
     * @effect The value for this armor is updated to account for the degradation in time.
     *          |updateValue()
     *
     *
     */
    @Model @Raw
    private void checkarmor() {
        int newactualarmorvalue = (int) (getActualarmor() - getWear());
        if(!isValidArmorValue(newactualarmorvalue))
            if(newactualarmorvalue > getMaxProtection())
                setActualarmor(getMaxProtection());
            else setActualarmor(1);
        else setActualarmor(newactualarmorvalue);

        //Value
        updateValue();
    }

    /**
     * Set the actual armorvalue to the given value
     *
     * @param actualarmor
     *        the new actual armorvalue
     */
    @Model @Raw
    private void setActualarmor(int actualarmor)  {
        this.actualarmor = actualarmor;
    }

    /**
     * Returns the old actual armorvalue
     */
    @Model @Basic
    private int getActualarmor(){
        return this.actualarmor;}

    /**
     *
     * Returns the actual armorvalue, taking the effects of wear through time into account.
     */
    @Basic
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

    @Raw
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

    @Basic
    public Date getCheckTime() {
        return checkTime;
    }

    /**
     * Set the time the armor was last checked to the given time;
     *
     * @param date
     *        the new date that the armor was last checked for this armor.
     */
    @Model
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
     *
     * @effect If the amount of hours is greater than one, the old time is also updated to the new time.
     *         |if(difference >= 1)
     *         |    setCheckTime(now)
     */
    @Model
    private long getWear(){
        Date now = new Date();

        long difference = (now.getTime() - checkTime.getTime()) / (1000*60*60);

        if(difference >= 1)
            setCheckTime(now);

        return difference;
    }

    /*************
     * Value
     **************/

    /**
     * A variable stating the maximum value a piece of armor can have.
     */
    private final int maxvalue;

    /**
     *
     * Returns the maximum value of the armorpiece.
     */
    @Immutable @Basic
    public int getMaxvalue() {
        return maxvalue;
    }


    /**
     * Updates the protection value of the armorpiece to check if time has degraded it and returns a updated value for this armor
     * if time has indeed lessened the value of this armor.
     *
     * @effect If more than 1 hour has passed since the creation of this armor than
     *         the current protection value is decremented by 1 for each hour that has passed. Thus, the value of this armor also decreases.
     *         |checkarmor()
     * @return Returns the updated value for this armor.
     */
    @Override
    public int getValue(){
        checkarmor();
        return super.getValue();
    }

    /**
     * Calculates the new value for this armor. It does this by taking the fraction of the current armor value and the maximum armor value
     * and multiplying this with its maximum price.
     *
     * @effect The old value is updated to the new, calculated value.
     *         |setValue(newvalue)
     */
    @Model
    private void updateValue(){
        double fraction = (float) getActualarmor()/ (float) getMaxProtection();
        int newvalue = (int) (getMaxvalue()*fraction);
        setValue(newvalue);
    }

    /**
     * Checks if the equipable can be equiped by an anchor
     * @param anchor
     *        the anchor that would equip the equipable
     * @return True only if the anchortype of the anchor is body.
     */
    @Override
    public boolean isValidAnchor(Anchor anchor){
        return anchor.getAnchorType() == AnchorType.LICHAAM ||anchor.getAnchorType() == AnchorType.OTHER;
    }
}



