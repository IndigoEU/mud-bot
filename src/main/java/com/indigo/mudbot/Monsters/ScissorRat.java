package com.indigo.mudbot.Monsters;

public class ScissorRat extends Monster {
    public ScissorRat(){
        this.name = "Rat with Scissors";
        this.assetName = "scissorrat";
        this.maxHP = 80;
        this.currentHP = 80;
        this.damage = 20;
        this.rewardXP = 150;
        this.spawnChance = 2;
        this.loot = 75;
    }
}