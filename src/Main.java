import RPG.*;

import java.util.Random;

/**
 * A simulation of a fight between a Monster and a Hero.
 */
public class Main {
    public static void main(String[] args) {
        //Construct new armor and two weapons
        Armor armor1 = new Armor(2, 100, ArmorType.DEFAULT, 50);
        Weapon weapon1 = new Weapon(50, 20, 30);
        Weapon weapon2 = new Weapon(50, 45, 20);

        //Construct new Hero
        Hero hero = new Hero("James o'Hara", 100, 118.68948, 20, armor1, weapon2);

        System.out.println("Total Value of the items of the hero == " + hero.getTotalValueOfItems());

        Armor armor2 = new Armor(2, 100, ArmorType.DEFAULT, 150);
        Weapon weapon3 = new Weapon(50, 20, 50);
        Weapon weapon4 = new Weapon(50, 25, 15);
        Purse purse = new Purse(5);

        Monster monster = new Monster("Destroyer o'Hope", 300, 300, ArmorType.SCALE, 10, 4, armor2, weapon3, purse);

        System.out.println("Total Value of the items of the monster == " + monster.getTotalValueOfItems());
        //Generate random int to decide who begins the fight
        Random rand = new Random();
        int randomNum = rand.nextInt(101);
        System.out.println("Monster: "  + monster.getHitPoints() + " / " + monster.getMaxHitPoints());
        System.out.println("Hero: "  + hero.getHitPoints() + " / " + hero.getMaxHitPoints());
        //Fight, if the random number is even then the hero begins, if it's not the monster begins.
        while(hero.isAlive() && monster.isAlive()){
            if(randomNum % 2 == 0){
                hero.Hit(monster);
                System.out.println("Monster: "  + monster.getHitPoints() + " / " + monster.getMaxHitPoints());
            }
            if(randomNum % 2 == 1){
                monster.Hit(hero);
                System.out.println("Hero: "  + hero.getHitPoints() + " / " + hero.getMaxHitPoints());
            }
            randomNum += 1;
        }
        System.out.println("Hero: "  + hero.getHitPoints() + " / " + hero.getMaxHitPoints());
        if(hero.isAlive()){
            System.out.println("The Hero is victorious");
            System.out.println("Total Value of the items of the hero == " + hero.getTotalValueOfItems());
        }

        if(!hero.isAlive()){
            System.out.println("The Monster is victorious");
            System.out.println("Total Value of the items of the monster == " + monster.getTotalValueOfItems());
        }
    }
}