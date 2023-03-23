package de.rusticprism.kreiscraftbot.commands;

import de.rusticprism.kreiscraftbot.config.ConfigManager;
import de.rusticprism.kreiscraftbot.config.PrefixConfig;
import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import de.rusticprism.kreiscraftbot.utils.OptionBuilder;
import de.rusticprism.kreiscraftbot.utils.OptionList;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

import java.awt.*;

public class PrefixCommand extends SlashCommand{
    public PrefixCommand() {
        super("prefix", "Set the Prefix of the Kreiscraft Bot", new OptionList(new OptionBuilder(OptionType.STRING,"prefix","Set the Prefix", true)));
    }
    @Override
    public void performCommand(Member m, TextChannel channel, SlashCommandInteraction interaction) {
        if (!m.hasPermission(Permission.ADMINISTRATOR)) {
            interaction.replyEmbeds(EmbedCreator.createembed("NoPermission", "You don´t have the Permission to perfrom that Command", Color.RED)).setEphemeral(true).queue();
            return;
        }
        ConfigManager.getConfig(PrefixConfig.class).setPrefix(channel.getGuild(), interaction.getOption("prefix").getAsString());
        interaction.replyEmbeds(EmbedCreator.createembed("Successfully set the Prefix to " + ConfigManager.getConfig(PrefixConfig.class).getPrefix(channel.getGuild()), Color.green)).queue();
    }

}
