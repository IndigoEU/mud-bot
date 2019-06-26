package com.indigo.mudbot;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.io.File;

public class Session {
    public int id;
    public Player[] players;
    public DungeonMap map;
    public Message[] initMessage;
    public Access access;
    public MessageChannel[] statusChannels;
    public Status status;

    public Session(int id, Player[] players, MessageChannel[] statusChannels, Access access){
        this.id = SessionHandler.getId();
        this.access = access;
        this.statusChannels = statusChannels;
        this.map = new DungeonMap();
        this.players = players;
    }

    public void StartSession(){
        MessageBuilder messageBuilder = new MessageBuilder();
        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Welcome to the dungeon");
        StringBuilder description = new StringBuilder("Once upon a time, our heroes, ");
        for(int i = 0; i < players.length-1; i++){
            description.append(players[i]);
        }
        description.append("and ").append(players[players.length-1]).append(" woke up in a seemingly huge dungeon...")
                .append("\nWhere shall our heroes venture next?");

        builder.setDescription(description.toString())
                .setImage("attachment://LUR.png");
        messageBuilder.setEmbed(builder.build());
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource("Environment/LUR.png").getFile());
        for(MessageChannel channel : statusChannels){
            channel.sendFile(file, "LUR.png", messageBuilder.build()).queue();
        }
    }
    public void SendMessage(){

    }
}
