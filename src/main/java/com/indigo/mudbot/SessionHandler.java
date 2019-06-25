package com.indigo.mudbot;

import com.indigo.mudbot.Database.DatabaseCharacter;
import com.indigo.mudbot.Functions.Send;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionHandler {
    private static Map<String, Session> sessionMap = new HashMap<>();
    private List<Player> players = new ArrayList<>();

    public void createSession(CommandEvent event)
    {
        System.out.println("SESSION STARTING");
        Player[] playing = new Player[4];
        /*int amount = 0;
        for(Player join : joinPlayers){
            playing[amount] = join;
            amount++;
        }
        int joined = joinPlayers.length;*/
        for (int i = 0; i < 4; i++){
            playing[i] = players.get(0);
            players.remove(0);
        }
        sessionMap.put(String.valueOf(sessionMap.size()), new Session(sessionMap.size(), playing, Access.All));
        Send.SimpleEmbed(event, "Session is starting now").queue();
    }

    public int getListSize()
    {
        return players.size();
    }

    public void addToQueue(CommandEvent event, DatabaseCharacter character)
    {
        Player player = new Player(event.getAuthor().getId(), character);
        players.add(player);
        Send.SimpleEmbed(event, "Joined queue, there are currently " + players.size() +" players in queue").queue();
        if(players.size() >= 4) createSession(event);
    }


    public static int getId(){
        return 1;
    }
}
