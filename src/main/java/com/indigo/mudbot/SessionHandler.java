package com.indigo.mudbot;

import com.indigo.mudbot.Database.DatabaseCharacter;
import com.indigo.mudbot.Functions.Send;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionHandler {
    private static Map<String, String> playerMap = new HashMap<>();
    private static Map<String, Session> sessionMap = new HashMap<>();
    private static Map<String, MessageChannel> channelMap = new HashMap<>();
    private List<Player> players = new ArrayList<>();

    public void createSession(CommandEvent event)
    {
        System.out.println("SESSION STARTING");
        List<Player> playing = new ArrayList<>();
        /*int amount = 0;
        for(Player join : joinPlayers){
            playing[amount] = join;
            amount++;
        }
        int joined = joinPlayers.length;*/

        List<MessageChannel> channels = new ArrayList<>();
        int size = players.size();
        for (int i = 0; i < 4 && i < size; i++){
            playing.add(players.get(0));
            channels.add(channelMap.get(players.get(0).getId()));
            System.out.println(channelMap.get(players.get(0).getId()));
            players.remove(0);
        }
        MessageChannel[] channelArray = channels.toArray(new MessageChannel[0]);
        for(Player player : playing) {
            playerMap.put(player.getId(), String.valueOf(sessionMap.size()));
        }
        Session session = new Session(sessionMap.size(), playing.toArray(new Player[0]), channelArray, Access.All);
        sessionMap.put(String.valueOf(sessionMap.size()), session);
        session.StartSession();
    }

    public int getListSize()
    {
        return players.size();
    }

    public List<Player> getPlayers(){
        return this.players;
    }
    public void addToQueue(CommandEvent event, DatabaseCharacter character)
    {
        Player player = new Player(event.getAuthor().getId(), character);
        players.add(player);
        channelMap.put(event.getAuthor().getId(), event.getChannel());
        Send.SimpleEmbed(event, "Joined queue, there are currently " + players.size() +" players in queue").queue();
        if(players.size() >= 2) createSession(event);
    }


    public static int getId(){
        return 1;
    }

    public Session getSession(String id) {
        return sessionMap.get(playerMap.get(id));
    }
}
