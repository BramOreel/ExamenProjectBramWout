package RPG;

import java.text.DecimalFormat;

public class Hero extends Creature{
    /**
     * variable stating how much protection the hero has, how easily he can dodge or deflect attacks.
     */
    protected float protection;
    /**
     * the default amount of protection for a hero
     */
    protected static final float defaultProtection = 10;
    /**
     * this static states how many decimal places a strenth variable can have.
     */
    private static final int decimalPlacesStrength = 2;
    /**
     * A variable stating the intrinsic strength of a Hero.
     */
    protected double Strength;

    /**
     * @return the intrinsic protection of the hero
     */
    public float getProtection() {
        return protection;
    }

    /**
     * @return the default protection of heroes
     */
    public static float getDefaultProtection() {
        return defaultProtection;
    }

    /**
     * @return the strenth of the hero
     */
    public double getStrength() {
        return Strength;
    }

    /**
     * Set the protection to a given number
     * @param protection
     *        the given protection
     * @post  If the given protection is a strictly positive number, then it will be set as the protection.
     *        If it is not, then the protection will be set to the default amount.
     *        | if(protection > 0):
     *        |     this.protection = protection
     *        | else:
     *        |     this.protection = getDefaultProtection()
     */
    public void setProtection(float protection) {
        if(protection > 0){
            this.protection = protection;
        }
        else{
            this.protection = getDefaultProtection();
        }
    }

    /**
     * Set a new strength and rounds it to the right amount of decimals.
     * @param strength
     *        the given strength
     * @post  The strength of the hero is set to the given number rounded off to the amount of decimal
     *        places given by decimalPlacesStrength.
     *        |this.Strength == Math.round(strength * Math.pow(10, decimalPlacesStrength)) / Math.pow(10, decimalPlacesStrength)
     */
    public void setStrength(double strength) {
        Strength = Math.round(strength * Math.pow(10, decimalPlacesStrength)) / Math.pow(10, decimalPlacesStrength);
    }
}
