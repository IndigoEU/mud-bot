package com.indigo.mudbot.Database;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;


@Document(collection = "character", schemaVersion = "1.0")
public class DatabaseCharacter {
    @Id
    private String characterId;
    private String inventoryId;
    private String name;
    private int currentHp;
    private int maxHp;
    private int equipArmor;
    private int equipWeapon;

    public String getCharacterId() { return characterId; }
    public void setCharacterId(String characterId) { this.characterId = characterId; }
    public String getInventoryId() { return inventoryId; }
    public void setInventoryId(String inventoryId) { this.inventoryId = inventoryId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCurrentHp() { return currentHp; }
    public void setCurrentHp(int currentHp) { this.currentHp = currentHp; }
    public int getMaxHp() { return maxHp; }
    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }
    public int getEquipArmor() { return equipArmor; }
    public void setEquipArmor(int equipArmor) { this.equipArmor = equipArmor; }
    public int getEquipWeapon() { return equipWeapon; }
    public void setEquipWeapon(int equipWeapon) { this.equipWeapon = equipWeapon; }

}
