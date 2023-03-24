package de.rusticprism.kreiscraftbot.listener;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.config.ConfigManager;
import de.rusticprism.kreiscraftbot.config.MusicConfig;
import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SlashCommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.isFromGuild()) {
            TextChannel textChannel = event.getChannel().asTextChannel();
            if (ConfigManager.getConfig(MusicConfig.class).getBotchannel(event.getGuild()) == event.getChannel().getIdLong()) {
                if (!KreiscraftBot.cmdMan.performSlash(event.getName(), event.getMember(), textChannel, event.getInteraction())) {
                    event.replyEmbeds(EmbedCreator.createembed("Unbekanntes Commando", Color.decode("#f22613"))).queue();
                }
            } else
                event.getInteraction().replyEmbeds(EmbedCreator.createembed("This is not the BotChannel!", Color.red)).setEphemeral(true).queue();
        }
    }
}
