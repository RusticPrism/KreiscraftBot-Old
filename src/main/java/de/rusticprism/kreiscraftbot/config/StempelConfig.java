package de.rusticprism.kreiscraftbot.config;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class StempelConfig extends Config {
    public StempelConfig() {
        super("StempelCommand");
    }

    @Override
    public void createDefault() {
        for (Guild guild : KreiscraftBot.bot.getJDA().getGuilds()) {
            for (Member member : guild.getMembers()) {
                getConfig().set(guild.getId() + "." + member.getId(), 0);
            }
        }
    }

    public void addStempel(Guild guild, String id) {
        int stempel = getStempel(guild, id);
        setStempel(guild, id, stempel + 1);
    }

    public int getStempel(Guild guild, String id) {
        if (getConfig().get(id) == null) {
            setStempel(guild, id, 0);
            return 0;
        }
        return getConfig().getInt(id);
    }

    public void setStempel(Guild guild, String id, int amount) {
        getConfig().set(guild.getId() + "." + id, String.valueOf(amount));
        saveConfig();
    }

    public void removeStempel(Guild guild, String id) {
        int stempel = getStempel(guild, id);
        setStempel(guild, id, stempel - 1);
    }
}
