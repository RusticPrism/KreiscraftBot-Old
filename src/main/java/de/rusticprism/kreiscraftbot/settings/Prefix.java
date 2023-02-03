package de.rusticprism.kreiscraftbot.settings;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import net.dv8tion.jda.api.entities.Guild;

public class Prefix {
    public static String getPrefix(Guild guild) {
        return KreiscraftBot.bot.getConfig(guild).getPrefix();
    }
    public static void setPrefix(Guild guild,String prefix) {
        KreiscraftBot.bot.getConfig(guild).setPrefix(prefix);
    }
}
