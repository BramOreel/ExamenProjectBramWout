package RPG;


public class Main {
    public static void main(String[] args) {
       // System.out.println("Hello world!");

        //Weapon yeet = new Weapon(3,19);
        //System.out.println(yeet.getId());
        //System.out.println(yeet.getDamage());
        Weapon woord = new Weapon(11, 49, 17);



        Armor jema = new Armor(25,3,ArmorType.DEFAULT);
        //System.out.println(jema.getId());
        Armor bingbong = new Armor(25,3,ArmorType.DEFAULT);
        //System.out.println(bingbong.getId());
        Armor bingbong2 = new Armor(-10000,3,ArmorType.DEFAULT);
        //System.out.println(bingbong2.getId());

        Backpack yeep = new Backpack(25,3);



        System.out.println(woord.getDamage());
        System.out.println(woord.getValue());
        System.out.println(woord.isDmgValLink());
        woord.DecrementDamage(14);
        System.out.println(woord.getDamage());
        System.out.println(woord.getValue());
        System.out.println();
        System.out.println();










    }



}