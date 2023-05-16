package RPG;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.*;


public class CreatureTest {
    private Hero hero1, hero2, hero3;

    private Monster monster1, monster2, monster3;

    private Backpack backpack1, backpack2, backpack3;

    private Weapon weapon1, weapon2, weapon3, weapon4, weapon5;

    private Armor armor1, armor2, armor3;

    private Purse purse1, purse2 , purse3;


    @Before
    public void setUpCreatures(){
        armor1 = new Armor(2, 100, ArmorType.DEFAULT, 50);
        armor2 = new Armor(2, 90, ArmorType.DEFAULT, 40);
        armor3 = new Armor(2, 80, ArmorType.DEFAULT, 30);

        weapon1 = new Weapon(50, 20, 30);
        weapon2 = new Weapon(50, 25, 20);
        weapon3 = new Weapon(50, 15, 40);
        weapon4 = new Weapon(50, 35, 50);
        weapon5 = new Weapon(50, 40, 60);

        purse1 = new Purse(5);
        purse1 = new Purse(6);
        purse1 = new Purse(7);

        hero1 = new Hero("Hero: One", 100, 15.6894, 20, armor1);
        hero2 = new Hero("Hero'Two", 100, 10.6894, 20, armor2);
        hero3 = new Hero("James o'Hara", 100, 8.68948, 20, armor3);

        monster1 = new Monster("Destroyer o'Hope", 100, 300, ArmorType.SCALE, 10, 2);
        monster2 = new Monster("MonsterTwo", 100, 300, ArmorType.TICK, 10, 4);
        monster3 = new Monster("Monster Three", 100, 300, ArmorType.TOUGH, 10, 3);

        try {
            hero1.pickUp(weapon1, AnchorType.LINKERHAND);
        } catch (CarryLimitReachedException e) {
            throw new RuntimeException(e);
        }
        try {
            hero1.pickUp(weapon2, AnchorType.RUG);
        } catch (CarryLimitReachedException e) {
            throw new RuntimeException(e);
        }

        try {
            monster1.pickUp(weapon3, AnchorType.OTHER);
        } catch (ItemAlreadyobtainedException e) {
            throw new RuntimeException(e);
        } catch (AnchorslotOccupiedException e) {
            throw new RuntimeException(e);
        } catch (CarryLimitReachedException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    public void TestValidName(){
        Assert.assertEquals(hero1.getName(), "Hero: One");
        Assert.assertEquals(monster1.getName(), "Destroyer o'Hope");
        Assert.assertEquals(hero2.getName(), "Hero'Two");
        Assert.assertEquals(monster2.getName(), "MonsterTwo");
        Assert.assertEquals(hero3.getName(), "James o'Hara");
        Assert.assertEquals(monster3.getName(), "Monster Three");
    }

    @Test
    public void TestInvalidName(){
        Assert.assertFalse(hero1.canHaveAsName("James' o'Hara'"));
        Assert.assertFalse(hero1.canHaveAsName("James:Hara"));
        Assert.assertFalse(hero1.canHaveAsName("james' o'Hara'"));
        Assert.assertFalse(hero1.canHaveAsName("James2"));
        Assert.assertFalse(monster1.canHaveAsName("Destroyer: Hope"));
        Assert.assertTrue(monster1.canHaveAsName("Destroyer'''''''Hope"));
        Assert.assertFalse(monster1.canHaveAsName("destroyer"));
    }

    @Test
    public void TestHeroConstrucor(){
        Assert.assertEquals(hero1.getStrength(), 15.69, 0.000000001);
        Assert.assertEquals(hero1.getProtection(), 20);
        Assert.assertEquals(hero1.getMaxCapacity(), 314);
        Assert.assertEquals(hero1.getCapacity(), 114);
        Assert.assertEquals(hero1.getHitPoints(), 100);
        Assert.assertEquals(hero1.getMaxHitPoints(), 100);
        Assert.assertEquals(hero1.getAnchorItemAt(0), weapon1);
        Assert.assertEquals(hero1.getAnchorItemAt(3), armor1);
        Assert.assertTrue(hero1.hasProperAnchors(hero1.getAnchors()));
        Assert.assertTrue(hero1.isAlive());
        Assert.assertEquals(hero1.getNbOfAnchors(), 5);
    }

    @Test
    public void TestMonsterConstrucor(){
        Assert.assertEquals(monster1.getMaxCapacity(), 300);
        Assert.assertEquals(monster1.getCapacity(), 250);
        Assert.assertEquals(monster1.getHitPoints(), 100);
        Assert.assertEquals(monster1.getMaxHitPoints(), 100);
        Assert.assertEquals(monster1.getAnchorItemAt(0), weapon3);
        Assert.assertTrue(monster1.hasProperAnchors(monster1.getAnchors()));
        Assert.assertTrue(monster1.isAlive());
        Assert.assertEquals(monster1.getNbOfAnchors(), 2);
        Assert.assertEquals(monster1.getDamage().getDamage(), 10);
    }

    @Test
    public void TestTotalProtection(){
        Assert.assertEquals(monster1.getTotalProtection(), 90);
        Assert.assertEquals(hero1.getTotalProtection(), 20 + 50);
        try {
            hero1.drop(armor1);
        } catch (OtherPlayersItemException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(hero1.getTotalProtection(), 20);
        try {
            hero1.pickUp(armor1, AnchorType.RECHTERHAND);
        } catch (CarryLimitReachedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(hero1.getTotalProtection(), 20);
    }

    @Test
    public void TestTotalDamage(){
        Assert.assertEquals(monster1.getTotalDamage(), 10);
        Assert.assertEquals(hero1.getTotalDamage(), 12);
        try {
            hero1.drop(weapon1);
        } catch (OtherPlayersItemException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(hero1.getTotalDamage(), 2);
        try {
            hero1.pickUp(weapon1, AnchorType.RECHTERHAND);
        } catch (CarryLimitReachedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(hero1.getTotalDamage(), 12);
        try {
            hero1.pickUp(weapon5, AnchorType.LINKERHAND);
        } catch (CarryLimitReachedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(hero1.getTotalDamage(), 32);
    }


    @Test
    public void TestHitDieLootHeroDies(){
        hero1.setHitPoints(5);
        hero1.setStrength(0);
        hero1.setProtection(0);
        Assert.assertTrue(hero1.isAlive() && monster1.isAlive());
        while(hero1.isAlive() && monster1.isAlive()){
            hero1.Hit(monster1);
            if(monster1.isAlive){
                monster1.Hit(hero1);
            }
        }
        Assert.assertFalse(hero1.isAlive());
        Assert.assertTrue(monster1.isAlive());
        Assert.assertEquals(hero1.getHitPoints(), 0);
        Assert.assertNotEquals(monster1.getHitPoints(), 0);
        Assert.assertEquals(hero1.getAnchorItemAt(0), null);
        Assert.assertEquals(hero1.getAnchorItemAt(1), null);
        Assert.assertEquals(hero1.getAnchorItemAt(2), null);
        Assert.assertEquals(hero1.getAnchorItemAt(3), null);
        Assert.assertEquals(hero1.getAnchorItemAt(4), null);
        Assert.assertEquals(monster1.getAnchorItemAt(0), armor1);
        Assert.assertEquals(monster1.getAnchorItemAt(1), weapon1);
        Assert.assertEquals(weapon1.getHolder(), monster1);
        Assert.assertEquals(armor1.getHolder(), monster1);
        Assert.assertEquals(weapon3.getHolder(), null);
    }
    @Test
    public void TestHitDieLootMonsterDies(){
        hero1.setProtection(100);
        Assert.assertTrue(hero1.isAlive() && monster1.isAlive());
        while(hero1.isAlive() && monster1.isAlive()){
            hero1.Hit(monster1);
            if(monster1.isAlive){
                monster1.Hit(hero1);
            }
        }
        Assert.assertFalse(monster1.isAlive());
        Assert.assertTrue(hero1.isAlive());
        Assert.assertEquals(monster1.getHitPoints(), 0);
        Assert.assertNotEquals(hero1.getHitPoints(), 0);
        Assert.assertEquals(monster1.getAnchorItemAt(0), null);
        Assert.assertEquals(monster1.getAnchorItemAt(1), null);
        Assert.assertEquals(hero1.getAnchorItemAt(0), weapon1);
        Assert.assertEquals(hero1.getAnchorItemAt(1), weapon3);
        Assert.assertEquals(hero1.getAnchorItemAt(2), weapon2);
        Assert.assertEquals(hero1.getAnchorItemAt(3), armor1);
        Assert.assertEquals(weapon1.getHolder(), hero1);
        Assert.assertEquals(armor1.getHolder(), hero1);
        Assert.assertEquals(weapon3.getHolder(), hero1);
        Assert.assertEquals(weapon2.getHolder(), hero1);
    }
}


