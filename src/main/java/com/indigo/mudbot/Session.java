package com.indigo.mudbot;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Session {
    private String moves;
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
    private EventWaiter waiter;
    private String currentMovement;
    private boolean start;

    Session(int id, Player[] players, MessageChannel[] statusChannels, EventWaiter waiter, Access access){
        this.id = id;
        this.waiter = waiter;
        this.access = access;
        this.statusChannels = statusChannels;
        this.start = true;
        this.players = players;
        this.map = new DungeonMap(String.valueOf(id));
    }

    void StartSession(){
        UpdateSession();
    }

    Player[] getPlayers(){
        return this.players;
    }

    void UpdateSession() {
        this.moves = "";
        this.currentMovement = map.getRoom();
        List<String> accepted = new ArrayList<>();
        MessageBuilder messageBuilder = new MessageBuilder();
        EmbedBuilder builder = new EmbedBuilder();
        if(start) {
            builder.setTitle("Welcome to the dungeon");
            StringBuilder description = new StringBuilder("Once upon a time, our heroes, ");
            for (int i = 0; i < players.length - 1; i++) {
                description.append(players[i].getName());
            }
            description.append("and ").append(players[players.length - 1].getName()).append(" woke up in a seemingly huge dungeon...")
                    .append("\nWhere shall our heroes venture next?");
            builder.setDescription(description.toString())
                    .setImage("attachment://" + this.currentMovement + ".png");
            pureMessage = builder;
        }
        else {
            builder.setTitle("As you enter the next room, the last collapses.");
            String description = "Deeper into dungeon, our heroes venture." + "\nWho knows for how much longer they will have to endure" +
                    "\nWhere shall our heroes venture next?";
            builder.setDescription(description)
                    .setImage("attachment://" + this.currentMovement + ".png");
            pureMessage = builder;
        }
        messageBuilder.setEmbed(builder.build());
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        System.out.println(this.currentMovement);
        currentImg = new File(classLoader.getResource("Environment/" + this.currentMovement +".png").getFile());
        for (MessageChannel channel : statusChannels) {
            channel.sendFile(currentImg, this.currentMovement + ".png", messageBuilder.build()).queue(msg -> statusMessage.add(msg));
            start = false;
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

    public String getDirection() {
        return this.currentMovement;
    }

    public void Move(String name, String s){
        SendMessage(name, s);
        moves += s + " ";
        int u = 0, d = 0, r = 0, l = 0;
        for(String string : moves.split(" ")){
            switch(string.toLowerCase()) {
                case "u":
                    u++;
                    break;
                case "d":
                    d++;
                    break;
                case "r":
                    r++;
                    break;
                case "l":
                    l++;
                    break;
            }
            if(u > players.length/2 || d > players.length/2 || r > players.length/2 || l > players.length/2){
                MoveInDirection();
            }
        }
    }

    private void MoveInDirection() {
        if(start) {
            UpdateSession();
        }
        else{
            new Encounter(statusChannels, this, waiter);
        }
    }
}
