package com.indigo.mudbot;

import com.indigo.mudbot.Classes.ClassTemplate;
import com.indigo.mudbot.Classes.Fighter;
import com.indigo.mudbot.Commands.CreateCharacter;
import com.indigo.mudbot.Commands.JoinGame;
import com.indigo.mudbot.Commands.Ping;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

public class Values {
    public static Command[] Commands = new Command[]{
            new Ping(),
            new CreateCharacter(Main.waiter),
            new JoinGame(Main.waiter)
    };
    public static ClassTemplate[] playerClasses = new ClassTemplate[]{
            new Fighter()
    };
}
