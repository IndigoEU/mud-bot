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
    private static Map<String, Session> sessionMap = new HashMap<>();
    private static Map<String, MessageChannel> channelMap = new HashMap<>();
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
        List<MessageChannel> channels = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            playing[i] = players.get(0);
            channels.add(channelMap.get(playing[i].getId()));
            System.out.println(channelMap.get(playing[i].getId()));
            players.remove(0);
        }
        MessageChannel[] channelArray = channels.toArray(new MessageChannel[0]);
        Session session = new Session(sessionMap.size(), playing, channelArray, Access.All);
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
        if(players.size() >= 4) createSession(event);
    }


    public static int getId(){
        return 1;
    }
}
