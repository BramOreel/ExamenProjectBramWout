import RPG.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //Construct new armor and two weapons
        Armor armor1 = new Armor(2, 100, ArmorType.DEFAULT, 50);
        Weapon weapon1 = new Weapon(50, 20, 30);
        Weapon weapon2 = new Weapon(50, 25, 20);

        //Construct new Hero
        Hero hero = new Hero("James o'Hara", 100, 8.68948, 20, armor1, weapon1, weapon2);

        System.out.println(hero.getTotalValueOfItems());



    }



}