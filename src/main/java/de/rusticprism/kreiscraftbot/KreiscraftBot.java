package de.rusticprism.kreiscraftbot;

import de.rusticprism.kreiscraftbot.listener.CommandListener;
import de.rusticprism.kreiscraftbot.listener.ModalListener;
import de.rusticprism.kreiscraftbot.listener.ReadyListener;
import de.rusticprism.kreiscraftbot.listener.SlashCommandListener;
import de.rusticprism.kreiscraftbot.music.listener.ReactionListener;
import de.rusticprism.kreiscraftbot.music.utils.SpotifyMusicPlayer;
import de.rusticprism.kreiscraftbot.token.Tokens;
import de.rusticprism.kreiscraftbot.utils.OtherUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class KreiscraftBot {
    public static Logger logger;
    public static CommandManager cmdMan;
    public static Bot bot;

    public static void main(String[] args) {
            try {
                startBot(args[0].toUpperCase());
            }catch (Exception e) {
                OtherUtil.logError(e);
            }
    }

    public static void startBot(String tokenname) {
        // create prompt to handle startup
        KreiscraftBot.logger = LoggerFactory.getLogger(KreiscraftBot.class);
        if (Tokens.valueOf(tokenname) == null) {
            System.out.println("Could´nt find token (" + tokenname + ")");
            System.out.println("Shutting down in 20 Seconds!");
            Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                System.out.println("Shutting down...");
                System.exit(1);
            }, 20, TimeUnit.SECONDS);
            return;
        }
        // set up the listener
        bot = new Bot();
        try {
            JDA jda = JDABuilder.create(Tokens.valueOf(tokenname).getToken(), GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MEMBERS)
                    .enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                    .disableCache(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS, CacheFlag.EMOJI, CacheFlag.ONLINE_STATUS, CacheFlag.STICKER, CacheFlag.SCHEDULED_EVENTS)
                    .setActivity(Activity.playing("auf kreiscraft.net"))
                    .setStatus(OnlineStatus.ONLINE)
                    .addEventListeners(new CommandListener(), new ReadyListener(), new SlashCommandListener(), new ModalListener(), new ReactionListener())
                    .setBulkDeleteSplittingEnabled(true)
                    .build();
            bot.setJDA(jda);
        } catch (IllegalArgumentException ex) {
            logger.warn("Error while Logging in!");
            System.exit(1);
        }
    }
}
