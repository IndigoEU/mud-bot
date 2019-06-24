package com.indigo.mudbot.Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Ping extends Command {
    @Override
    protected void execute(CommandEvent event) {
        event.reply("Ping is "+event.getJDA().getPing());
    }
}
