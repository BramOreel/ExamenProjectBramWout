package RPG;

/**
 * A class for weapons within an RPG
 *
 *
 */
public class Weapon extends Equipable{

    /**
     * CONSTRUCTORS
     */

    public Weapon(){
        setId(getIdcounter());
        incrementId();

    }

    /**
     * A static variable referencing the current idvalue. This value must always be divisible by three and even.
     */
    private static long idcounter = 36936918;

    /**
     *
     * Returns the current value of the idcounter
     */
    private static long getIdcounter() {
        return idcounter;
    }

    /**
     * Sets the idcounter to the specified value.
     * @param idcounter
     *        the value of the next, unused id.
     * @post If the given id is unique and valid, this weapon id is set to the given id, otherwise the id is set to zero.
     *       This should never happen however, seeing that the weapon id is always internally generated.
     *       | if(canHaveAsId(id))
     * 	     | then new.getId().equals(id)
     * 	     | else new.getId().equals(0)
     */
    private void setIdcounter(long idcounter) {
        if(canHaveAsId(idcounter))
            Weapon.idcounter = idcounter;
        else Weapon.idcounter = 0;
    }

    /**
     * Increments the value of the idcounter by 6. Thus guaranteeing it will always be divisible by two and three.
     */
    private void incrementId(){
        setIdcounter(getIdcounter() + 6);
    }

    /**
     * check if the weapon can have the given id as its id.
     * @param idcounter
     *        the idcounter to be checked
     *
     * @return False if the idcounter is negative, greater than the maximum integer value, not divisible by two or not divisble by three.
     *         | if(idcounter < 0 || idcounter > Integer.MAX_VALUE || idcounter % 2 !=0 || idcounter % 3 !=0 )
     *         | then result == false
     */
    @Override
    protected boolean canHaveAsId(long idcounter){
        return(super.canHaveAsId(idcounter) && idcounter % 2 == 0 && idcounter % 3 ==0);


    }

}
