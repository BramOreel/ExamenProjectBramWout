package RPG;

import be.kuleuven.cs.som.annotate.Raw;

public class Anchor {
    /**
     * Variable containing the type of anchor, either a body part or in the case of a monster, OTHER.
     */
    private final AnchorType anchorType;
    /**
     * Variable containing the item it holds.
     */
    private Equipable item;

    /**
     * The owner of the anchor.
     */
    private final Creature owner;

    /**
     * @return The AnchorType of the anchor
     */
    public AnchorType getAnchorType() {
        return anchorType;
    }

    /**
     * @return the item that the Anchor contains, returns null if it is empty.
     */
    public Equipable getItem() {
        return item;
    }

    /**
     * Makes the Anchor hold an item.
     * @param item
     *        The item that the Anchor needs to hold.
     */
    @Raw
    public void setItem(Equipable item) {
        this.item = item;
    }

    protected Creature getOwner() {
        return owner;
    }

    /**
     * Generates a new Anchor that holds a certain item and has an anchortype.
     * @param anchorType
     *        The given AnchorType of the new anchor
     * @param item
     *        The item the anchor holds.
     * @param owner
     *        The owner of the anchor
     * @effect The given item gets set as the item that the anchor contains.
     *         setItem(item)
     */
    @Raw
    public Anchor(AnchorType anchorType,Creature owner, Equipable item) {
        this.anchorType = anchorType;
        this.owner = owner;
        //  dit zou beter een functie van Equipable zijn setItem(item);
    }


    /**
     * Generates a new Anchor that does not hold an item and has a certain anchorType.
     * @param anchorType
     *        The given AnchorType of the new anchor
     * @param owner
     *        The owner of the anchor
     *@effect Generates a new Anchor with the parameters, and null as the item.
     *        |this(anchorType, owner, null)
     */
    @Raw
    public Anchor(AnchorType anchorType, Creature owner) {
        this(anchorType, owner, null);
    }
}
