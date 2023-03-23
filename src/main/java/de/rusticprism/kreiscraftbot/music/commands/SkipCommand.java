package de.rusticprism.kreiscraftbot.music.commands;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.commands.Command;
import de.rusticprism.kreiscraftbot.config.ConfigManager;
import de.rusticprism.kreiscraftbot.config.PrefixConfig;
import de.rusticprism.kreiscraftbot.music.audio.AudioHandler;
import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.awt.*;

public class SkipCommand extends Command {
    public SkipCommand() {
        super("skip", "Skip the current Song", "");
    }

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        if(message.getContentDisplay().split(" ").length == 1) {
            AudioHandler handler = KreiscraftBot.bot.getPlayerManager().setUpHandler(channel.getGuild());
            if (handler.getPlayer().getPlayingTrack() == null) {
                channel.sendMessageEmbeds(EmbedCreator.createembed("How am i supposed to skip the Song if non is playing!", Color.red)).queue();
                return;
            }
            if (handler.getQueue().size() == 0) {
                channel.sendMessageEmbeds(EmbedCreator.createembed("The queue is emyty", Color.red)).queue();
                return;
            }
            handler.skipTrack();
            channel.sendMessageEmbeds(EmbedCreator.createembed("Successfully skipped the current Song!", Color.green)).queue();
        } else
            channel.sendMessageEmbeds(EmbedCreator.createembed("Please only use " + ConfigManager.getConfig(PrefixConfig.class).getPrefix(channel.getGuild()) + "skip!", Color.red)).queue();
    }
}
