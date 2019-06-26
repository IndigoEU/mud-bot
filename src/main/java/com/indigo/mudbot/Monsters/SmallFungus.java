package com.indigo.mudbot.Monsters;

public class SmallFungus extends MonsterTemplate {
    public SmallFungus() {
        this.name = "Small Fungus";
        this.assetName = "fungus";
        this.maxHP = 40;
        this.damage = 10;
        this.rewardXP = 50;
        this.spawnChance = 4;
    }
}