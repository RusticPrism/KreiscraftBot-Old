package de.rusticprism.kreiscraftbot.commands;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.config.ConfigManager;
import de.rusticprism.kreiscraftbot.config.StempelConfig;
import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import de.rusticprism.kreiscraftbot.utils.OptionBuilder;
import de.rusticprism.kreiscraftbot.utils.OptionList;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

import java.awt.*;

public class UnStempelCommand extends SlashCommand{
    public UnStempelCommand() {
        super("unstempel", "Enferne einen Stempel von einem User",
                new OptionList(new OptionBuilder(OptionType.MENTIONABLE,"user","Der User dessen Stempel entfernt werden soll!",true)), "feinerjunge", "daddysjunge");
    }

    @Override
    public void performCommand(Member member, TextChannel channel, SlashCommandInteraction interaction) {
        if(!member.hasPermission(Permission.ADMINISTRATOR)) {
            interaction.replyEmbeds(EmbedCreator.createembed("NoPermission","You don´t have the Permission to perfrom that Command", Color.RED)).setEphemeral(true).queue();
            return;
        }
        if(interaction.getOption("user") == null) {
            interaction.replyEmbeds(EmbedCreator.createembed("Kein User gefunden!", Color.RED)).queue();
            return;
        }
        String id = interaction.getOption("user").getAsString();
        User user = KreiscraftBot.bot.getJDA().getUserById(id);
        if(user.isSystem() || user.isBot()) {
            interaction.replyEmbeds(EmbedCreator.createembed("Du kannst keinem Bot einen Stempel entfernen!",Color.red)).queue();
            return;
        }
        ConfigManager.getConfig(StempelConfig.class).removeStempel(channel.getGuild(), String.valueOf(user.getIdLong()));
        interaction.replyEmbeds(EmbedCreator.createembed("Ein Stempel wurde dem User \"**" + user.getName() + "**\" entfernt!", Color.GREEN)).queue();
    }
}
