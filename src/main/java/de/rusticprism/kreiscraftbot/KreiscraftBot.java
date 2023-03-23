package de.rusticprism.kreiscraftbot;

import de.rusticprism.kreiscraftbot.listener.CommandListener;
import de.rusticprism.kreiscraftbot.listener.ModalListener;
import de.rusticprism.kreiscraftbot.listener.ReadyListener;
import de.rusticprism.kreiscraftbot.listener.SlashCommandListener;
import de.rusticprism.kreiscraftbot.music.listener.ReactionListener;
import de.rusticprism.kreiscraftbot.utils.BotConfig;
import de.rusticprism.kreiscraftbot.utils.OtherUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class KreiscraftBot {
    public static HashMap<Long, BotConfig> configs;
    public static Logger logger;
    public static CommandManager cmdMan;
    public static Bot bot;
    private static HashMap<String, String> tokens;

    public static void main(String[] args) {
            tokens = new HashMap<>();
            tokens.put("kreiscraftbot","OTE2MzAwNTM2NjgzMzA3MDM4.YaoJXw.Mr9c3cRy1ibprvMNQbdYveleMgY");
            tokens.put("devbot", "OTE3MTM1NjY4ODk4MDU0MjQ0.Ya0TJw.SdQrw4dujNG2FfTZ0TomwMLTR-U");
            try {
                startBot(args[0]);
            }catch (Exception e) {
                OtherUtil.logError(e);
            }
    }

    public static void startBot(String tokenname) {
        // create prompt to handle startup
        configs = new HashMap<>();
        KreiscraftBot.logger = LoggerFactory.getLogger(KreiscraftBot.class);
        if(!tokens.containsKey(tokenname)) {
            System.out.println("Could´nt find token (" + tokenname + ")");
            System.out.println("Shutting down in 20 Seconds!");
            Executors.newSingleThreadScheduledExecutor().schedule(()-> {
                System.out.println("Shutting down...");
                System.exit(1);
                },20,TimeUnit.SECONDS);
            return;
        }


        // set up the listener
        bot = new Bot();

        try {
            JDA jda = JDABuilder.create(tokens.get(tokenname),GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MEMBERS)
                    .enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                    .disableCache(CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS, CacheFlag.EMOJI, CacheFlag.ONLINE_STATUS,CacheFlag.STICKER,CacheFlag.SCHEDULED_EVENTS)
                    .setActivity(Activity.playing("auf kreiscraft.de"))
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
