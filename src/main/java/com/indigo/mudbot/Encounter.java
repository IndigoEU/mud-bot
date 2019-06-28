package com.indigo.mudbot;

import com.indigo.mudbot.Monsters.Monster;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Encounter {
    private EventWaiter waiter;
    private MessageChannel[] channels;
    private Session session;
    private Random random;
    private File encounterImage;
    public Encounter(MessageChannel[] channels, Session session, EventWaiter waiter){
        this.waiter = waiter;
        this.channels = channels;
        this.session = session;
        this.random = new Random();
        int randomInt = random.nextInt(101);
        System.out.println(randomInt);
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
        session.UpdateSession();
    }

    private void EncounterMonster() {
        int selectMonster = random.nextInt(Values.Monsters.size())+1;
        System.out.println(selectMonster + " < selectmonster");
        Monster monster = Values.Monsters.get(selectMonster);

        int monsterPower = monster.accuracy/10 * monster.damage * monster.speed/5;
        int playerPower = 0;
        for(int i = 0; i < session.getPlayers().length; i++){
            int weapon = session.getPlayers()[i].character.getEquipWeapon();
            playerPower += Values.Weapons.get(weapon).attack * Values.Weapons.get(weapon).accuracy/10 * Values.Weapons.get(weapon).speed/5;
        }
        if(playerPower > monsterPower){
            for(int i=0; i<session.getPlayers().length; i++)
            {
                session.getPlayers()[i].character.gainXP(monster.rewardXP);
                session.getPlayers()[i].character.gainMoney(monster.loot);
            }
        }
        else{
            System.out.println("Game over");
            return;
        }
    }

    /*private void CheckForExistence(){
        if(statusMessages.size() > 0){

        }
    }


















    private void MonsterEncounterRestart(Monster monster) {
        if (statusMessages.size() > 0) {
            for (Message msg : statusMessages) {
                ClassLoader classloader = ClassLoader.getSystemClassLoader();
                System.out.println(monster.getAssetName());
                File file = new File(classloader.getResource("Monsters/" + monster.getAssetName() + ".png").getFile());
                MessageBuilder mb = new MessageBuilder();
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("A monster encounter!");
                eb.setImage("attachment://" + monster.getAssetName() + ".png");
                eb.setDescription("You encountered a " + monster.getName() + "\n" +
                        "HP: " + monster.getHp() + "\n");
                for (int j = 0; j < session.getPlayers().length; j++) {
                    eb.addField(session.getPlayers()[j].getName(), "Hp: " + session.getPlayers()[j].character.getCurrentHp(), false);
                }
                mb.setEmbed(eb.build());
                msg.editMessage(mb.build()).queue();
                for (MessageChannel channel : channels) {
                    channel.sendFile(file, monster.getAssetName() + ".png", mb.build()).queue(msg2 -> {
                        if (statusMessages.size() < session.getPlayers().length) {
                            statusMessages.add(msg2);
                        }
                        msg2.addReaction("<:Attack:594085449740320780").queue();
                        msg2.addReaction("<:Escape:594083888628760576").queue();
                        waiter.waitForEvent(MessageReactionAddEvent.class, ev -> ev.getChannel().equals(Main.getSessionHandler().getChannelMap().get(ev.getUser().getId())), res -> {
                            Player player = Main.getSessionHandler().getUserMap().get(res.getUser().getId());

                            if (res.getReaction().getReactionEmote().getName().equals("Attack") && player.character.getCurrentHp() > 0) {
                                while(player.character.getCurrentHp() > 0 && monster.currentHP > 0) {
                                    player.character.dealDamage(monster.damage);
                                    monster.dealDamage(Values.Weapons.get(player.character.getEquipWeapon()).attack);
                                }
                                session.UpdateSession();
                            } else if (res.getReaction().getReactionEmote().getName().equals("Escape") && player.character.getCurrentHp() > 0) {
                                Random rnd = new Random();
                                int chance = rnd.nextInt(10);
                                EmbedBuilder embed = new EmbedBuilder();
                                float d = Values.Weapons.get(player.character.getEquipWeapon()).speed;
                                chance += d;
                                boolean escaped = false;
                                if (chance == monster.speed) {
                                    embed.setTitle("You've managed to escape!")
                                            .setColor(0x00FF00);
                                    escaped = true;
                                } else {
                                    embed.setTitle("You've failed to escape! Monster used that chance and attacked you!")
                                            .setColor(0xFF0000);
                                    int damage = monster.attack();
                                    player.character.dealDamage(damage);
                                    escaped = false;
                                }
                                if(escaped) {
                                    if(!updatedSession) session.UpdateSession();
                                    updatedSession = true;
                                    return;
                                }
                                else MonsterEncounterRestart(monster);
                            } else {
                                boolean alive = false;
                                for (Player playerChar : session.getPlayers()) {
                                    if (!(playerChar.character.getCurrentHp() <= 0)) alive = true;
                                }
                                if (alive) MonsterEncounterRestart(monster);
                                else {
                                    channel.sendMessage("GAME OVER").queue();
                                    session.UpdateSession();
                                }
                            }
                        });
                    });
                }
            }
        } else {
            ClassLoader classloader = ClassLoader.getSystemClassLoader();
            System.out.println(monster.getAssetName());
            File file = new File(classloader.getResource("Monsters/" + monster.getAssetName() + ".png").getFile());
            MessageBuilder mb = new MessageBuilder();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("A monster encounter!");
            eb.setImage("attachment://" + monster.getAssetName() + ".png");
            eb.setDescription("You encountered a " + monster.getName() + "\n" +
                    "HP: " + monster.getHp() + "\n");
            for (int i = 0; i < session.getPlayers().length; i++) {
                eb.addField(session.getPlayers()[i].getName(), "Hp: " + session.getPlayers()[i].character.getCurrentHp(), false);
            }
            mb.setEmbed(eb.build());
            for (MessageChannel channel : channels) {
                channel.sendFile(file, monster.getAssetName() + ".png", mb.build()).queue(msg -> {
                    if (statusMessages.size() < session.getPlayers().length) {
                        statusMessages.add(msg);
                    }
                    msg.addReaction("<:Attack:594085449740320780").queue();
                    msg.addReaction("<:Escape:594083888628760576").queue();
                    waiter.waitForEvent(MessageReactionAddEvent.class, ev -> ev.getChannel().equals(Main.getSessionHandler().getChannelMap().get(ev.getUser().getId())), res -> {
                        Player player = Main.getSessionHandler().getUserMap().get(res.getUser().getId());

                        if (res.getReaction().getReactionEmote().getName().equals("Attack") && player.character.getCurrentHp() > 0) {
                            while(player.character.getCurrentHp() > 0 && monster.currentHP > 0) {
                                player.character.dealDamage(monster.damage);
                                monster.dealDamage(Values.Weapons.get(player.character.getEquipWeapon()).attack);


                            }
                        } else if (res.getReaction().getReactionEmote().getName().equals("Escape") && player.character.getCurrentHp() > 0) {
                            Random rnd = new Random();
                            int chance = rnd.nextInt(10);
                            EmbedBuilder embed = new EmbedBuilder();
                            float d = Values.Weapons.get(player.character.getEquipWeapon()).speed;
                            chance += d;
                            boolean escaped = false;
                            if (chance == monster.speed) {
                                embed.setTitle("You've managed to escape!")
                                        .setColor(0x00FF00);
                                escaped = true;
                            } else {
                                embed.setTitle("You've failed to escape! Monster used that chance and attacked you!")
                                        .setColor(0xFF0000);
                                int damage = monster.attack();
                                player.character.dealDamage(damage);
                                escaped = false;
                            }
                            if(escaped) {
                                if(!updatedSession) session.UpdateSession();
                                updatedSession = true;
                                return;
                            }
                            else MonsterEncounterRestart(monster);
                        } else {
                            boolean alive = false;
                            for (Player playerChar : session.getPlayers()) {
                                if (!(playerChar.character.getCurrentHp() <= 0)) alive = true;
                            }
                            if (alive) MonsterEncounterRestart(monster);
                            else {
                                channel.sendMessage("GAME OVER").queue();
                                session.UpdateSession();
                            }
                        }
                    });
                });
            }
        }
    }*/

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
                waiter.waitForEvent(MessageReactionAddEvent.class, e -> Main.getSessionHandler().getUserMap().containsKey(e.getUser().getId()), event -> {
                    final String chosenReaction = event.getReaction().getReactionEmote().getName();
                    int choice;
                    if (chosenReaction.equals(shop.getItem1().getEmoteName())) choice = 1;
                    else if (chosenReaction.equals(shop.getItem2().getEmoteName())) choice = 2;
                    else if (chosenReaction.equals(shop.getItem3().getEmoteName())) choice = 3;
                    else if (chosenReaction.equals("\u274c")) choice = 4;
                    else choice = 4;
                    Player player = Main.getSessionHandler().getUserMap().get(event.getUser().getId());
                    int currentMoney = player.character.getCurrency();
                    switch (choice) {
                        case 1:
                            if(currentMoney < shop.getItem1().price){
                                EmbedBuilder eb = new EmbedBuilder()
                                        .setTitle("You can't afford that.")
                                        .setColor(0xFF0000);
                                event.getChannel().sendMessage(eb.build()).queue();
                            }
                            else {

                            }
                            break;
                        case 2:
                            if(currentMoney < shop.getItem2().price){
                                EmbedBuilder eb = new EmbedBuilder()
                                        .setTitle("You can't afford that.")
                                        .setColor(0xFF0000);
                                event.getChannel().sendMessage(eb.build()).queue();
                            }
                            else {

                            }
                            break;
                        case 3:
                            if(currentMoney > shop.getItem3().price){
                                EmbedBuilder eb = new EmbedBuilder()
                                        .setTitle("You can't afford that.")
                                        .setColor(0xFF0000);
                                event.getChannel().sendMessage(eb.build()).queue();
                            }
                            else {
                                player.character.addItem(shop.getItem3().id);
                            }
                            break;
                        case 4:
                            session.UpdateSession();
                            break;
                    }
                });
            });
        }

    }/*
    private UnusedInventory(Player player){
        StringBuilder sb = new StringBuilder();
        int[][] invSlots = Main.getDatabase().getJsonDBTemplate().findById(player.character.getInventoryId(), DatabaseCharacterInventory.class).getInventory();
        for (int[] inventoryRow : invSlots) {
            for (int inventorySlot : inventoryRow) {
                if (inventorySlot == 0) {
                    sb.append(Values.NO_ITEM);
                } else {
                    sb.append(Values.Items.get(inventorySlot).getAssetName());
                }
            }
            sb.append("\n");
        }
        msg.editMessage(new EmbedBuilder()
                .setTitle("Select an item to use")
                .setDescription(sb.toString()).build()).queue();
        waiter.waitForEvent(MessageReceivedEvent.class, e -> e.getChannel().equals(Main.getSessionHandler().getChannelMap().get(e.getAuthor().getId())), e -> {
            String[] array = e.getMessage().getContentRaw().split(":");
            int[] numArray = new int[2];
            int selectItem;
            try {
                numArray[0] = Integer.parseInt(array[0]);
                numArray[1] = Integer.parseInt(array[1]);
                selectItem = invSlots[numArray[0] - 1][numArray[1] - 1];
            } catch (Exception ex) {
                e.getChannel().sendMessage(new EmbedBuilder()
                        .setTitle("Invalid item selected")
                        .setColor(0xFF0000).build()).queue();
                return;
            }
            if (!Values.Usable.containsKey(selectItem)) {
                e.getChannel().sendMessage(new EmbedBuilder()
                        .setTitle("This item cannot be used")
                        .setColor(0x00FF00).build()).queue();
            } else {
                player.character.setCurrentHp(player.character.getCurrentHp() + 50);
            }
        });
    }*/
}
