package RPG;

public class Monster extends Creature{
    /**
     * variable stating how much protection the monster has, how easily he can dodge or deflect attacks.
     */
    protected Armor naturalProtection;
    /**
     * Variable stating how much damage the monster does.
     */
    protected Weapon damage;

    /**
     * @return the natural protection of the monster.
     */

    public Armor getNaturalProtection() {
        return naturalProtection;
    }

    /**
     * @return the natural damage of the monster
     */

    public Weapon getDamage() {
        return damage;
    }

    /**
     * Sets a new given armor as the natural protection.
     * @param naturalProtection
     *        the armor that acts as the natural protection.
     */

    protected void setNaturalProtection(Armor naturalProtection) {
        this.naturalProtection = naturalProtection;
    }

    /**
     * Sets a new damage stat.
     * @param damage
     *        the new damage stat.
     */

    protected void setDamage(Weapon damage) {
        this.damage = damage;
    }

    /**
     *
     * @param name
     * @param maxHitPoints
     * @param maxCapacity
     * @param naturalProtection
     * @param damage
     */

    public Monster(String name, int maxHitPoints, int maxCapacity, Armor naturalProtection, Weapon damage) {
        super(name, maxHitPoints, maxCapacity);
        this.naturalProtection = naturalProtection;
        this.damage = damage;
    }
}
