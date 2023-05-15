package RPG;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class for signaling illegal attempts to equip or store an item has already been equipped.
 *
 * @author 	Wout Thiers & Bram Oreel
 * @version 1.0
 */
public class ItemAlreadyobtainedException extends Exception{
    /**
     * The item that is already equipped.
     */
    private final Equipable item;

    /**
     * generates a new exception
     * @param item
     *        The item that is already equipped.
     * @post  The given item is set as the item involved in the exception.
     *        |this.item == item;
     */
    public ItemAlreadyobtainedException(Equipable item){
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
