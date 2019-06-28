package com.indigo.mudbot.Items;

public class Item {
    public int rarity;
    public String description;
    public int price;
    public boolean canDrop;
    public String name;
    public String assetName;
    public String emoteName;
    public int id;

    public String getName() {
        return name;
    }
    public String getAssetName() {
        return assetName;
    }
    public String getEmoteName() { return emoteName; }
    public int getId() { return id; }
}