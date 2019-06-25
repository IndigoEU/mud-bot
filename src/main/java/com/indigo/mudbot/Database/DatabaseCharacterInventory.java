package com.indigo.mudbot.Database;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

@Document(collection = "inventory", schemaVersion = "1.0")
public class DatabaseCharacterInventory {
    @Id
    private String inventoryId;
    private int[][] inventory;

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int[][] getInventory() {
        return inventory;
    }

    public void setInventory(int[][] inventory) {
        this.inventory = inventory;
    }
}
