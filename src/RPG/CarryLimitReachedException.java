package RPG;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class for signaling illegal attempts to equip or store an item that would exceed the carry limit.
 *
 * @author 	Wout Thiers & Bram Oreel
 * @version 1.0
 */
public class CarryLimitReachedException extends Exception{
    /**
     * The item that exceeds the carry limit.
     */
    private final Equipable item;

    /**
     * Generates a new exception.
     * @param item
     *        The item that would exceed the carry limit
     * @post  the given item is set as the item involved in the exception.
     *        |this.item == item
     */
    public CarryLimitReachedException(Equipable item){
        this.item = item;
    }

    /**
     * returns the item involved in the exception.
     */
    @Basic @Immutable
    public Equipable getItem() {
        return item;
    }
}
