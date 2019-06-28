package com.indigo.mudbot.Monsters;

public class SmallFungus extends Monster {
    public SmallFungus() {
        this.name = "Small Fungus";
        this.assetName = "fungus";
        this.maxHP = 40;
        this.currentHP = 40;
        this.damage = 10;
        this.rewardXP = 50;
        this.spawnChance = 4;
        this.speed = 5;
        this.accuracy = 55;
    }
}