package de.rusticprism.kreiscraftbot.config;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.config.util.FileConfiguration;
import de.rusticprism.kreiscraftbot.settings.RepeatMode;
import net.dv8tion.jda.api.entities.Guild;

public class MusicConfig extends Config {

    private boolean stayinchannel = false;
    private RepeatMode repeatMode = RepeatMode.OFF;
    private int volume = 150;
    private long botchannel = 0;

    public long getBotchannel() {
        return botchannel;
    }

    public void setBotchannel(long botchannel) {
        this.botchannel = botchannel;
    }

    public boolean isStayinchannel() {
        return stayinchannel;
    }

    public void setStayinchannel(boolean stayinchannel) {
        this.stayinchannel = stayinchannel;
    }

    public RepeatMode getRepeatMode() {
        return repeatMode;
    }

    public void setRepeatMode(RepeatMode repeatMode) {
        this.repeatMode = repeatMode;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
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
            config.set(guild.getId() + ".repeatmode", repeatMode);
            config.set(guild.getId() + ".stayinchannel", stayinchannel);
            config.set(guild.getId() + ".volume", volume);
        }
    }
}
