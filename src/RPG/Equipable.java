package RPG;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

import java.util.UUID;

/**
 * An abstract class of equipable items for heroes and monsters
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
 * @note Subclasses may only add/strengthen invariants (Liskov principle).
 *
 * @author Bram Oreel
 * @version 1.0
 *
 */
public abstract class Equipable {

    /**
     * Initialize a new Equipable item with a given weight and holder
     *
     * @param weight
     *        The weight of the equipable item in kilograms
     * @param holder
     *        The current holder of the equipable item
     * @effect if the given weight is valid, the weight is set to the specified value, else the weight of this item is set to 5 kilograms.
     *          |if(canHaveAsWeight(weight))
     *              this.weight = weight
     *          else this.weight = 5
     */
    @Raw @Model
    protected Equipable(int weight, Creature holder){
        if(canHaveAsWeight(weight))
            this.weight = weight;
        else this.weight = 5;
        setHolder(holder);
    }

    /**
     * Initialize a new Equipable item with a given weight without a holder. In the game this 'spawns' a new item.
     * @param weight
     *        The weight of the equipable item in kilograms.
     * @effect An equipable is initialised with given weight and a 'null' type holder.
     *         |this(weight, null)
     */
    @Raw @Model
    protected Equipable(int weight){
        this(weight,null);
    }



    /***********************
     * Identification
     */

    /**
     * Variable listing the Identification number of the item.
     */
    private long Id;


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
     * A static final value referencing the maximum allowed value in dukaten for equipables.
     */
    private static final int MAXSELLVALUE = 100;

    /**
     * Returns the maximum sell value for this equipable.
     */
    @Basic
    public int getMAXSELLVALUE(){
        return MAXSELLVALUE;
    }


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
     *         The given value is a integer less thann one.
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
     * @return True if the given value is greater than one and smaller than the maximum sell value.
     *        |if(value > 1) && (value < MAXSELLVALUE) then result == True.
     */
    @Raw //refactor
    public boolean isValidValue(int value){
        return(value >= 1 && value < getMAXSELLVALUE());
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
    @Model
    protected void setHolder(Creature holder) {
        this.holder = holder;
    }

    /**
     * A variable referencing the backpack this equipable item is stored in.
     * Null means that the item is currently not stored within a backpack.
     */
    private Backpack parentbackpack = null;

    /**
     * Returns the backpack that the equipable is currently being stored in.
     *
     */
    @Basic
    public Backpack getParentbackpack() { return parentbackpack;}

    /**
     * Sets the parent backpack for this item to the specified backpack.
     *
     * @param parentbackpack
     *        The new parent backpack for this equipable item.
     */
    @Model
    protected void setParentbackpack(Backpack parentbackpack) {this.parentbackpack = parentbackpack;}

    /**
     *
     * @param anchor
     * @throws IllegalArgumentException
     */

    /**
     * Sets up the unidirectional relation between the anchors of a creature and the items stored in them.
     *
     * @param anchor
     *        The anchor that this equipable item will be stored in.
     *
     * @effect The item equiped in the given anchorslot is set to this item.
     *         |anchor.setItem(this)
     * @effect The holder of this equipable item is set to the owner of the anchor
     *         |setHolder(anchor.getOwner());
     * @effect The capacity of the owner is reduced to account for the weight of the item.
     *         |anchor.getOwner().ChangeCapacity(-this.getWeight())
     */
    @Model @Raw
    protected void equip(Anchor anchor){
        anchor.setItem(this);
        setHolder(anchor.getOwner());
        anchor.getOwner().ChangeCapacity(-this.getWeight());
    }



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
     * Gives a value of how shiny an equipable is, depending on the type of equipable.
     * @return 1 if it's a weapon, 2 if it's a piece of armor and 3 if it's a purse.
     *        | if this instanceof Weapon then result == 1;
     *        | if this instanceof Armor then result == 2;
     *        | if this instanceof Purse then result == 3;
     *        | else result == 0;
     *
     */
    public int getShinyValue(){
        if(this instanceof Weapon) return 1;
        if(this instanceof Armor) return 2;
        if(this instanceof Purse) return 3;
        else return 0;
        
    }


}



