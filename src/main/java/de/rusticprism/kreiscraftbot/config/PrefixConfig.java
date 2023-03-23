package de.rusticprism.kreiscraftbot.config;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.config.util.FileConfiguration;
import net.dv8tion.jda.api.entities.Guild;

public class PrefixConfig extends Config {
    public PrefixConfig() {
        super("PrefixConfig");
    }

    public String getPrefix(Guild guild) {
        if (getConfig().getString(guild.getId()) == null) {
            return "!";
        }
        return getConfig().getString(guild.getId());
    }

    public void setPrefix(Guild guild, String prefix) {
        getConfig().set(guild.getId(), prefix);
        saveConfig();
    }

    @Override
    public void createDefault() {
        for (Guild guild : KreiscraftBot.bot.getJDA().getGuilds()) {
            setPrefix(guild, "!");
        }
    }
}
