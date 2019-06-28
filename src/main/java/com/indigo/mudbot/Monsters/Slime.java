package com.indigo.mudbot.Monsters;

public class Slime extends MonsterTemplate {
    public Slime() {
        this.name = "Slime";
        this.assetName = "slime";
        this.maxHP = 50;
        this.damage = 10;
        this.rewardXP = 70;
        this.spawnChance = 5;
        this.speed = 2;
        this.accuracy = 40;
    }
}