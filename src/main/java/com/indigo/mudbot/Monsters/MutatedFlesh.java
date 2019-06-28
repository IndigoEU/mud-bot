package com.indigo.mudbot.Monsters;

public class MutatedFlesh extends Monster {
    public MutatedFlesh() {
        this.name = "Mutated Flesh";
        this.assetName = "flesh";
        this.maxHP = 70;
        this.currentHP = 70;
        this.damage = 15;
        this.rewardXP = 75;
        this.spawnChance = 4;
        this.speed = 1;
        this.accuracy = 55;
        this.loot = 35;
    }
}