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

    /**
     * Calculates the maximum capacity with the given strength.
     * @param strength
     *        the strength of the character.
     * @return The maximum capacity of the hero based on the rules,
     *         the maximum capacity is 20 times the strength, rounded off an integer.
     *         | maxcapacity == Math.round(strength * 20)
     *
     */
    public int calculateMaxCapacity(double strength){
        return (int) Math.round(strength * 20);
    }

    /**
     * Generates a new Hero with a name, a maximum amount of hitpoints, a given strength stat and a given protetection stat.
     * @param name
     *        The given name of the new Hero
     * @param maxHitPoints
     *        The given maximum amount of hitpoints the Hero can have
     * @param strength
     *        The given strength the hero has
     * @param protection
     *        The given protection stat the hero has.
     *@effect The maxcapacity is calculated with the given strength and then set as the strength.
     *        |setMaxCapacity(calculateMaxCapacity(strength));
     *@effect The Hero is generated as a creature with a given name, maxHitPoints and the calculated maxCapacity.
     *        | super(name, maxHitPoints, maxCapacity)
     *@effect The protection is set as the protection.
     *        | setProtection(protection)
     *@effect The strength is set as the strength.
     *        | setStrength(strength)
     */

    public Hero(String name, int maxHitPoints, double strength, float protection) {
        super(name, maxHitPoints,0);
        setMaxCapacity(calculateMaxCapacity(strength));
        setProtection(protection);
        setStrength(strength);
    }

    /**
     * Generates a new Hero with a name, a maximum amount of hitpoints, a given strength stat and the default protetection stat.
     * @param name
     *        The given name of the new Hero
     * @param maxHitPoints
     *        The given maximum amount of hitpoints the Hero can have
     * @param strength
     *        The given strength the hero has
     *@effect The hero is generated as a hero with the default protection stat.
     *        | this(name,maxHitPoints,strength,getDefaultProtection())
     */
    public Hero(String name, int maxHitPoints, double strength){
        this(name,maxHitPoints,strength,getDefaultProtection());
    }
}


