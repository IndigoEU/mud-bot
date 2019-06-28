package com.indigo.mudbot.Items;

public class HealingPotion extends Item {
    public HealingPotion() {
        this.name = "Healing Potion";
        this.assetName = "<:PotionMud:593852143165767701>";
        this.rarity = 3;
        this.price = 100;
        this.canDrop = true;
        this.description = "A rare brew capable of closing wounds. Drink carefully - it's easy to get addicted to it.";
    }
    public int Use() {
        return 50;
    }
}