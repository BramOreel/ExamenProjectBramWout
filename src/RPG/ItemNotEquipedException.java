package RPG;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class for signaling illegal attempts to unequip an item that is not equipped.
 *
 * @author 	Wout Thiers & Bram Oreel
 * @version 1.0
 */
public class ItemNotEquipedException extends Exception{
    /**
     * The item that is not equipped.
     */
    private final Equipable item;

    /**
     * generates a new exception
     * @param item
     *        The item that is not equipped.
     * @post  The given item is set as the item involved in the exception.
     *        |this.item == item;
     */
    public ItemNotEquipedException(Equipable item) {
        this.item = item;
    }

    /**
     * Returns the item involved in the exception.
     */
    @Basic
    @Immutable
    public Equipable getItem() {
        return item;
    }
}
