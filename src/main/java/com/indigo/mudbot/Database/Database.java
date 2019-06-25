package com.indigo.mudbot.Database;

import com.indigo.mudbot.Main;
import io.jsondb.JsonDBTemplate;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

import java.util.List;

public class Database implements EventListener {
    private static final String location = "C:/DatabaseTest";
    private static final String basicScanPackage = "com.indigo.mudbot.Database";
    private JsonDBTemplate jsonDBTemplate;
    public Database(){
        this.jsonDBTemplate = new JsonDBTemplate(location, basicScanPackage);
        if(!jsonDBTemplate.collectionExists(DatabaseCharacter.class)) jsonDBTemplate.createCollection(DatabaseCharacter.class);
        if(!jsonDBTemplate.collectionExists(DatabaseCharacterInventory.class)) jsonDBTemplate.createCollection(DatabaseCharacterInventory.class);
        if(!jsonDBTemplate.collectionExists(DatabaseUser.class)) jsonDBTemplate.createCollection(DatabaseUser.class);
    }

    public JsonDBTemplate getJsonDBTemplate() {
        return jsonDBTemplate;
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof MessageReceivedEvent) {
            MessageReceivedEvent messageEvent = (MessageReceivedEvent) event;
            String jxQuery = String.format("/.[userId='%s']", messageEvent.getAuthor().getId());
            System.out.println(jxQuery);
            List<DatabaseUser> list = Main.getDatabase().jsonDBTemplate.find(jxQuery, DatabaseUser.class);
            if (list.size() == 0) {
                DatabaseUser newUser = new DatabaseUser();
                newUser.setOwO(false);
                newUser.setSlot1(0);
                newUser.setSlot2(0);
                newUser.setSlot3(0);
                newUser.setSlot4(0);
                newUser.setUserId(messageEvent.getAuthor().getId());
                Main.getDatabase().jsonDBTemplate.insert(newUser);
            }
        }
    }
}
