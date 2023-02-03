package de.rusticprism.kreiscraftbot.listener;

import de.rusticprism.kreiscraftbot.CommandManager;
import de.rusticprism.kreiscraftbot.KreiscraftBot;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        KreiscraftBot.bot.loadConfig();
        System.out.println("Im on " + event.getGuildTotalCount() + " Server(s), " + event.getGuildUnavailableCount() + " is/are Unavailable!");
        KreiscraftBot.cmdMan = new CommandManager();
    }
}
