package com.indigo.mudbot;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;


@Document(collection = "character", schemaVersion = "1.0")
public class DatabaseCharacter {
    @Id
    private String characterId;
    private String inventoryId;
    private int currentHp;
    private int maxHp;
    private int equipArmor;
    private int equipWeapon;
}
