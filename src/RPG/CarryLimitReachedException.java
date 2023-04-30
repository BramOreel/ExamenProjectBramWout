package RPG;

public class CarryLimitReachedException extends Exception{

    private final Equipable item;

    public CarryLimitReachedException(Equipable item){
        this.item = item;
    }

    public Equipable getItem() {
        return item;
    }
}
