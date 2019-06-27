package com.indigo.mudbot.Commands;

import com.indigo.mudbot.Database.DatabaseCharacter;
import com.indigo.mudbot.Database.DatabaseCharacterInventory;
import com.indigo.mudbot.Functions.DatabaseInfo;
import com.indigo.mudbot.Functions.Send;
import com.indigo.mudbot.Items.Item;
import com.indigo.mudbot.Main;
import com.indigo.mudbot.Values;
import com.indigo.mudbot.Weapons.Weapon;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import io.jsondb.query.Update;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.requests.restaction.MessageAction;

public class Equip extends Command {
    private EventWaiter waiter;
    public Equip(EventWaiter waiter){
        this.name = "equip";
        this.waiter = waiter;
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
                StringBuilder sb = new StringBuilder();
                int characterInfo = DatabaseInfo.GetCharacterSlots(updateSlot, event.getAuthor().getId());
                DatabaseCharacter character = Main.getDatabase().getJsonDBTemplate().findById(String.valueOf(characterInfo), DatabaseCharacter.class);
                if(character == null){
                    Send.SimpleEmbed(event, "Invalid character slot selected").queue();
                    return;
                }
                DatabaseCharacterInventory inventory = Main.getDatabase().getJsonDBTemplate().findById(character.getInventoryId(), DatabaseCharacterInventory.class);
                int[][] inventorySlots = inventory.getInventory();
                for(int[] inventoryRow : inventorySlots){
                    for(int inventorySlot : inventoryRow){
                        if(inventorySlot == 0){
                            sb.append(Values.NO_ITEM);
                        }
                        else {
                            sb.append(Values.Items.get(inventorySlot).getAssetName());
                        }
                    }
                    sb.append("\n");
                }
                Send.SimpleEmbed(event,"Please select an item to equip (example - 2:4 where 2 is the second row and 4 is fourth column)", sb.toString()).queue();
                waiter.waitForEvent(MessageReceivedEvent.class, e -> e.getAuthor().getId().equals(event.getAuthor().getId()), e -> {
                    String[] array = e.getMessage().getContentRaw().split(":");
                    int[] numArray = new int[2];
                    int selectItem;
                    try {
                        numArray[0] = Integer.parseInt(array[0]);
                        numArray[1] = Integer.parseInt(array[1]);
                        selectItem = inventorySlots[numArray[0] - 1][numArray[1] - 1];
                    }
                    catch(Exception ex){
                        Send.SimpleEmbed(event, "Invalid item selected").queue();
                        return;
                    }
                    if(selectItem == 0 || !Values.Weapons.containsKey(selectItem)) {
                        Send.SimpleEmbed(event, "Invalid item selected").queue();
                        return;
                    }
                    System.out.println("Passed with "+selectItem);
                    character.setEquipWeapon(selectItem);
                    Update update = new Update();
                    update.set("equipWeapon", selectItem);
                    Main.getDatabase().getJsonDBTemplate().findAndModify("/.[characterId='" + character.getCharacterId() +"']", update, DatabaseCharacter.class);
                });
            });
        });
    }
}
