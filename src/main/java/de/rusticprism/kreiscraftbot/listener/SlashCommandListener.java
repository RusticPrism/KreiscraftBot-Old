package de.rusticprism.kreiscraftbot.listener;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
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
            System.out.println(KreiscraftBot.configs.get(textChannel.getGuild().getIdLong()).get("Botchannel"));
            if (KreiscraftBot.configs.get(textChannel.getGuild().getIdLong()).get("Botchannel") == null || KreiscraftBot.configs.get(textChannel.getGuild().getIdLong()).get("Botchannel").equals(textChannel.getId())) {
                if (!KreiscraftBot.cmdMan.performSlash(event.getName(), event.getMember(), textChannel, event.getInteraction())) {
                    event.replyEmbeds(EmbedCreator.createembed("Unbekanntes Commando", Color.decode("#f22613"))).queue();
                }
            }else event.getInteraction().replyEmbeds(EmbedCreator.createembed("This is not the BotChannel!",Color.red)).setEphemeral(true).queue();
        }
    }
}
