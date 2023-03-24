package de.rusticprism.kreiscraftbot.config;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.config.util.FileConfiguration;
import de.rusticprism.kreiscraftbot.settings.RepeatMode;
import net.dv8tion.jda.api.entities.Guild;

public class MusicConfig extends Config {

    public long getBotchannel(Guild guild) {
        return getConfig().getLong(guild.getId() + ".botchannel");
    }

    public void setBotchannel(Guild guild, long botchannel) {
        getConfig().set(guild.getId() + ".botchannel", botchannel);
        saveConfig();
    }

    public boolean isStayinchannel(Guild guild) {
        return getConfig().getBoolean(guild.getId() + ".stayinchannel");
    }

    public void setStayinchannel(Guild guild, boolean stayinchannel) {
        getConfig().set(guild.getId() + ".stayinchannel", stayinchannel);
    }

    public RepeatMode getRepeatMode(Guild guild) {
        return RepeatMode.valueOf(getConfig().getString(guild.getId() + ".repeatmode"));
    }

    public void setRepeatMode(Guild guild, RepeatMode repeatMode) {
        getConfig().set(guild.getId() + ".repeatmode", repeatMode.getName());
    }

    public int getVolume(Guild guild) {
        return getConfig().getInt(guild.getId() + ".volume");
    }

    public void setVolume(Guild guild, int volume) {
        getConfig().set(guild.getId() + ".volume", volume);
    }

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
            config.set(guild.getId() + ".repeatmode", RepeatMode.OFF.getName());
            config.set(guild.getId() + ".stayinchannel", false);
            config.set(guild.getId() + ".volume", 150);
            config.set(guild.getId() + ".botchannel", 0);
        }
    }
}
