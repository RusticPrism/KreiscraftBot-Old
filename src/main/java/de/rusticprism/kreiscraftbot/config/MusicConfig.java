package de.rusticprism.kreiscraftbot.config;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.config.util.FileConfiguration;
import de.rusticprism.kreiscraftbot.settings.RepeatMode;
import net.dv8tion.jda.api.entities.Guild;

public class MusicConfig extends Config {

    /*
    guildid:
            repeatmode: OFF
            stayinchannel: false
            volume: 150
     */
    public MusicConfig() {
        super("MusicConfig");
    }

    @Override
    public void createDefault() {
        FileConfiguration config = getConfig();
        for (Guild guild : KreiscraftBot.bot.getJDA().getGuilds()) {
            config.set(guild.getId() + ".repeatmode", RepeatMode.OFF);
            config.set(guild.getId() + ".stayinchannel", false);
            config.set(guild.getId() + ".volume", 150);
        }
    }
}
