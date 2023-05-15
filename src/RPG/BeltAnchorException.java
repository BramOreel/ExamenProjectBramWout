package RPG;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class for signaling illegal attempts to equip an item that is not a purse in an anchor with anchortype RIEM.
 *
 * @author 	Wout Thiers & Bram Oreel
 * @version 1.0
 */
public class BeltAnchorException extends RuntimeException {
    /**
     * The item that is attemted to be equipped in an anchor with anchortype RIEM
     */
    private final Equipable item;


    /**
     * Generates a new exception
     * @param item
     *        The item that is attemted to be equipped in an anchor with anchortype RIEM.
     * @post  The item involved in this exception is set to the given item.
     *        |this.item == item
     */
    public BeltAnchorException(Equipable item){this.item = item;}

    /**
     * returns the item that is involved in the exception.
     */
    @Basic @Immutable
    public Equipable getItem() {
        return item;
    }
}
