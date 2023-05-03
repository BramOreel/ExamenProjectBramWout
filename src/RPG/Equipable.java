package RPG;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.UUID;

/**
 * An abstract class of equipable items of heroes and monsters
 *
 * @invar Each equipable item must have a valid identification number.
 *        | canHaveAsId(getId())
 *
 * @invar Each equipable item must have a valid weight
 *        | canHaveAsWeight(getWeight())
 *
 * @invar Each equipable item must have a allowed value
 *        | isValidValue(getValue())
 *
 */
public abstract class Equipable {


    /**
     * Variable listing the Identification Number of the item.
     */
    private long Id;


    /**
     * CONSTRUCTORS
     */

    /**
     * Initialize a new Equipable item with a given weight and holder
     */
    @Raw @Model
    protected Equipable(int weight){
        if(canHaveAsWeight(weight))
            this.weight = weight;
        else this.weight = 5;
    }

    /***********************
     * Identification
     */

    /**
     * Checks if the equipable can be equiped by an anchor
     * @param anchor
     *        the anchor that would equip the equipable
     * @return True
     */

    public boolean isValidAnchor(Anchor anchor){
        return true;
    }

    /**
     *
     * Return the Id of the equipable item
     */
    @Basic @Immutable
    public long getId() {
        return Id;
    }

    /**
     * Sets the id of this equipable item to the given id.
     * @param id
     *        the unique id for an equipable item
     * @post If the given id is valid, which it should be at this point in the code, this equipable item is set to the given id,
     *       otherwise the id is set to zero. At this point uniqueness cannot be guaranteed.
     *       | if (canHaveAsId(id))
     * 	     | then new.getId().equals(id)
     * 	     | else new.getId().equals(0)
     *
     *
     */
    @Raw @Model
    protected final void setId(long id) {
        if(canHaveAsId(id))
            Id = id;
        else Id = 0;


    }

    /**
     * Check whether the equipable item can have the given id as its id
     * @param id
     *        the id to be checked
     * @return False if the id has a negative value or is greater then the maximum integer value.
     *         | if(id < 0 || id > Integer.MAX_VALUE)
     *         | then result == false
     * @note This checker only checks the common rule between all the subclasses. Whether or not the id is unique is resolved by the subclasses.
     *
     */
    @Raw @Model
    protected boolean canHaveAsId(long id){
       return(id >= 0 && id < Integer.MAX_VALUE);

    }


    /*********************************
     * Weight
     */

    /**
     * a final variable containing the weight of an Equipable item in kilograms
     */
    private final int weight;

    /**
     *
     * Returns the weight of this equipable
     */
    @Basic
    public int getWeight() {
        return weight;
    }

    /**
     * Checks if an equipable item can have this value as its weight.
     *
     * @param weight
     *        the weight of the equipable item
     * @return True if the value is greater than zero
     *         |if(weight > 0) then result == True
     */
    @Raw
    public boolean canHaveAsWeight(long weight){
        return(weight > 0);
    }

    /************
     * Value
     */

    /**
     * A variable containing the value of an Equipable item in 'dukaten'
     */
    private int value = 1;

    /**
     * Returns the value of the equipable item
     */
    @Basic
    public int getValue() {
        return value;
    }

    /**
     *Sets the value for this equipable item to the given item
     *
     * @param value
     *        the new value for this equipable item
     *
     * @throws IllegalArgumentException
     *         The given value is a integer less then one.
     *         |value < 1
     */
    @Raw @Model
    protected void setValue(int value) throws IllegalArgumentException {
        if(!isValidValue(value))
            throw new IllegalArgumentException();
        this.value = value;
    }

    /**
     * Checks whether an equipable item can have this value as its value.
     *
     * @param value
     *        the value to be checked
     *
     * @return False if the given value is smaller than one.
     *        |if(value < 1) then result == False
     */
    @Model @Raw
    protected boolean isValidValue(int value){
        return(value >= 1);
    }


    /********
     * Holder
     */

    /**
     * A variabele referencing the creature object that currently holds this equipable item.
     */
    private Creature holder = null;

    /**
     *
     * Returns the holder of this equipable item
     */
    @Basic
    public Creature getHolder() {
        return holder;
    }

    /**
     * Sets the holder of this equipable item to the given holder
     * @param holder
     *        the new holder for this equipable item
     */
    protected void setHolder(Creature holder) {
        this.holder = holder;
    }


    public void equip(Anchor anchor) throws IllegalArgumentException{
        anchor.setItem(this);
        this.setHolder(anchor.getOwner());
    }

    /**
     * Equips an equipable in a random empty anchor of the creature.
     * @param monster
     *        The monster that has to equip the item.
     * @effect The item will be added to the first empty anchor. If there is no free anchor nothing happens.
     *        | for(Anchor anchor : monster.getAnchors())
     *        |     if (anchor.getItem() == null)
     *        |         equip(anchor);
     *        |         break;
     */
    protected void equip(Monster monster){
        for(Anchor anchor : monster.getAnchors()) {
            if (anchor.getItem() == null) {
                equip(anchor);
                break;
            }
        }
    }

    public void unequip(Anchor anchor) throws IllegalArgumentException{
        anchor.setItem(null);
        this.setHolder(null);
    }

    /**
     * Unequips an item if the creature has the item equiped
     * @param creature
     *        The creature that holds the item.
     * @effect  The first anchor of the creature that holds this item has its item set to null.
     *        |for(Anchor anchor : creature.getAnchors()) {
     *        |    if (anchor.getItem() == this) {
     *        |        anchor.setItem(null);
     * @effect  The holder of this item is set to null it the item is held by the creature.
     *        | if(creature has this)
     *        |     this.setHolder(null);
     */

    public void unequip(Creature creature){
        for(Anchor anchor : creature.getAnchors()) {
            if (anchor.getItem() == this) {
                anchor.setItem(null);
                this.setHolder(null);
                break;
            }
        }
    }









}



