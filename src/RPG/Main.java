package RPG;


import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
       // System.out.println("Hello world!");

        Weapon yeet = new Weapon(3,20);

        Weapon woord = new Weapon(11, 49, 17);



        Armor jema = new Armor(25,3,ArmorType.DEFAULT,20);
        //System.out.println(jema.getId());
        Armor bingbong = new Armor(25,3,ArmorType.DEFAULT,30);
        //System.out.println(bingbong.getId());
        Armor bingbong2 = new Armor(-10000,3,ArmorType.DEFAULT,40);
        //System.out.println(bingbong2.getId());

        Backpack yeep = new Backpack(25,3,20,60);

        Hero Bram = new Hero("Bram",27,12.43);

        System.out.println(Bram.getCapacity());

        try {
            Bram.pickUp(yeet,AnchorType.RECHTERHAND);
        } catch (ItemAlreadyobtainedException e) {
            throw new RuntimeException(e);
        } catch (AnchorslotOquipiedException e) {
            throw new RuntimeException(e);
        } catch (CarryLimitReachedException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Anchor> anchors = Bram.getAnchors();
        System.out.println(Bram.getAnchorAt(3).getAnchorType().getName());
        System.out.println(Bram.getCapacity());
        try {
            Bram.pickUp(woord,AnchorType.LICHAAM);
        } catch (ItemAlreadyobtainedException e) {
            throw new RuntimeException(e);
        } catch (AnchorslotOquipiedException e) {
            throw new RuntimeException(e);
        } catch (CarryLimitReachedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(yeet.getHolder().getName());

        Backpack rugzak = new Backpack(25,2,50,100);
        Backpack satchel = new Backpack(25,3,55,25);
        try {
            Bram.pickUp(rugzak,AnchorType.LINKERHAND);
        } catch (ItemAlreadyobtainedException e) {
            throw new RuntimeException(e);
        } catch (AnchorslotOquipiedException e) {
            throw new RuntimeException(e);
        } catch (CarryLimitReachedException e) {
            throw new RuntimeException(e);
        }


        Bram.pickUpAndStore(satchel,rugzak);
        System.out.println(rugzak.getContent());
        System.out.println(Bram.getCapacity());
        System.out.println(Bram.getAnchorItemAt(1));


    }



}