package com.indigo.mudbot;

public class Session {
    public int id;
    public Player[] players;
    public DungeonMap map;
    public Access access;
    public Status status;

    public Session(int id, Player[] players, Access access){
        this.id = SessionHandler.getId();
        this.access = access;
        this.map = new DungeonMap();
        this.players = players;
    }

    public void StartSession(){

    }
    public void SendMessage(){

    }
}
