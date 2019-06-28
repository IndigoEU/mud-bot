package com.indigo.mudbot.Monsters;

public class SaltElemental extends Monster {
    public SaltElemental() {
        this.name = "Salt Elemental";
        this.assetName = "saltelemental";
        this.maxHP = 150;
        this.currentHP = 150;
        this.damage = 22;
        this.rewardXP = 250;
        this.spawnChance = 1;
        this.speed = 9;
        this.accuracy = 65;
    }
}