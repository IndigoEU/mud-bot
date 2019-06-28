package com.indigo.mudbot.Commands;

import com.indigo.mudbot.Database.DatabaseCharacter;
import com.indigo.mudbot.Database.DatabaseCharacterInventory;
import com.indigo.mudbot.Database.DatabaseUser;
import com.indigo.mudbot.Functions.DatabaseInfo;
import com.indigo.mudbot.Functions.Send;
import com.indigo.mudbot.Main;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import io.jsondb.query.Update;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.requests.restaction.MessageAction;

public class CreateCharacter extends Command {
    EventWaiter waiter;
    public CreateCharacter(EventWaiter waiter){
        this.waiter = waiter;
        this.name = "createcharacter";
        this.help = "Let's you create a character.";
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
                slotInfo.append("Slot ").append(i + 1).append(": ").append(DatabaseInfo.GetCharacterName(i + 1, event.getAuthor().getId())).append("\n");
            }
        }
        MessageAction message = Send.SimpleEmbed(event, "Character slots", "Please select a slot in which to create/overwrite a character: \n"+slotInfo.toString());
        message.queue(selectMessage -> {
            selectMessage.addReaction("\u0031\u20E3").queue();
            selectMessage.addReaction("\u0032\u20E3").queue();
            selectMessage.addReaction("\u0033\u20E3").queue();
            selectMessage.addReaction("\u0034\u20E3").queue();
            System.out.println("yesyes");
            waiter.waitForEvent(MessageReactionAddEvent.class, e -> e.getUser().getId().equals(event.getAuthor().getId()), f -> {
                int slot = 0;
                System.out.println("yesyesyes");
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
                int id = Main.getDatabase().getJsonDBTemplate().getCollection(DatabaseCharacter.class).size()+1;
                Send.SimpleEmbed(event, "What shall be the new character's name?").queue();
                waiter.waitForEvent(MessageReceivedEvent.class, e -> e.getAuthor().getId().equals(event.getAuthor().getId()), res -> {
                    System.out.println("got past bool");
                    DatabaseCharacter newChar = new DatabaseCharacter();
                    newChar.setCharacterId(String.valueOf(id));
                    newChar.setMaxHp(100);
                    newChar.setCurrentHp(100);
                    newChar.setEquipWeapon(3);
                    newChar.setName(res.getMessage().getContentRaw());
                    newChar.setInventoryId(String.valueOf(Main.getDatabase().getJsonDBTemplate().getCollection(DatabaseCharacterInventory.class).size()+1));
                    DatabaseCharacterInventory newInventory = new DatabaseCharacterInventory();
                    newInventory.setInventoryId(String.valueOf(Main.getDatabase().getJsonDBTemplate().getCollection(DatabaseCharacterInventory.class).size()+1));
                    newInventory.setInventory(new int[][]{{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}});
                    Main.getDatabase().getJsonDBTemplate().insert(newChar);
                    Main.getDatabase().getJsonDBTemplate().insert(newInventory);
                    Update update = new Update();
                    update.set("slot"+updateSlot, String.valueOf(id));
                    String jxQuery = String.format("/.[userId='%s']", event.getAuthor().getId());
                    Main.getDatabase().getJsonDBTemplate().findAndModify(jxQuery, update, DatabaseUser.class);
                    Send.SimpleEmbed(event, "Character created").queue();
                });
            });
        });
    }
}
