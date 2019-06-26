package com.indigo.mudbot.Commands;

import com.indigo.mudbot.Main;
import com.indigo.mudbot.Session;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Say extends Command {
    public Say(){
        this.name = "say";
    }

    @Override
    protected void execute(CommandEvent event) {
        Session enter = Main.getSessionHandler().getSession(event.getAuthor().getId());
        enter.SendMessage(event.getAuthor().getName(), event.getArgs());
    }
}
