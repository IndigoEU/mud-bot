package com.indigo.mudbot.Commands;

import com.indigo.mudbot.Functions.DatabaseInfo;
import com.indigo.mudbot.Functions.Send;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.requests.restaction.MessageAction;

public class CreateCharacter extends Command {
    EventWaiter waiter;
    public CreateCharacter(EventWaiter waiter){
        this.waiter = waiter;
        this.name = "createcharacter";
        this.category = new Category("MUD");
        this.aliases = new String[]{"create", "createchar", "newchar"};
    }
    @Override
    protected void execute(CommandEvent event) {
        StringBuilder slotInfo = new StringBuilder();
        for(int i = 0; i < 4; i++) {
            int result = DatabaseInfo.GetCharacterSlots(i+1, event.getAuthor().getId());
            if(result == 0){
                slotInfo.append("Slot ").append(i+1).append(": No character\n");
            }
            else{
                slotInfo.append("Slot ").append(i+1).append(": ").append(DatabaseInfo.GetCharacterName(i + 1, event.getAuthor().getId()+"\n"));
            }
        }
        MessageAction message = Send.SimpleEmbed(event, "Character slots", "Please select a slot in which to create/overwrite a character: "+slotInfo.toString());
        message.queue(selectMessage -> {
            selectMessage.addReaction(event.getJDA().getEmotesByName("one", true).get(0)).queue();
            selectMessage.addReaction(event.getJDA().getEmotesByName("two", true).get(0)).queue();
            selectMessage.addReaction(event.getJDA().getEmotesByName("three", true).get(0)).queue();
            selectMessage.addReaction(event.getJDA().getEmotesByName("four", true).get(0)).queue();

            waiter.waitForEvent(MessageReactionAddEvent.class, e -> e.getUser().getId().equals(event.getAuthor().getId()), e -> {
            });
        });
    }
}
