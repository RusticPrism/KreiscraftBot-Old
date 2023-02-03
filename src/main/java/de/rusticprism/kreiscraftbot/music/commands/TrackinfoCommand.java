package de.rusticprism.kreiscraftbot.music.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.commands.Command;
import de.rusticprism.kreiscraftbot.music.audio.AudioHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class TrackinfoCommand extends Command {
    public TrackinfoCommand() {
        super("trackinfo", "Get the Information of the current Song", "tinfo" , "tracki");
    }

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        AudioHandler handler = KreiscraftBot.bot.getPlayerManager().setUpHandler(channel.getGuild());
        AudioPlayer player = handler.getPlayer();
        AudioTrack track;
        if((track = player.getPlayingTrack()) != null) {
            AudioTrackInfo info = track.getInfo();

            String author = info.author;
            String title = info.title;
            String url = info.uri;
            boolean isStream = info.isStream;
            long position = track.getPosition();
            long length = track.getDuration();

            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor(author);
            builder.setTitle(title, url);
            track.getIdentifier();
            String videourl1 = url.replace("https://www.youtube.com/watch?v=","");
            builder.setImage("https://img.youtube.com/vi/"+ videourl1 + "/hqdefault.jpg");

            long curSeconds = position / 1000;
            long curMinutes = curSeconds / 60;
            long curStunden = curMinutes / 60;
            curSeconds %= 60;
            curMinutes %= 60;

            long maxSeconds = length / 1000;
            long maxMinutes = maxSeconds / 60;
            long maxStunden = maxMinutes / 60;
            maxSeconds %= 60;
            maxMinutes %= 60;

            String time = ((curStunden > 0) ? curStunden + "h " : "") + curMinutes + "min " + curSeconds + "s / " + ((maxStunden > 0) ? maxStunden + "h " : "") + maxMinutes + "min " + maxSeconds + "s";


            builder.setDescription(isStream ? "-> STREAM" : "-> " + time);

            channel.sendMessageEmbeds(builder.build()).queue();
        }else channel.sendMessageEmbeds(new EmbedBuilder().setDescription("I´am not playing anything!").build()).queue();
    }
}
