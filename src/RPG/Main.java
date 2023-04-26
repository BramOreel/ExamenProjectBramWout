package RPG;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Weapon yeet = new Weapon();
        System.out.println(yeet.getId());
        System.out.println(yeet.getId());

        Armor jema = new Armor(25);
        System.out.println(jema.getId());
        Armor bingbong = new Armor(25);
        System.out.println(bingbong.getId());
        Armor bingbong2 = new Armor(-10000);
        System.out.println(bingbong2.getId());

        Backpack yeep = new Backpack(25);
        System.out.println(yeep.getId());

    }



}