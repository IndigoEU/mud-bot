package com.indigo.mudbot;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Session {
    private File currentImg;
    private int id;
    private Player[] players;
    private DungeonMap map;
    private List<Message> statusMessage = new ArrayList<>();
    private EmbedBuilder pureMessage;
    private Access access;
    private MessageChannel[] statusChannels;
    private Status status;
    private Stack<String[]> chat = new Stack<>();

    Session(int id, Player[] players, MessageChannel[] statusChannels, Access access){
        this.id = SessionHandler.getId();
        this.access = access;
        this.statusChannels = statusChannels;
        this.map = new DungeonMap(5, 5);
        this.players = players;
    }

    void StartSession(){
        UpdateSession();
    }


    void UpdateSession(){
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
        pureMessage = builder;
        messageBuilder.setEmbed(builder.build());
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        currentImg = new File(classLoader.getResource("Environment/LUR.png").getFile());
        for(MessageChannel channel : statusChannels){
            channel.sendFile(currentImg, "LUR.png", messageBuilder.build()).queue(msg -> statusMessage.add(msg));
        }
    }
    public void SendMessage(String name, String text){
        chat.push(new String[]{name, text});
        EmbedBuilder embedBuilder = pureMessage;
        int counter = 0;
        for(int i = chat.size(); i > 0 && counter < 25; i--){
            String[] chatVal = chat.get(i-1);
            embedBuilder.addField(chatVal[0], chatVal[1],false);
            chat.remove(i-1);
            counter++;
        }
        MessageBuilder messageBuilder = new MessageBuilder()
                .setEmbed(embedBuilder.build());
        for(Message msg : statusMessage){
            msg.editMessage(messageBuilder.build()).queue();
        }
    }
}
