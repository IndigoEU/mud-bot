package com.indigo.mudbot.Commands;

import com.indigo.mudbot.Functions.Send;
import com.indigo.mudbot.Main;
import com.indigo.mudbot.Session;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Move extends Command {
    public Move(){
        this.name = "move";
        this.description = "let´s you move around and explore the dungeon"
        this.guildOnly = false;
    }


    @Override
    protected void execute(CommandEvent event) {
        Session enter = Main.getSessionHandler().getSession(event.getAuthor().getId());
        String directions;
        try {
            directions = enter.getDirection();
        } catch(NullPointerException ex){
            Send.SimpleEmbed(event, "You're not in any session").queue();
            return;
        }
        String[] directionArray = directions.split("");
        List<String> direction = new ArrayList<>(Arrays.asList(directionArray));
        System.out.println(direction);
        if(direction.contains(event.getArgs().toUpperCase())){
            enter.Move(event.getAuthor().getName(), event.getArgs().toUpperCase());
        }
        else Send.SimpleEmbed(event, "Invalid direction specified").queue();
    }
}
