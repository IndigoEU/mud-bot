package com.indigo.mudbot;

import com.indigo.mudbot.Functions.Send;
import com.indigo.mudbot.Monsters.MonsterTemplate;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Encounter {
    private EventWaiter waiter;
    private final MessageChannel[] channels;
    private List<Message> statusMessages = new ArrayList<>();
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
        int selectMonster = random.nextInt(Values.Monsters.size());
        MonsterTemplate monster = Values.Monsters.get(selectMonster);
        MonsterEncounterRestart(monster);





















    }

    private void MonsterEncounterRestart(MonsterTemplate monster) {
        ClassLoader classloader = ClassLoader.getSystemClassLoader();
        File file = new File(classloader.getResource("Monsters/"+monster.getAssetName()+".png").getFile());
        MessageBuilder mb = new MessageBuilder();
        EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("A monster encounter!");
            eb.setImage("attachment://"+monster.getAssetName()+".png");
            eb.setDescription("You encountered a "+monster.getName()+"\n" +
            "It is a " + monster.name + "\n" +
            "HP: " + monster.getHp() + "\n");
            for(int i = 0; i < session.getPlayers().length; i++){
                eb.addField(session.getPlayers()[i].getName(), "Hp: " + session.getPlayers()[i].character.getCurrentHp(), false);
            }
        mb.setEmbed(eb.build());
        for(MessageChannel channel : channels){
            channel.sendFile(file, monster.getAssetName()+".png", mb.build()).queue(msg -> {
                if(statusMessages.size() < 4) {
                    statusMessages.add(msg);
                }
                msg.addReaction("<:Attack:594085449740320780>").queue();
                msg.addReaction("<:Items:594083888356392971>").queue();
                msg.addReaction("<:Escape:594083888628760576>").queue();
                waiter.waitForEvent(MessageReactionAddEvent.class, ev -> ev.getUser().getId().equals(true), res -> {
                    Player player = Main.getSessionHandler().getUserMap().get(res.getUser().getId());
                    if(player == null){
                        res.getChannel().sendMessage(new EmbedBuilder()
                                .setTitle("You aren't part of this game")
                                .setColor(0xFF0000).build()).queue();
                        MonsterEncounterRestart(monster);
                    }
                    else if(!(Arrays.asList(session.getPlayers()).contains(player))){
                        res.getChannel().sendMessage(new EmbedBuilder()
                                .setTitle("You aren't part of this game")
                                .setColor(0xFF0000).build()).queue();
                        MonsterEncounterRestart(monster);
                    }
                    else if(res.getReaction().getReactionEmote().getName().equals("<:Attack:594085449740320780>")) {
                        if(monster.speed > Values.Weapons.get(player.character.getEquipWeapon()).speed){
                            int damage = monster.getAttack();
                            player.character.dealDamage(damage);
                            monster.dealDamage(Values.Weapons.get(player.character.getEquipWeapon()).attack);
                            MonsterEncounterRestart(monster);
                        }
                        else {
                            int damage = monster.getAttack();
                            player.character.dealDamage(damage);
                            monster.dealDamage(Values.Weapons.get(player.character.getEquipWeapon()).attack);
                            MonsterEncounterRestart(monster);
                        }
                    }
                    else if(res.getReaction().getReactionEmote().getName().equals("<:Items:594083888356392971>")){
                        //TBD
                        MonsterEncounterRestart(monster);
                    }
                    else if(res.getReaction().getReactionEmote().getName().equals("<:Escape:594083888628760576>")){
                        Random rnd = new Random();
                        int chance = rnd.nextInt(100);
                        if(chance > 80){
                            EmbedBuilder embed = new EmbedBuilder()
                                    .setTitle("You've managed to escape!")
                                    .setColor(0x00FF00);
                            for(MessageChannel channelRun : channels){
                                channelRun.sendMessage(embed.build()).queue();
                                session.UpdateSession();
                            }
                        }
                    }
                    else{
                        MonsterEncounterRestart(monster);
                    }
                });
            });
        }
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
                result.addReaction(shop.getItem1().getAssetName().replace(">", "")).queue();
                result.addReaction(shop.getItem2().getAssetName().replace(">", "")).queue();
                result.addReaction(shop.getItem3().getAssetName().replace(">", "")).queue();
                result.addReaction("\u274C").queue();
                waiter.waitForEvent(MessageReactionAddEvent.class, e -> e.getUser().getId().equals(result.getAuthor().getId()), event -> {
                    final String chosenReaction = event.getReaction().getReactionEmote().getName();
                    final String item1 = shop.getItem1().getAssetName().replace(">", "");
                    final String item2 = shop.getItem2().getAssetName().replace(">", "");
                    final String item3 = shop.getItem3().getAssetName().replace(">", "");
                    int choice;
                    if(chosenReaction.equals(item1))choice = 1;
                    else if(chosenReaction.equals(item2))choice = 2;
                    else if(chosenReaction.equals(item3))choice = 3;
                    else if(chosenReaction.equals("\u274c"))choice = 4;
                    else choice = 4;
                    switch (choice){
                        case 1:
                            
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            session.UpdateSession();
                            break;
                    }
                });
            });
        }

    }
}
