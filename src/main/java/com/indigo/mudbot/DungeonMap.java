package com.indigo.mudbot;

import java.io.File;
import java.util.Random;

public class DungeonMap {

    protected Random rnd = new Random();
    protected int roomNumber = 0;
    private File roomImg;

    public File move()
    {
        int number = rnd.nextInt(7);

        switch (number)
        {
            case 0:
                roomImg = new File("Enviroment/U.png");//forward
                break;
            case 1:
                roomImg = new File("Enviroment/L.png");//corner left
                break;
            case 2:
                roomImg = new File("Enviroment/R.png");//corner right
                break;
            case 3:
                roomImg = new File("Enviroment/LU.png");//left and forward
                break;
            case 4:
                roomImg = new File("Enviroment/LR.png");//left and right
                break;
            case 5:
                roomImg = new File("Enviroment/RU.png");//right and forward
                break;
            case 6:
                roomImg = new File("Enviroment/LRU.png");//left right forward
                break;
        }

        number = rnd.nextInt(100);

        if(number < 10 && roomNumber > 9)
            roomImg = new File("Boss.png");//spawn boss

        if(number >= 10 && number < 55)
        {
            //roll and spawn a monster
            int c = rnd.nextInt(32);

            if(c < 5)
                roomImg = new File("spider.png");
            if(c >= 5 && c < 10)
                roomImg = new File("slime.png");
            if(c >= 10 && c < 15)
                roomImg = new File("skeleton.png");
            if(c >= 15 && c < 19)
                roomImg = new File("flesh");
            if(c >= 19 && c < 23)
                roomImg = new File("fungus");
            if(c >= 23 && c < 26)
                roomImg = new File("demoncat");
            if(c >= 26 && c < 29)
                roomImg = new File("gnomereaper");
            if(c >= 29 && c < 31)
                roomImg = new File("scrissorrat");
            if(c == 31)
                roomImg = new File("saltelemental");

        }
        if(number >= 55 && number < 70)
            roomImg = new File("Enviroment/shop.png");//shop

        this.roomNumber++;

        return roomImg;
    }
}
