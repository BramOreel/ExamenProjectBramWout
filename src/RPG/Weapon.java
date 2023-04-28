package RPG;

import java.security.SecureRandom;
import java.util.Random;

/**
 * A class for weapons within an RPG
 *
 * @invar the damage of a weapon must be valid
 *        |isValidDamage(getWeapon())
 */
public class Weapon extends Equipable{

    /**
     * CONSTRUCTORS
     */

    public Weapon(int weight, int damagevalue){
        super(weight);
        setId(getIdcounter());
        incrementId();
        setDamage(damagevalue);

    }

    public Weapon(int weight){
        super(weight);
        setId(getIdcounter());
        incrementId();
        generateRandomDamage();

    }

    /**
     * A static variable referencing the current idvalue. This value must always be divisible by three and even.
     */
    private static long idcounter = 36936918;

    /**
     *
     * Returns the current value of the idcounter
     */
    private static long getIdcounter() {
        return idcounter;
    }

    /**
     * Sets the idcounter to the specified value.
     * @param idcounter
     *        the value of the next, unused id.
     * @post If the given id is unique and valid, this weapon id is set to the given id, otherwise the id is set to zero.
     *       This should never happen however, seeing that the weapon id is always internally generated.
     *       | if(canHaveAsId(id))
     * 	     | then new.getId().equals(id)
     * 	     | else new.getId().equals(0)
     */
    private void setIdcounter(long idcounter) {
        if(canHaveAsId(idcounter))
            Weapon.idcounter = idcounter;
        else Weapon.idcounter = 0;
    }

    /**
     * Increments the value of the idcounter by 6. Thus guaranteeing it will always be divisible by two and three.
     */
    private void incrementId(){
        setIdcounter(getIdcounter() + 6);
    }

    /**
     * check if the weapon can have the given id as its id.
     * @param idcounter
     *        the idcounter to be checked
     *
     * @return False if the idcounter is negative, greater than the maximum integer value, not divisible by two or not divisble by three.
     *         | if(idcounter < 0 || idcounter > Integer.MAX_VALUE || idcounter % 2 !=0 || idcounter % 3 !=0 )
     *         | then result == false
     */
    @Override
    protected boolean canHaveAsId(long idcounter){
        return(super.canHaveAsId(idcounter) && idcounter % 2 == 0 && idcounter % 3 ==0);


    }

    /*********************
     * Damage
     *********************/

    /**
     * A constant representing the current maximum damagevalue for weapons
     */
    static final int DAMAGE_MAXVALUE = 100;

    /**
     * A variabele referencing the damage of a weapon
     */
    private int damage = 7;

    /**
     *
     * Return the damage of this weapon
     */
    public int getDamage() {
        return damage;
    }

    /**
     *
     * Return the Maximum damagevalue of all weapons
     */
    public static int getDamageMaxvalue(){
        return Weapon.DAMAGE_MAXVALUE;
    }

    /**
     * Sets the damage to the specified value
     *
     * @param damage
     *        the new damage for this weapon
     *
     * @pre The given damage must be allowed
     *      |isValidDamage(damage)
     *
     * @post The given damage is registered as the damage of the weapon
     *       |new.getDamage() == damage
     *
     */
    private void setDamage(int damage) {this.damage = damage;}

    /**
     * Check whether the given damage is a valid damageNumber
     * @param damage
     *        the damage to check
     * @return True if the damage is greater than 1, smaller than the listed maximum value and a multiple of seven
     *         |result == ((damage > 1) && (damage < getDamageMaxvalue()) && (damage % 7 == 0));
     */
    private boolean isValidDamage(int damage){
        return((damage > 1) && (damage < getDamageMaxvalue()) && (damage % 7 == 0));
    }


    private void generateRandomDamage(){
        SecureRandom R = new SecureRandom();
        int upperbound = Math.floorDiv(getDamageMaxvalue(),7) - 1;

        int int1 = R.nextInt(upperbound);
        int1 = (7 * int1) + 7;
        setDamage(int1);
    }

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
    private void changeDamage(int delta){
        setDamage(getDamage()+delta);
    }

    /**
     * Increases the damage of this weapon with the given delta.
     *
     * @param   delta
     *          The amount of damage by which the damage of this weapon
     *          must be increased.
     * @pre     The given delta must be strictly positive.
     *          | delta > 0
     * @effect  The damage of this weapon is increased with the given delta.
     *          | changeDamage(delta)
     */
    public void EnhanceDamage(int delta){
        changeDamage(delta);
    }
    /**
     * Decreases the damage of this weapon with the given delta.
     *
     * @param   delta
     *          The amount of damage by which the damage of this weapon
     *          must be decreased.
     * @pre     The given delta must be strictly positive.
     *          | delta > 0
     * @effect  The damage of this weapon is decreased with the given delta.
     *          | changeDamage(delta)
     */
    public void DecrementDamage(int delta){
        changeDamage(-delta);
    }







}