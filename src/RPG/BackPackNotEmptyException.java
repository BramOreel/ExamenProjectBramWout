package RPG;

public class BackPackNotEmptyException extends Exception {

    private final Backpack backpack;


    public BackPackNotEmptyException(Backpack backpack){
        this.backpack = backpack;
    }

    public Backpack getBackpack() {
        return backpack;
    }
}
