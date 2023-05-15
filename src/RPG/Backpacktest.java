package RPG;

import RPG.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.*;


 public class Backpacktest {

     Armor testArmor;
      Hero testHero;
      Backpack HeroBackpack;
    Backpack GroundBackpack;
      Backpack StoredBackpack;
   Armor StoredArmor;
     Weapon StoredWeapon;
      Weapon HeavyWeapon;

      Hero testHero2;

    public Backpacktest(){}

    @Before
    public void setUpBackpack()
    {
        StoredArmor = new Armor(14,2, ArmorType.DEFAULT,600);
         testArmor = new Armor(14,2,ArmorType.DEFAULT,40);
         testHero = new Hero("Dummy",23,14.20,testArmor);
         testHero2 = new Hero("Dummy",20,14.3,StoredArmor);
         HeroBackpack = new Backpack(20,5,400,20);
         GroundBackpack = new Backpack(21,4,20,20);
         StoredBackpack = new Backpack(22,12,20,20);
        try {
            testHero.pickUp(HeroBackpack,AnchorType.RUG);
        } catch (CarryLimitReachedException e) {
            throw new RuntimeException(e);
        }

         StoredWeapon = new Weapon(2);
        HeavyWeapon = new Weapon(40);
    }

    @Test
    public void testNormalStore() {

      try {
          HeroBackpack.addEquipable(StoredArmor);
      } catch (BackPackNotEmptyException e) {
          throw new RuntimeException(e);
      } catch (CarryLimitReachedException e) {
          throw new RuntimeException(e);
      } catch (OtherPlayersItemException e) {
          throw new RuntimeException(e);
      } catch (ItemAlreadyobtainedException e) {
          throw new RuntimeException(e);
      }
      Assert.assertEquals(HeroBackpack.getHolder(), StoredArmor.getHolder());
      Assert.assertEquals(HeroBackpack.getNbOfArmors(),1);
      Assert.assertEquals(StoredArmor.getParentbackpack(), HeroBackpack);
      Assert.assertEquals(HeroBackpack.getTotalWeight(),HeroBackpack.getWeight()+ StoredArmor.getWeight());

    }

    @Test (expected = NullPointerException.class)
    public void testAddItemToGroundBackpack( ) throws OtherPlayersItemException, ItemAlreadyobtainedException, CarryLimitReachedException, BackPackNotEmptyException {
        GroundBackpack.addEquipable(StoredWeapon);
    }


    @Test
     public void testStoreBackpack(){
        try {
            HeroBackpack.addEquipable(StoredBackpack);
        } catch (BackPackNotEmptyException e) {
            throw new RuntimeException(e);
        } catch (CarryLimitReachedException e) {
            throw new RuntimeException(e);
        } catch (OtherPlayersItemException e) {
            throw new RuntimeException(e);
        } catch (ItemAlreadyobtainedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(HeroBackpack.getHolder(), StoredBackpack.getHolder());
        Assert.assertEquals(HeroBackpack.getNbOfItems(),1);
        Assert.assertEquals(StoredBackpack.getParentbackpack(), HeroBackpack);
        Assert.assertEquals(HeroBackpack.getTotalWeight(),HeroBackpack.getWeight()+ StoredBackpack.getWeight());
    }

    @Test(expected = BackPackNotEmptyException.class)
     public void testStoreLoadedBackpack() throws OtherPlayersItemException, ItemAlreadyobtainedException, CarryLimitReachedException, BackPackNotEmptyException {
        testHero2.pickUp(GroundBackpack,AnchorType.RUG);
        GroundBackpack.addEquipable(StoredWeapon);
        testHero2.drop(GroundBackpack);
        HeroBackpack.addEquipable(GroundBackpack);

    }

    @Test(expected = CarryLimitReachedException.class)
     public void testCarryLimit() throws OtherPlayersItemException, ItemAlreadyobtainedException, CarryLimitReachedException, BackPackNotEmptyException {
        HeroBackpack.addEquipable(HeavyWeapon);
    }

    @Test(expected = ItemAlreadyobtainedException.class)
     public void testObtainedItem() throws OtherPlayersItemException, ItemAlreadyobtainedException, CarryLimitReachedException, BackPackNotEmptyException {
        HeroBackpack.addEquipable(StoredWeapon);
        HeroBackpack.addEquipable(StoredWeapon);
    }


    @Test
     public void testAddingMultipleItems() throws OtherPlayersItemException, ItemAlreadyobtainedException, CarryLimitReachedException, BackPackNotEmptyException {
        Weapon biem = new Weapon(1);
        Weapon bong = new Weapon(3);
        Backpack id17 = new Backpack(17,5,200,14);
        HeroBackpack.addEquipable(biem);
        HeroBackpack.addEquipable(bong);
        HeroBackpack.addEquipable(id17);
        HeroBackpack.addEquipable(StoredArmor);

        Assert.assertEquals(HeroBackpack.getNbOfItems(),4);
        Assert.assertEquals(HeroBackpack.getTotalWeight(),16);


    }












}
