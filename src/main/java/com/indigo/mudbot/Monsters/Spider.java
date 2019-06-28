package com.indigo.mudbot.Monsters;

public class Spider extends MonsterTemplate {
    public Spider() {
        this.name = "Spider";
        this.assetName = "spider";
        this.maxHP = 25;
        this.damage = 10;
        this.rewardXP = 50;
        this.spawnChance = 5;
        this.speed = 8;
        this.accuracy = 50;
    }
}