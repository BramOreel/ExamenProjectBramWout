package RPG;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

import java.security.SecureRandom;

/**
 * A class for weapons within an RPG
 *
 * @invar the damage of a weapon must be valid
 *        |isValidDamage(getWeapon())
 *
 * @author Bram Oreel
 * @version 1.2.
 */
public class Weapon extends Equipable{

    /**
     * CONSTRUCTORS
     */
    /**
     * Initialize a new weapon with given weight and damage value.
     *
     * @param weight
     *        The weight of the weapon in kilograms
     * @param damagevalue
     *        The damage value for this weapon
     *
     * @pre damagevalue is a valid damagevalue
     *      |isValidDamage(damagevalue)
     *
     * @effect A unique identification number is generated for this weapon
     *         |setId(getIdcounter())
     * @effect The id counter is incremented to the next allowed value and updated for all weapons
     *         |incrementId()
     * @effect The parameter which calculates the value of this weapon, based on the weapons damage
     *         is set to true.
     *         |dmgvallink = true
     * @effect The new weapon is an equipable with the given weight
     *         |super(weight)
     * @effect The value for this weapon is calculated using the weapons damage and set to the calculated value
     *         |ModifyDamage(damagevalue)
     *
     * @post The weapon has the given damage as its damage value.
     */
    @Raw
    public Weapon(int weight, int damagevalue){ // value gegenereerd
        super(weight);
        setId(getIdcounter());
        incrementId();
        this.dmgvallink = true;
        ModifyDamage(damagevalue);
    }

    /**
     * Initialize a new weapon with given weight and random damage.
     *
     * @param weight
     *        The weight of the new weapon in kilograms.
     *
     * @effect This new file is initialized with the given weight.
     *         The weapon has a random, allowed value.
     *         |this(weight, Weapon.generateRandomDamage())
     */
    @Raw
    public Weapon(int weight){
       this(weight, Weapon.generateRandomDamage());
    }

    /**
     * Initialize a new weapon with given weight, damage and value
     *
     * @param weight
     *        The weight of this weapon in kilograms
     * @param damagevalue
     *        The damage value of this weapon
     * @param value
     *        The value in 'dukaten' for this weapon.
     *
     * @pre damagevalue is a valid damagevalue
     *      |isValidDamage(damagevalue)
     *
     * @effect A unique identification number is generated for this weapon
     *         |setId(getIdcounter())
     * @effect The id counter is incremented to the next allowed value and updated for all weapons
     *         |incrementId()
     * @effect The parameter which calculates the value of this weapon, based on the weapons damage
     *         is set to false
     *         |dmgvallink = false
     * @effect The new weapon is an equipable with the given weight
     *         |super(weight)
     * @effect if the given value is valid, the weapon has the given value as its value.
     *         |setValue(value)
     *
     * @post The weapon has the given damage as its damage
     *
     * @throws IllegalArgumentException
     *         the given value is not postive or greater than 200
     *         |value < 0 || value > 200
     */
    @Raw
    public Weapon(int weight, int damagevalue, int value) throws IllegalArgumentException{
        super(weight);
        setId(getIdcounter());
        incrementId();
        this.dmgvallink = false;
        ModifyDamage(damagevalue);
        if(!canHaveAsValue(value))
            throw new IllegalArgumentException();
        setValue(value);
    }


    /**
     * A static final value referencing the maximum allowed value in dukaten for weapons.
      */
    private static final int MAXSELLVALUE = 200;

    /**
     * Returns the maximum sell value for this weapon.
     */
    @Basic
    @Override
    @Immutable
    public int getMAXSELLVALUE(){
        return MAXSELLVALUE;
    }



    /**
     * A static variable referencing the current idvalue. This value must always be divisible by three and even.
     */
    private static long idcounter = 36936918;

    /**
     *
     * Returns the current value of the idcounter
     */
    @Basic @Model
    private static long getIdcounter() {
        return idcounter;
    }

    /**
     * Checks if the equipable can be equiped by an anchor
     * @param anchor
     *        the anchor that would equip the equipable
     * @return True only if the anchortype of the anchor is body.
     */
    @Override
    public boolean isValidAnchor(Anchor anchor){
        return (anchor.getAnchorType() == AnchorType.RECHTERHAND || anchor.getAnchorType() == AnchorType.LINKERHAND||anchor.getAnchorType() == AnchorType.OTHER);
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
    @Model @Raw
    private void setIdcounter(long idcounter) {
        if(canHaveAsId(idcounter))
            Weapon.idcounter = idcounter;
        else Weapon.idcounter = 0;
    }

    /**
     * Increments the value of the idcounter by 6. Thus guaranteeing it will always be divisible by two and three.
     */
    @Model @Raw
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
    @Override @Raw @Model
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
    @Basic
    public int getDamage() {
        return damage;
    }

    /**
     *
     * Return the Maximum damagevalue of all weapons
     */
    @Basic @Immutable
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
    @Model @Raw
    private void setDamage(int damage) {this.damage = damage;}

    /**
     * Check whether the given damage is a valid damageNumber
     * @param damage
     *        the damage to check
     * @return True if the damage is greater than 1, smaller than the listed maximum value and a multiple of seven
     *         |result == ((damage > 1) && (damage < getDamageMaxvalue()) && (damage % 7 == 0));
     */

    public boolean isValidDamage(int damage){
        return((damage > 1) && (damage < getDamageMaxvalue()) && (damage % 7 == 0));
    }

    /**
     * Returns a random damage value between 1 and the maximum damagevalue which is a multiple of seven.
     */
    @Model @Raw
    private static int generateRandomDamage(){
        SecureRandom R = new SecureRandom();
        int upperbound = Math.floorDiv(getDamageMaxvalue(),7) - 1;

        int int1 = R.nextInt(upperbound);
        int1 = (7 * int1) + 7;
        return int1;
    }

    /**
     * Change the damage of the weapon to the given delta.
     *
     * @param delta
     *        the amount of damage by which the weapon damage must be increased or decreased
     *
     * @pre The given delta must be a multiple of seven and the old value, incremented with the given delta must be smaller than 100 and greater than 0.
     *      |delta % 7 == 0 && 0 < delta + getDamage() < 100
     * @effect The damage of this weapon is adapted with the given delta.
     *         | ModifyDamage(getDamage()+delta)
     * @effect If weapon damage and value are linked, the value is also modified
     *         |setValue((getDamage()+delta)*2)
     *
     * @post The weapon has the old damagevalue plus the given delta as its new damage value.
     *       |now.getDamage() = old.getDamage + delta
     *
     */
    @Model
    private void changeDamage(int delta){
        ModifyDamage(getDamage() + delta);
    }

    /**
     * Increases the damage of this weapon with the given delta.
     *
     * @param   delta
     *          The amount of damage by which the damage of this weapon
     *          must be increased.
     * @pre The given delta must be a multiple of seven and the old value, incremented with the given delta must be smaller than 100 and greater than 0.
     *      |delta % 7 == 0 && 0 < delta + getDamage() < 100
     *
     * @effect  The damage of this weapon is increased with the given delta.
     *          | changeDamage(delta)
     * @effect If the weapon damage and value are linked, the value is also modified with 2 times the given delta
     *          |setValue(getValue() + 2*delta)
     *
     * @post The weapon has the old damagevalue plus the given delta as its new damage value.
     *       |now.getDamage() = old.getDamage + delta
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
     * @pre The given delta must be a multiple of seven and the old value, incremented with the given delta must be smaller than 100 and greater than 0.
     *      |delta % 7 == 0 && 0 <  - delta + getDamage() < 100
     *
     * @effect  The damage of this weapon is decreased with the given delta.
     *          | changeDamage(delta)
     * @effect If the weapon damage and value are linked, the value is also modified with 2 times the given delta
     *          |setValue(getValue() - 2*delta)
     *
     * @post The weapon has the old damagevalue minus the given delta as its new damage value.
     *       |now.getDamage() = old.getDamage - delta
     *
     */

    public void DecrementDamage(int delta){
        changeDamage(-delta);
    }

    /**********
     * Value
     */

    /**
     * A final variabele stating if the damage and value of this weapon are linked
     */
    final private boolean dmgvallink;

    /**
     *
     * Returns true if damage and value of this weapon are linked
     */
    @Immutable @Basic
    public boolean isDmgValLink() {
        return dmgvallink;
    }

    /**
     * Modifies the damage of a weapon and its value if and only if damage and value are linked.
     *
     * @param damage
     *        the new damage for this weapon
     * @pre the damage for the weapon must be valid
     *      |isValidDamage(damage)
     *
     * @post the damagevalue is set to the given damage
     *      |setDamage(damage)
     * @effect if damage and value are linked,
     *             if the new value is valid the value of the weapon is updated to the new damage*2.
     *             Else if the new value is greater than 200, the new value is set to 200.
     *             Else if the new value is less than 1, the new value is set to 1.
     *        |if(isDmgValLink())
     *          value = getDamage()*2
     *          then: if(canHaveAsValue(value)
     *                  then setValue(value)
     *                else if(value > 200)
     *                 then  setValue(200)
     *                else if(value < 1)
     *                 then setValue(1)
     */
     @Model @Raw
    private void ModifyDamage(int damage){
        setDamage(damage);
        if(isDmgValLink()){
           int value = getDamage()*2;
           if(canHaveAsValue(value))
               setValue(value);
           else if(value > 200)
               setValue(200);
           else if(value < 1)
               setValue(1);
        }
    }
}
