package de.rusticprism.kreiscraftbot.listener;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.settings.Prefix;
import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class CommandListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String message = event.getMessage().getContentDisplay();

        if(event.isFromType(ChannelType.TEXT) && !event.getMessage().isEdited()) {
            TextChannel channel = event.getChannel().asTextChannel();
            if(message.startsWith(Prefix.getPrefix(channel.getGuild()))) {
                String[] args = message.substring(1).split(" ");
                if (KreiscraftBot.configs.get(channel.getGuild().getIdLong()).get("Botchannel") == null || KreiscraftBot.configs.get(channel.getGuild().getIdLong()).get("Botchannel").equals(channel.getId())) {
                    if (args.length > 0) {
                        if (!KreiscraftBot.cmdMan.perform(args[0], event.getMember(), channel, event.getMessage())) {
                            channel.sendMessageEmbeds(EmbedCreator.createembed("Unbekanntes Commando", Color.decode("#f22613"))).queue();
                        }
                    }
                }else event.getMessage().delete().queue();
            }
        }

    }
}