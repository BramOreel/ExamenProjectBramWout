package RPG;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * A class for signaling illegal attempts to store a certain backpack that is not empty in a different backpack.
 *
 * @author 	Wout Thiers & Bram Oreel
 * @version 1.0
 */
public class BackPackNotEmptyException extends Exception {
    /**
     * Variable stating which backpack is not empty.
     */
    private final Backpack backpack;

    /**
     * Creates a new exception.
     * @param backpack
     *        the backpack that is not empty.
     * @post  sets the backpack that is involved in the exception to the given backpack.
     *        |this.backpack == backpack
     */
    public BackPackNotEmptyException(Backpack backpack){
        this.backpack = backpack;
    }

    /**
     * Returns the backpack involved in the exception.
     */
    @Basic @Immutable
    public Backpack getBackpack() {
        return backpack;
    }
}
