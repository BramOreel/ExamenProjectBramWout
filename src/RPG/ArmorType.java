package RPG;


import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * An enumeration of different armor types
 * In the current version, it is only possible to make armor of the 'default' type.
 * It is possible to add different armor types in future versions.
 *
 * @invar Each armor type must have a valid maximum protectionvalue
 *        |isValidProtValue(getMaxProtection());
 *
 * @author bramo
 * @version 1.0
 */

public enum ArmorType {

    DEFAULT(100), SCALE(90), TICK(80), TOUGH(70);

    /**
     * Initialize a new armor with given extension.
     *
     * @param Maxvalue
     *        the maximum value for this armortype
     * @pre  The provided Maxvalue is valid
     *       |isValidArmorValue(getMaxValue())
     *
     * @post The armortype is set to the given value.
     *       |this.maxvalue = Maxvalue;
     *
     *
     */
    private ArmorType(int Maxvalue){this.maxvalue = Maxvalue;}

    /**
     *
     * Return the maximum value of this armortype.
     */
    @Raw @Basic @Immutable
    public int getMaxvalue() {
        return this.maxvalue;
    }

    /**
     * A variable registering the maximum armorvalue of an armortype.
     */
    private final int maxvalue;

    /**
     * Checks if the maximum armorvalue is a allowed value.
     *
     * @param maxvalue
     *        the maximum value for this armortype
     * @return True if this value is not negative
     *         |result == (maxvalue > 0)
     */
    public static boolean isValidArmorValue(int maxvalue){return maxvalue > 0;}
}
