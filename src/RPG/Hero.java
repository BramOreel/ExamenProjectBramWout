package RPG;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import be.kuleuven.cs.som.annotate.Raw;

import java.text.DecimalFormat;

public class Hero extends Creature{

    /**
     * Generates a new Hero with a name, a maximum amount of hitpoints, a given strength stat and a given protetection stat.
     * Also initialises five Anchors of the anchor class
     *
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
     *@effect five empty anchors are initialised and set as the anchors of this hero. One left hand, One right hand, one back, one chest and one belt.
     *        |initialiseAnchors();
     */

    public Hero(String name, int maxHitPoints, double strength, float protection) {
        super(name, maxHitPoints, (int) (20*strength));
        setMaxCapacity(calculateMaxCapacity(strength));
        setProtection(protection);
        setStrength(strength);
        initialiseAnchors();
    }

    /**
     * Generates a new hero with items equiped.
     * @param name
     *        The given name of the new Hero
     * @param maxHitPoints
     *        The given maximum amount of hitpoints the Hero can have
     * @param strength
     *        The given strength the hero has
     * @param protection
     *        The given protection stat the hero has.
     * @param items
     *        The given items that the Hero has to equip.
     * @effect The Hero gets generated with the given name, maxhipoints, strength and protection.
     *        | this(name, maxHitPoints, strength, protection)
     * @effect All the given items get equiped in the free anchor slots, if there is no empty
     *         compatible anchor left the item does not get equiped.
     *         |for(Equipable item : items)
     *         |   for(Anchor anchor : getAnchors())
     *         |       if(item.isValidAnchor(anchor) && anchor.getItem() == null)
     *         |           item.equip(item)
     */
    public Hero(String name, int maxHitPoints, double strength, float protection, Equipable... items) {
        this(name, maxHitPoints, strength, protection);
        for(Equipable item : items){
            for(Anchor anchor : getAnchors()){
                if(item.isValidAnchor(anchor) && anchor.getItem() == null){
                    item.equip(anchor);
                }
            }
        }
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
     * Checks if a given name is valid.
     * @param name
     *        the given name that gets checked.
     * @return True if all the characters in the given name are valid characters, the name is not null
     *          ,the first character is a capital letter, it does not include more than two apostrophes and
     *          every colon is followed by a whitespace.
     *        | name.matches(validCharacters) && name != null && name.matches("^[A-Z].*") && apostrophecount < 3  && allColonsFollowedBySpace
     */
    @Raw
    @Override
    public boolean isValidName(String name){
        int apostrophecount = 0;
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == '\'') {
                apostrophecount++;
            }
        }
        Pattern pattern = Pattern.compile(":\\S");
        Matcher matcher = pattern.matcher(name);
        boolean allColonsFollowedBySpace = !matcher.find() && !(name.charAt(name.length() - 1) == ':');
        return (name.matches(validCharacters) && name != null && name.matches("^[A-Z].*") && apostrophecount < 3  && allColonsFollowedBySpace);
    }

    /**
     * Anchors
     */
    private void initialiseAnchors(){
        ArrayList<Anchor> list = new ArrayList<Anchor>();
        list.add(new Anchor(AnchorType.LINKERHAND,this));
        list.add(new Anchor(AnchorType.RECHTERHAND,this));
        list.add(new Anchor(AnchorType.RUG,this));
        list.add(new Anchor(AnchorType.LICHAAM,this));
        list.add(new Anchor(AnchorType.RIEM,this));
        setAnchors(list);
    }

}


