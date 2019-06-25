package com.indigo.mudbot;

import com.indigo.mudbot.Database.Database;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    private static Database database;
    private static SessionHandler sessionHandler;
    public static EventWaiter waiter = new EventWaiter();

    public static void main(String[] args) throws IOException, LoginException {
        sessionHandler = new SessionHandler();
        database = new Database();
        List<String> list = Files.readAllLines(Paths.get("config.txt"));
        String token = list.get(0);
        CommandClientBuilder client = new CommandClientBuilder();
        client.setOwnerId("256471435856314369");
        client.setPrefix("/");
        client.addCommands(
                Values.Commands
        );
        new JDABuilder(AccountType.BOT)
                .setToken(token)
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setGame(Game.playing("loading..."))
                .addEventListener(database)
                .addEventListener(waiter)
                .addEventListener(client.build())
                .build();
    }

    public static Database getDatabase() {
        return database;
    }
    public static SessionHandler getSessionHandler() { return sessionHandler; }

}
