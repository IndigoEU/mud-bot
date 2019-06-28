package com.indigo.mudbot.Monsters;

public class DemonCat extends MonsterTemplate {
    public DemonCat() {
        this.name = "Demon Cat";
        this.assetName = "demoncat";
        this.maxHP = 100;
        this.damage = 20;
        this.rewardXP = 100;
        this.spawnChance = 3;
        this.speed = 8;
        this.accuracy = 40;
    }
}