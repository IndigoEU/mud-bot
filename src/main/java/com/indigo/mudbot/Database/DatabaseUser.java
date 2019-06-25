package com.indigo.mudbot.Database;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

import javax.annotation.Nullable;

@Document(collection = "user", schemaVersion = "1.0")
public class DatabaseUser {
    @Id
    private String userId;
    private int slot1;
    private int slot2;
    private int slot3;
    private int slot4;
    private boolean owo;
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public int getSlot1() { return slot1; }
    public void setSlot1(int slot1) {
        this.slot1 = slot1;
    }
    public int getSlot2() { return slot2; }
    public void setSlot2(int slot2) {
        this.slot2 = slot2;
    }
    public int getSlot3() {
        return slot3;
    }
    public void setSlot3(int slot3) {
        this.slot3 = slot3;
    }
    public int getSlot4() {
        return slot4;
    }
    public void setSlot4(int slot4) { this.slot4 = slot4; }
    public boolean getOwO() { return owo; }
    public void setOwO(boolean owo) { this.owo = owo; }
}
