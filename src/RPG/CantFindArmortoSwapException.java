package RPG;
/**
 * A class for signaling illegal attempts to swap an armor while there is no armor equipped in the Lichaam anchor.
 *
 * @author 	Wout Thiers & Bram Oreel
 * @version 1.0
 */
public class CantFindArmortoSwapException extends RuntimeException{
    /**
     * Generates a new exception signaling that there is no armor equipped to swap with.
     */
    public CantFindArmortoSwapException(){}
}
