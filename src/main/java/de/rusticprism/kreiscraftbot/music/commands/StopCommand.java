package de.rusticprism.kreiscraftbot.music.commands;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.commands.Command;
import de.rusticprism.kreiscraftbot.music.audio.AudioHandler;
import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

import java.awt.*;

public class StopCommand extends Command {
    public StopCommand() {
        super("stop", "Stop the current playing song!", "");
    }

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        if(!channel.getId().equalsIgnoreCase("784035327462604882") && channel.getGuild().getId().equalsIgnoreCase("781146789784322079")) {
            message.delete().queue();
            return;
        }
                if(channel.getGuild().getAudioManager().isConnected()) {
                    //RegenBot.INSTANCE.getGuildAudioPlayer(channel.getGuild()).player.stopTrack();
                    AudioHandler handler = KreiscraftBot.bot.getPlayerManager().setUpHandler(channel.getGuild());
                    handler.stopAndClear();
                    channel.getGuild().getAudioManager().closeAudioConnection();
                    message.addReaction(Emoji.fromUnicode("U+2705")).queue();
                }else channel.sendMessageEmbeds(EmbedCreator.createembed("The Bot isn't in any Voice-channel", Color.red)).queue();
    }
}
