package RPG;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class for signaling illegal attempts to do something to an item that is equipped by another creature.
 *
 * @author 	Wout Thiers & Bram Oreel
 * @version 1.0
 */
public class OtherPlayersItemException extends Exception{
    /**
     * The item that is equipped by a different creature.
     */
    private final Equipable item;

    /**
     * generates a new exception
     * @param item
     *        The item that is equipped by a different creature.
     * @post  The given item is set as the item involved in the exception.
     *        |this.item == item;
     */
    public OtherPlayersItemException(Equipable item) {
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
