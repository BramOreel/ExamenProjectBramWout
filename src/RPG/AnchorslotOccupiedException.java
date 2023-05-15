package RPG;
/**
 * A class for signaling illegal attempts to equip a certain item in an anchor that is not empty.
 *
 * @author 	Wout Thiers & Bram Oreel
 * @version 1.0
 */
public class AnchorslotOccupiedException extends Exception{
    /**
     * Variable stating which anchor is occupied.
     */
    private final Anchor anchor;

    /**
     * Creates a new exception.
     * @param anchor
     *        the anchor that is occupied.
     * @post  the anchor for this exception is set to the given anchor.
     *        |this.anchor == anchor
     */
    public AnchorslotOccupiedException(Anchor anchor){
        this.anchor = anchor;
    }

    /**
     * returns the anchor involved in the exception.
     */
    public Anchor getAnchor() {
        return anchor;
    }
}
