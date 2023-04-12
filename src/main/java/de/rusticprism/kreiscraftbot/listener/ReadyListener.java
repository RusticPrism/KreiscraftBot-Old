package de.rusticprism.kreiscraftbot.listener;

import de.rusticprism.kreiscraftbot.CommandManager;
import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.config.ConfigManager;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        new ConfigManager();
        System.out.println("Im on " + event.getGuildTotalCount() + " Server(s), " + event.getGuildUnavailableCount() + " is/are Unavailable!");
        KreiscraftBot.cmdMan = new CommandManager();
        KreiscraftBot.bot.getThreadpool().scheduleAtFixedRate(() -> {
            int random = new Random().nextInt(0, KreiscraftBot.bot.activities.size());
            KreiscraftBot.bot.getJDA().getPresence().setActivity(KreiscraftBot.bot.activities.get(random));
        }, 10, 10, TimeUnit.SECONDS);
    }
}
