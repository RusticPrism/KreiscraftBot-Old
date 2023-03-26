package de.rusticprism.kreiscraftbot.commands;

import de.rusticprism.kreiscraftbot.config.ConfigManager;
import de.rusticprism.kreiscraftbot.config.MusicConfig;
import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import de.rusticprism.kreiscraftbot.utils.OptionBuilder;
import de.rusticprism.kreiscraftbot.utils.OptionList;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

import java.awt.*;

public class BotChannelCommand extends SlashCommand{
    public BotChannelCommand() {
        super("botchannel", "Set the Channel where the Bot Responds", new OptionList(new OptionBuilder(OptionType.STRING,"channel")));
    }

    @Override
    public void performCommand(Member member, TextChannel channel, SlashCommandInteraction interaction) {
        if(!member.hasPermission(Permission.ADMINISTRATOR)) {
            interaction.replyEmbeds(EmbedCreator.noPermission()).setEphemeral(true).queue();
            return;
        }
        if(interaction.getOption("channel") == null) {
            interaction.replyEmbeds(EmbedCreator.createembed("The Botchannel is " + channel.getGuild().getTextChannelById(ConfigManager.getConfig(MusicConfig.class).getBotchannel(channel.getGuild())).getAsMention(), Color.green)).setEphemeral(true).queue();
            return;
        }
        String option = interaction.getOption("channel").getAsString();
        if(channel.getGuild().getTextChannelsByName(option,true).size() == 1) {
           TextChannel channel1 = channel.getGuild().getTextChannelsByName(option,true).get(0);
            ConfigManager.getConfig(MusicConfig.class).setBotchannel(channel.getGuild(), channel1.getIdLong());
            interaction.replyEmbeds(EmbedCreator.createembed("Set the Botchannel to " + channel1.getAsMention(), Color.green)).setEphemeral(true).queue();
        }else if(channel.getGuild().getTextChannelsByName(option,true).size() >1) {
            TextChannel channel1 = channel.getGuild().getTextChannelsByName(option,true).get(0);
            ConfigManager.getConfig(MusicConfig.class).setBotchannel(channel.getGuild(), channel1.getIdLong());
            interaction.replyEmbeds(EmbedCreator.createembed("There is more than one channel with this name. Taking the first one(" + channel1.getAsMention() + ")!", Color.green)).setEphemeral(true).queue();
        }else {
            interaction.replyEmbeds(EmbedCreator.createembed("There is no channel with this name!",Color.red)).setEphemeral(true).queue();
        }

    }
}
