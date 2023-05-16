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

        hero1 = new Hero("Hero: One", 100, 15.6894, 50, armor1);
        hero2 = new Hero("Hero'Two", 100, 10.6894, 40, armor2);
        hero3 = new Hero("James o'Hara", 100, 8.68948, 30, armor3);

        monster1 = new Monster("Destroyer o'Hope", 100, 300, ArmorType.SCALE, 10, 2);
        monster2 = new Monster("MonsterTwo", 100, 300, ArmorType.TICK, 10, 4);
        monster3 = new Monster("Monster Three", 100, 300, ArmorType.TOUGH, 10, 3);
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




}
