package com.indigo.mudbot;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

import java.io.File;
import java.util.Random;

public class Encounter {
    private EventWaiter waiter;
    private final MessageChannel[] channels;
    private Session session;
    private Random random;
    private File encounterImage;
    public Encounter(MessageChannel[] channels, Session session, EventWaiter waiter){
        this.waiter = waiter;
        this.channels = channels;
        this.session = session;
        this.random = new Random();
        int randomInt = random.nextInt(101);
        if(randomInt > 90){
            EncounterShop();
        }
        else if(randomInt > 30){
            EncounterMonster();
        }
        else if(randomInt > 10){
            EncounterNothing();
        }
        else{
            EncounterBoss();
        }
    }

    private void EncounterBoss() {
    }

    private void EncounterMonster() {

    }

    private void EncounterNothing() {
        session.UpdateSession();
    }

    private void EncounterShop() {
        Random rnd = new Random();
        int randomInt = rnd.nextInt(Values.Shops.size())+1;
        Shop shop = Values.Shops.get(randomInt);
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        this.encounterImage = new File(classLoader.getResource("Shops/" + randomInt +".png").getFile());
        MessageBuilder messageBuilder = new MessageBuilder();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("A shop!")
                .setDescription("As you travel through the dungeon, you come across a merchant!\nHe sells: \n" + shop.getItem1().getAssetName() + " " + shop.getItem1().price + Values.CURRENCY + "\n" +
                        shop.getItem2().getAssetName() + " " + shop.getItem2().price + Values.CURRENCY + "\n" +
                        shop.getItem3().getAssetName() + " " + shop.getItem3().price + Values.CURRENCY)
                .setImage("attachment://" + randomInt + ".png")
                .setColor(0xE6B20F);
        messageBuilder.setEmbed(embedBuilder.build());
        for(MessageChannel channel : channels){
            channel.sendFile(encounterImage, randomInt+".png", messageBuilder.build()).queue(result -> {
                result.addReaction(shop.getItem1().getAssetName()).queue();
                result.addReaction(shop.getItem2().getAssetName()).queue();
                result.addReaction(shop.getItem3().getAssetName()).queue();
                result.addReaction("❌").queue();
                waiter.waitForEvent(MessageReactionAddEvent.class, e -> e.getUser().getId().equals(result.getAuthor().getId()), event -> {
                    final String chosenReaction = event.getReaction().getReactionEmote().getName();
                    final String item1 = shop.getItem1().getAssetName();
                    final String item2 = shop.getItem2().getAssetName();
                    final String item3 = shop.getItem3().getAssetName();
                    int choice;
                    if(chosenReaction.equals(item1))choice = 1;
                    else if(chosenReaction.equals(item2))choice = 2;
                    else if(chosenReaction.equals(item3))choice = 3;
                    else if(chosenReaction.equals("❌"))choice = 4;
                    else choice = 4;
                    switch (choice){
                        case 1:
                            session.
                    }
                });
            });
        }

    }
}
