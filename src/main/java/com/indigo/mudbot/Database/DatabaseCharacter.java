package com.indigo.mudbot.Database;

import com.indigo.mudbot.Main;
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
    private int equipWeapon;
    private int currency;
    private int xp;
    private int level;
    private int xpToLevel;

    public int getCurrency(){ return currency; }
    public void setCurrency(int currency){ this.currency = currency; }
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
    public int getEquipWeapon() { return equipWeapon; }
    public void setEquipWeapon(int equipWeapon) { this.equipWeapon = equipWeapon; }
    public int dealDamage(int damage) {
        this.currentHp -= damage;
        return this.currentHp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
    public void setXpToLevel(int xp) { this.xpToLevel = xp; }

    public void gainXP(int xp) {
        this.xp += xp;
        if(this.xp >= this.xpToLevel)
            levelUp();
    }
    private void levelUp(){
        this.maxHp += 25;
        this.currentHp = this.maxHp;
        this.xpToLevel *= 1.5;
        this.xp = 0;
    }

    public void gainMoney(int c) { this.currency += c; }
    public void setMoney(int c) { this.currency = c; }

    public boolean addItem(int id) {
        DatabaseCharacterInventory inventory = Main.getDatabase().getJsonDBTemplate().findById(this.inventoryId, DatabaseCharacterInventory.class);
        int[][] inv = inventory.getInventory();
        boolean end = false;
        for(int[] invRow : inv){
            for(int invSlot : invRow){
                if(invSlot == 0){
                    end = true;
                    invSlot = id;
                    break;
                }
            }
            if(end) {
                return true;
            }
        }
        return false;
    }
}
