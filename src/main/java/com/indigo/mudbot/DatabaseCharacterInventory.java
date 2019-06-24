package com.indigo.mudbot;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

@Document(collection = "inventory", schemaVersion = "1.0")
public class DatabaseCharacterInventory {
    @Id
    private String inventoryId;
    private int[] row1;
    private int[] row2;
    private int[] row3;
    private int[] row4;
}
