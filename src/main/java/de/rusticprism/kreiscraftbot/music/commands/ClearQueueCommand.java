package de.rusticprism.kreiscraftbot.music.commands;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.commands.Command;
import de.rusticprism.kreiscraftbot.music.audio.AudioHandler;
import de.rusticprism.kreiscraftbot.settings.Prefix;
import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.awt.*;

public class ClearQueueCommand extends Command {
    public ClearQueueCommand() {
        super("clear-queue", "Clear the current Queue", "cq");
    }

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        if(message.getContentDisplay().split(" ").length == 1) {
            AudioHandler handler = KreiscraftBot.bot.getPlayerManager().setUpHandler(channel.getGuild());
            handler.stopAndClear();
            channel.sendMessageEmbeds(EmbedCreator.createembed("Successfully cleared Queue!",Color.green)).queue();
        }else channel.sendMessageEmbeds(EmbedCreator.createembed("Please only use" + Prefix.getPrefix(channel.getGuild()) + "clear-queue", Color.red)).queue();
    }
}
