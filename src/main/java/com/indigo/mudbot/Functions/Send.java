package com.indigo.mudbot.Functions;

import com.indigo.mudbot.Database.DatabaseUser;
import com.indigo.mudbot.Main;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.requests.restaction.MessageAction;

public class Send {
    public static MessageAction SimpleEmbed(CommandEvent event, String title){
        boolean owo = Main.getDatabase().getJsonDBTemplate().findById(event.getAuthor().getId(), DatabaseUser.class).getOwO();
        if(owo){
            title = Alternator.owoify(title);
        }
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle(title)
                .setColor(0x00FF00);
        return event.getChannel().sendMessage(embedBuilder.build());
    }

    public static MessageAction SimpleEmbed(CommandEvent event, String title, String description){

        boolean owo = Main.getDatabase().getJsonDBTemplate().findById(event.getAuthor().getId(), DatabaseUser.class).getOwO();
        if(owo){
            title = Alternator.owoify(title);
            description = Alternator.owoify(description);
        }
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(0x00FF00);
        return event.getChannel().sendMessage(embedBuilder.build());
    }
}