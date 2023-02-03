package de.rusticprism.kreiscraftbot;

import de.rusticprism.kreiscraftbot.music.audio.AudioHandler;
import de.rusticprism.kreiscraftbot.music.audio.PlayerManager;
import de.rusticprism.kreiscraftbot.utils.BotConfig;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

public class Bot {
    private final ScheduledExecutorService threadpool;
    private final PlayerManager manager;

    private boolean shuttingDown = false;
    private JDA jda;

    public Bot() {
        this.threadpool = Executors.newSingleThreadScheduledExecutor();
        this.manager = new PlayerManager(this);
        this.manager.init();
        KreiscraftBot.logger = Logger.getLogger("KreiscraftBot");
    }

    public BotConfig getConfig(Guild guild) {
        return KreiscraftBot.configs.get(guild.getIdLong());
    }

    public ScheduledExecutorService getThreadpool() {
        return threadpool;
    }

    public PlayerManager getPlayerManager() {
        return manager;
    }

    public JDA getJDA() {
        return jda;
    }

    public void closeAudioConnection(long guildId) {
        Guild guild = jda.getGuildById(guildId);
        if (guild != null)
            threadpool.submit(() -> guild.getAudioManager().closeAudioConnection());
    }

    public void shutdown() {
        KreiscraftBot.configs.forEach((guildid, botConfig) -> botConfig.save());
        if (shuttingDown)
            return;
        shuttingDown = true;
        threadpool.shutdownNow();
        if (jda.getStatus() != JDA.Status.SHUTTING_DOWN) {
            jda.getGuilds().forEach(g ->
            {
                g.getAudioManager().closeAudioConnection();
                AudioHandler ah = (AudioHandler) g.getAudioManager().getSendingHandler();
                if (ah != null) {
                    ah.stopAndClear();
                    ah.getPlayer().destroy();
                }
            });
            jda.shutdown();
        }
        System.exit(0);
    }
    public void setJDA(JDA jda) {
        this.jda = jda;
    }


    public void loadConfig() {
        // load config
        for (Guild guild : getJDA().getGuilds()) {
            BotConfig config = new BotConfig(guild);
            if(config.get("Prefix") == null) {
                config.writeDefaultFile();
            }
            KreiscraftBot.configs.put(guild.getIdLong(), config);
        }
    }
}