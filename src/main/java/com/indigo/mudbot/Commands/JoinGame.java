package com.indigo.mudbot.Commands;

import com.indigo.mudbot.Database.DatabaseCharacter;
import com.indigo.mudbot.Functions.DatabaseInfo;
import com.indigo.mudbot.Functions.Send;
import com.indigo.mudbot.Main;
import com.indigo.mudbot.Player;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.requests.restaction.MessageAction;

import java.util.ArrayList;
import java.util.List;

public class JoinGame extends Command {
    private EventWaiter waiter;
    public JoinGame(EventWaiter waiter){
        this.name = "joingame";
        this.description = "starts the game"
        this.waiter = waiter;
        this.guildOnly = false;
    }



    @Override
    protected void execute(CommandEvent event) {
        boolean repeat = false;
        for(Player player : Main.getSessionHandler().getPlayers()){
            if(player.getId().equals(event.getAuthor().getId())) repeat = true;
        }
        if(repeat){
            Send.SimpleEmbed(event, "You're already in queue for a game").queue();
            return;
        }
        List<String> exclusions = new ArrayList<>();
        StringBuilder slotInfo = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int result = DatabaseInfo.GetCharacterSlots(i + 1, event.getAuthor().getId());
            if (result == 0) {
                slotInfo.append("Slot ").append(i + 1).append(": No character\n");
                exclusions.add(String.valueOf(i));
            } else {
                slotInfo.append("Slot ").append(i + 1).append(": ").append(DatabaseInfo.GetCharacterName(i + 1, event.getAuthor().getId())).append("\n");
            }
        }
        MessageAction message = Send.SimpleEmbed(event, "Character slots", "Please select a character: \n" + slotInfo.toString());
        message.queue(selectMessage -> {
            selectMessage.addReaction("\u0031\u20E3").queue();
            selectMessage.addReaction("\u0032\u20E3").queue();
            selectMessage.addReaction("\u0033\u20E3").queue();
            selectMessage.addReaction("\u0034\u20E3").queue();
            waiter.waitForEvent(MessageReactionAddEvent.class, e -> e.getUser().getId().equals(event.getAuthor().getId()), f -> {
                int slot = 0;
                if (f.getReaction().getReactionEmote().getName().equals("\u0031\u20E3")) {
                    slot = 1;
                } else if (f.getReaction().getReactionEmote().getName().equals("\u0032\u20E3")) {
                    slot = 2;
                } else if (f.getReaction().getReactionEmote().getName().equals("\u0033\u20E3")) {
                    slot = 3;
                } else if (f.getReaction().getReactionEmote().getName().equals("\u0034\u20E3")) {
                    slot = 4;
                }
                if (slot == 0) {
                    Send.SimpleEmbed(event, "Invalid slot selected").queue();
                    return;
                }
                final int updateSlot = slot;
                /**if (exclusions.contains(String.valueOf(updateSlot))) {
                    System.out.println("error");
                    return;
                }*/
                DatabaseCharacter character = Main.getDatabase().getJsonDBTemplate().findById(String.valueOf(DatabaseInfo.GetCharacterSlots(updateSlot, event.getAuthor().getId())), DatabaseCharacter.class);
                if(character == null){
                    Send.SimpleEmbed(event, "Invalid character slot selected").queue();
                    return;
                }
                Main.getSessionHandler().addToQueue(event, character);
            });
        });
    }
}
