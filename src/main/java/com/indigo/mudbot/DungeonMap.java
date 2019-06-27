package com.indigo.mudbot;

import java.util.Random;

public class DungeonMap {
    String sessionId;
    public DungeonMap(String sessionId){
        this.sessionId = sessionId;
    }

    public String getRoom(){
        Random random = new Random();
        switch(random.nextInt(6)){
            case 0:
                return "L";
            case 1:
                return "U";
            case 2:
                return "R";
            case 3:
                return "LU";
            case 4:
                return "LR";
            case 5:
                return "UR";
            default:
                return "LUR";
        }

    }
}
