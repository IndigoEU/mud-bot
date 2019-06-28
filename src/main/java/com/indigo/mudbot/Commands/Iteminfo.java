package com.indigo.mudbot.Commands;

import com.indigo.mudbot.Functions.Send;
import com.indigo.mudbot.Main;
import com.indigo.mudbot.Values;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Iteminfo extends Command {
    public Iteminfo() {
        this.name = "iteminfo";
        this.help = "Let's see you the item information";
    }

    @Override
    protected void execute(CommandEvent event) {
        int key = Integer.parseInt(event.getArgs());

        String stats = "Name: " + Values.Items.get(key).name +
                        "\n" + Values.Items.get(key).description +
                        "\nValue: " + Values.Items.get(key).price +
                        "\nRarity: " + Values.Items.get(key).rarity;
        if(key < 12)
        {
            stats += "\nDamage: " + Values.Weapons.get(key).attack +
                    "\nAccuracy: " + Values.Weapons.get(key).accuracy;
        }
        if(key <= 14 && key > 0)
            Send.SimpleEmbed(event, Values.Items.get(key).name, stats);

        if(key < 0 || key > 14)
            Send.SimpleEmbed(event, "Invalid item chosen. Choose an id between 1 and 14.");
    }
}
