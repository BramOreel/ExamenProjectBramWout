package RPG;

import RPG.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.*;

public class RemoveFromAnchorTest {

    Armor testArmor;
    Hero testHero;
    Backpack HeroBackpack;
    Backpack GroundBackpack;
    Backpack StoredBackpack;
    Armor StoredArmor;
    Weapon StoredWeapon;
    Weapon HeavyWeapon;

    Hero testHero2;

    Weapon nullItem;

    Weapon randomweapon;

    public RemoveFromAnchorTest(){}

    @Before
    public void setUpAnchors() throws CarryLimitReachedException {
        StoredArmor = new Armor(14, 2, ArmorType.DEFAULT, 600);
        testArmor = new Armor(14, 2, ArmorType.DEFAULT, 40);
        testHero = new Hero("Dummy", 23, 14.20, testArmor);
        testHero2 = new Hero("Dummy", 20, 14.3, StoredArmor);
        HeroBackpack = new Backpack(20, 5, 400, 20);
        GroundBackpack = new Backpack(21, 4, 20, 20);
        StoredBackpack = new Backpack(22, 12, 20, 20);
        StoredWeapon = new Weapon(2);
        HeavyWeapon = new Weapon(40);
        randomweapon = new Weapon(10);
        testHero.pickUp(StoredWeapon,AnchorType.LINKERHAND);
        testHero.pickUp(HeroBackpack,AnchorType.RECHTERHAND);
        testHero2.pickUp(GroundBackpack,AnchorType.LINKERHAND);
    }

    @Test
    public void testDrop() throws OtherPlayersItemException{

        int capacity = testHero.getCapacity();

        testHero.drop(StoredWeapon);

        Assert.assertEquals(StoredWeapon.getHolder(),null);
        Assert.assertEquals(testHero.getAnchorItemAt(0),null);
        Assert.assertEquals(StoredWeapon.getParentbackpack(),null);
        Assert.assertEquals(testHero.getCapacity(), capacity + 2);
    }

    @Test
    public void testBackpackdrop() throws OtherPlayersItemException, ItemAlreadyobtainedException, CarryLimitReachedException {
        testHero.pickUpAndStore(randomweapon,HeroBackpack);

        int capacity = testHero.getCapacity();
        testHero.drop(HeroBackpack);

        Assert.assertEquals(HeroBackpack.getHolder(),null);
        Assert.assertEquals(testHero.getAnchorItemAt(2),null);
        Assert.assertEquals(HeroBackpack.getParentbackpack(),null);
        Assert.assertEquals(randomweapon.getHolder(),null);
        Assert.assertEquals(randomweapon.getParentbackpack(),HeroBackpack);
        Assert.assertEquals(testHero.getCapacity(), capacity + 10 + 5);

    }

    @Test(expected = OtherPlayersItemException.class)
    public void testDropOtherPlayerItem() throws OtherPlayersItemException {
        testHero.drop(GroundBackpack);
    }

    @Test(expected = OtherPlayersItemException.class)
    public void testDropGroundItem() throws OtherPlayersItemException {
        testHero.drop(HeavyWeapon);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDropNullItem() throws OtherPlayersItemException {
        testHero.drop(nullItem);
    }

    //Drop Item At AnchorTests
    @Test
    public void testDropALlAnchorItems() throws CarryLimitReachedException, OtherPlayersItemException, ItemAlreadyobtainedException {
        int maxcapacity = testHero.getMaxCapacity();

        Weapon randomweapon2 = new Weapon(4);
        Weapon randomweapon3 = new Weapon(5);
        testHero.pickUp(randomweapon3, AnchorType.RUG);
        testHero.pickUpAndStore(randomweapon2, HeroBackpack);
        testHero.dropAllItems();

        Assert.assertEquals(testHero.getCapacity(), maxcapacity);
        for (int i = 0; i < testHero.getNbOfAnchors(); i++) {
            Assert.assertEquals(testHero.getAnchorItemAt(i), null);

        }
    }

    //Move Anchor Item to anchor tests

    @Test
    public void testMoveItem () throws AnchorslotOccupiedException {

        Equipable item = testHero.getAnchorItemAt(1);
        Assert.assertEquals(testHero.getAnchorItemAt(2),null);
        testHero.moveAnchorItemtoAnchor(1,2);
        Assert.assertEquals(testHero.getAnchorItemAt(1),null);
        Assert.assertEquals(testHero.getAnchorItemAt(2),item);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveIllegalAnchor() throws AnchorslotOccupiedException {
        testHero.moveAnchorItemtoAnchor(0,8);
    }

    @Test(expected = NullPointerException.class)
    public void testMoveNullItem() throws AnchorslotOccupiedException {
        testHero.moveAnchorItemtoAnchor(4,3);
    }

    @Test(expected = AnchorslotOccupiedException.class)
    public void testMoveToOccupiedAnchor() throws AnchorslotOccupiedException {
        testHero.moveAnchorItemtoAnchor(1,0);
    }












}
