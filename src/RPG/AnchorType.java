package RPG;

/**
 * An enumeration of different anchor types.
 *
 * @author Bram Oreel & Wout Thiers
 * @version 1.0
 */

public enum AnchorType {
    RUG("RUG"), LICHAAM("Lichaam"), LINKERHAND("Linkerhand"), RECHTERHAND("Rechterhand"), RIEM("Riem"), OTHER("other");

    /**
     * Generates new Anchortype
     * @param name
     *        the name of the anchortype
     * @post  the name is set to the given name.
     *        | this.name == name
     */
    private AnchorType(String name){
        this.name = name;
    }

    private String name;

    /**
     * Gives the name of the anchortype
     * @return the name
     */
    public String getName() {
        return name;
    }
}
