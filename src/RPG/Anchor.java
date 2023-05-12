package RPG;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
/**
 * A class of Anchors, slots where creatures can equip equipables
 * @invar  Each anchor can't contain an invalid equipable
 *         | hasValidEquipable(anchor.getItem(), anchor.getAnchorType())
 * @invar  Each anchor has an owner
 *         | getOwner() != null
 * @author 	Wout Thiers & Bram Oreel
 * @version 1.0
 */
public class Anchor {
    /**
     * Variable containing the type of anchor, either a body part or in the case of a monster, OTHER.
     * This cannot change after creation.
     */
    private final AnchorType anchorType;
    /**
     * Variable containing the item it holds.
     */
    private Equipable item;

    /**
     * The owner of the anchor. The owner can't change after creation.
     */
    private final Creature owner;

    /**
     * return The AnchorType of the anchor
     */
    @Basic @Immutable
    public AnchorType getAnchorType() {
        return anchorType;
    }

    /**
     * return the item that the Anchor contains, returns null if it is empty.
     */
    @Basic
    public Equipable getItem() {
        return item;
    }

    /**
     * Makes the Anchor hold an item.
     * @param item
     *        The item that the Anchor needs to hold.
     * @post  The item it hold is set to the given equipable
     *        |this.item == item
     */
    @Raw
    public void setItem(Equipable item) {
        this.item = item;
    }

    /**
     * Returns the owner of the anchor
     */
    @Basic @Immutable
    public Creature getOwner() {
        return owner;
    }
    /**
     * Generates a new Anchor that does not hold an item and has a certain anchorType.
     * @param anchorType
     *        The given AnchorType of the new anchor
     * @param owner
     *        The owner of the anchor
     * @post  The given anchortype gets set as the anchortype
     *        |this.anchorType == anchorType
     * @post  The given owner gets set as the owner
     *        |this.owner == owner
     */
    @Raw
    public Anchor(AnchorType anchorType, Creature owner) {
        this.anchorType = anchorType;
        this.owner = owner;
    }

    /**
     * Checks if an item can be equipped by an anchot with a certain anchorType
     * @param item
     *        the item
     * @param anchorType
     *        the anchortype of the anchor
     * @return  False if the anchortype is RIEM and the item is not a Purse, true otherwise.
     *          | if anchorType == AnchorType.RIEM && !(item instanceof Purse)
     *          | then result == false
     *          | else result == true
     */
    public boolean hasValidEquipable(Equipable item, AnchorType anchorType){
        if(anchorType == AnchorType.RIEM && !(item instanceof Purse)){
            return false;
        }
        return true;
    }
}
