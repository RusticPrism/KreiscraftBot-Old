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

public class VolumeCommand extends Command {
    public VolumeCommand() {
        super("volume", "Change the Volume of the Bot", "");
    }

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        String[] args = message.getContentDisplay().split(" ");
        if(args.length == 2) {
            if(!channel.getId().equalsIgnoreCase("784035327462604882") && channel.getGuild().getId().equalsIgnoreCase("781146789784322079")) {
                message.delete().queue();
                return;
            }
            try {
                Integer.parseInt(args[1]);
            }catch (NumberFormatException e) {
                channel.sendMessageEmbeds(EmbedCreator.createembed("Argument 1 has to be a Number!",Color.red)).queue();
                return;
            }
            if(Integer.parseInt(args[1]) >300) {
                channel.sendMessageEmbeds(EmbedCreator.createembed("You cannot set the Volume higher than 300!", Color.RED)).queue();
                return;
            }
            AudioHandler handler = KreiscraftBot.bot.getPlayerManager().setUpHandler(channel.getGuild());
            handler.getPlayer().setVolume(Integer.parseInt(args[1]));
            channel.sendMessageEmbeds(EmbedCreator.createembed("Successfully set the Volume to " + Integer.parseInt(args[1]),Color.GREEN)).queue();

        }else channel.sendMessageEmbeds(EmbedCreator.createembed("Please use " + Prefix.getPrefix(channel.getGuild()) + "volume <Volume>!",Color.red)).queue();
    }
}
