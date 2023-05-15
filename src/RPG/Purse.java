package RPG;

/**
 * A class for backpacks in an RPG.
 *
 * @author Bram Oreel & Wout Thiers
 * @version 1.0.
 */
public class Purse extends Equipable{
    /**
     * Generates a new purse.
     * @param weight
     *        the weight of the purse.
     * @effect The purse is generated as an equipable with the given weight.
     *         |super(weight)
     */
    private Purse(int weight){
        super(weight);
    }
}
