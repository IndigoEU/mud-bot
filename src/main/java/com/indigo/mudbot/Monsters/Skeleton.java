package com.indigo.mudbot.Monsters;

public class Skeleton extends Monster {
    public Skeleton() {
        this.name = "Skeleton";
        this.assetName = "skeleton";
        this.maxHP = 40;
        this.damage = 8;
        this.rewardXP = 50;
        this.spawnChance = 5;
        this.speed = 3;
        this.accuracy = 45;
    }
}