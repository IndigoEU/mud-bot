package com.indigo.mudbot.Monsters;

public class GnomeReaper extends Monster {
    public GnomeReaper() {
        this.name = "Gnome Reaper";
        this.assetName = "gnomereaper";
        this.maxHP = 100;
        this.currentHP = 100;
        this.damage = 15;
        this.rewardXP = 100;
        this.spawnChance = 3;
        this.speed = 7;
        this.accuracy = 40;
    }
}