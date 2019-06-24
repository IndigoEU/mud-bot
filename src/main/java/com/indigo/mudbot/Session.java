package com.indigo.mudbot;

public class Session {
    public int Id;
    public String name;
    public Player[] players;
    public DungeonMap map;
    public Access access;

    public Session(Player[] players, Access access){
        this.Id = SessionHandler.getId();
    }
}
