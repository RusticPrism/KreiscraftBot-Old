package de.rusticprism.kreiscraftbot.listener;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.config.ConfigManager;
import de.rusticprism.kreiscraftbot.config.MusicConfig;
import de.rusticprism.kreiscraftbot.config.PrefixConfig;
import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class CommandListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        String message = event.getMessage().getContentDisplay();

        if(event.isFromType(ChannelType.TEXT) && !event.getMessage().isEdited()) {
            TextChannel channel = event.getChannel().asTextChannel();
            if (message.startsWith(ConfigManager.getConfig(PrefixConfig.class).getPrefix(channel.getGuild()))) {
                String[] args = message.substring(1).split(" ");
                System.out.println(ConfigManager.getConfig(MusicConfig.class).getBotchannel(channel.getGuild()));
                System.out.println(channel.getIdLong());
                if (ConfigManager.getConfig(MusicConfig.class).getBotchannel(channel.getGuild()) == channel.getIdLong()) {
                    if (args.length > 0) {
                        if (!KreiscraftBot.cmdMan.perform(args[0], event.getMember(), channel, event.getMessage())) {
                            channel.sendMessageEmbeds(EmbedCreator.createembed("Unbekanntes Commando", Color.decode("#f22613"))).queue();
                        }
                    }
                } else {
                    event.getMessage().replyEmbeds(EmbedCreator.createembed("This is not the Bot-channel", Color.red))
                            .complete().delete().queueAfter(15, TimeUnit.SECONDS);
                    event.getMessage().delete().queue();
                }
            }
        }

    }
}
