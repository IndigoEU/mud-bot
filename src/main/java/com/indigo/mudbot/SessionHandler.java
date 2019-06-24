package com.indigo.mudbot;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Map;

public class SessionHandler {
    private static Map<String, Session> sessionMap;
    private List<String> players = new ArrayList<String>();

    public static createSession()
    {

    }

    public static int getListSize()
    {
        return players.size();
    }

    public static addToQueue(CommandEvent event)
    {
        players.add(event.getAuthor());
        event.reply("There are currently " + getListSize() + " players in queue.");

        if(getListSize() >= 4)
            createSession();
    }


    public static int getId(){
        return 1;
    }
}
