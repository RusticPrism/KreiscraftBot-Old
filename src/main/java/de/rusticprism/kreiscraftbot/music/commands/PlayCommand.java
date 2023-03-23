package de.rusticprism.kreiscraftbot.music.commands;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.commands.Command;
import de.rusticprism.kreiscraftbot.config.ConfigManager;
import de.rusticprism.kreiscraftbot.config.MusicConfig;
import de.rusticprism.kreiscraftbot.config.PrefixConfig;
import de.rusticprism.kreiscraftbot.music.utils.ResultHandler;
import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.awt.*;

public class PlayCommand extends Command {
    public PlayCommand() {
        super("play", "Add a Song to the Queue", "p");
    }

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        String[] args = message.getContentDisplay().split(" ");
        if (args.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i < args.length; i++) stringBuilder.append(args[i]).append(" ");

            String url = stringBuilder.toString().trim();
            if (!url.toLowerCase().startsWith("https")) url = "ytsearch:" + url;
            Message response = message.replyEmbeds(EmbedCreator.builder("Searching for [`" + stringBuilder.substring(0, stringBuilder.length() - 1) + "`]", Color.ORANGE).build()).complete();
            KreiscraftBot.bot.getPlayerManager().loadItemOrdered(message.getGuild(), url, new ResultHandler(message.getGuild(), m, response));
            KreiscraftBot.bot.getPlayerManager().setUpHandler(channel.getGuild()).getPlayer().setVolume(ConfigManager.getConfig(MusicConfig.class).getVolume());
        } else
            channel.sendMessageEmbeds(EmbedCreator.createembed("Please use " + ConfigManager.getConfig(PrefixConfig.class).getPrefix(channel.getGuild()) + "play <url>!", Color.red)).queue();
    }
}
