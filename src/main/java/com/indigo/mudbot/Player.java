package com.indigo.mudbot;

import com.indigo.mudbot.Database.DatabaseCharacter;

public class Player {
    DatabaseCharacter character;
    String id;
    public Player(String id, DatabaseCharacter character){
        this.id = id;
        this.character = character;
    }

    public String getId() {
        return this.id;
    }

    public void Disconnect(){

    }
}
