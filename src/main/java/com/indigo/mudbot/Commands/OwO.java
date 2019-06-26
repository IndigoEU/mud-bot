package com.indigo.mudbot.Commands;

import com.indigo.mudbot.Database.DatabaseUser;
import com.indigo.mudbot.Functions.DatabaseInfo;
import com.indigo.mudbot.Main;
import com.indigo.mudbot.Functions.Send;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class OwO extends Command {
    @Override
    protected void execute(CommandEvent event) {

        boolean owo = Main.getDatabase().getJsonDBTemplate().findById(event.getAuthor().getId(), DatabaseUser.class).getOwO();

        if(owo)
        {
            Main.getDatabase().getJsonDBTemplate().findById(event.getAuthor().getId(), DatabaseUser.class).setOwO(0);
            Send.SimpleEmbed(event, "OwO", "Un-OwOification complete. Be happy with freedom.");
        }
        else
        {
            Main.getDatabase().getJsonDBTemplate().findById(event.getAuthor().getId(), DatabaseUser.class).setOwO(1);
            Send.SimpleEmbed(event, "OwO", "OwOification compwete.");
        }
    }
}
