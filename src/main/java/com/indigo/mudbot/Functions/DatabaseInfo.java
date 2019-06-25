package com.indigo.mudbot.Functions;

import com.indigo.mudbot.DatabaseCharacter;
import com.indigo.mudbot.DatabaseUser;
import com.indigo.mudbot.Main;

public class DatabaseInfo {
    public static int GetCharacterSlots(int slot, String user){
        DatabaseUser result = Main.getDatabase().getJsonDBTemplate().findById(user, DatabaseUser.class);
        switch(slot) {
            case 1:
                return result.getSlot1();
            case 2:
                return result.getSlot2();
            case 3:
                return result.getSlot3();
            default:
                return result.getSlot4();
        }
    }
    public static String GetCharacterName(int slot, String user){
        DatabaseUser result = Main.getDatabase().getJsonDBTemplate().findById(user, DatabaseUser.class);
        int characterId;
        switch(slot){
            case 1:
                characterId = result.getSlot1();
                break;
            case 2:
                characterId = result.getSlot2();
                break;
            case 3:
                characterId = result.getSlot3();
                break;
            default:
                characterId = result.getSlot4();
        }
        DatabaseCharacter character = Main.getDatabase().getJsonDBTemplate().findById(characterId, DatabaseCharacter.class);
        return character.getName();
    }
}