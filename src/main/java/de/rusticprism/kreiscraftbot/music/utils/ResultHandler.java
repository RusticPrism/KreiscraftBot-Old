package de.rusticprism.kreiscraftbot.music.utils;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.music.audio.AudioHandler;
import de.rusticprism.kreiscraftbot.music.audio.QueuedTrack;
import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import de.rusticprism.kreiscraftbot.utils.FormatUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import org.apache.commons.codec.binary.StringUtils;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class ResultHandler implements AudioLoadResultHandler {

    private final Guild guild;
    private final Member user;
    private final Message message;

    public ResultHandler(Guild guild, Member user, Message message) {
        this.guild = guild;
        this.user = user;
        this.message = message;
    }

    private void loadSingle(AudioTrack track, AudioPlaylist playlist) {
        AudioHandler handler = KreiscraftBot.bot.getPlayerManager().setUpHandler(guild);

        //Join VoiceChannel of Member
        if (user.getVoiceState().getChannel() == null) {
            message.editMessageEmbeds(EmbedCreator.createembed("You are not connected to any VoiceChannel!", Color.red)).queue();
            return;
        }
        if (user.getVoiceState().isDeafened()) {
            message.editMessageEmbeds(EmbedCreator.createembed("How do you wan´t to listen to music if you are deafened!", Color.red)).queue();
            return;
        }
        guild.getAudioManager().openAudioConnection(user.getVoiceState().getChannel());
        //Add Track
        EmbedCreator creator = EmbedCreator.builder(null,
                        "**Successfully added [" + track.getInfo().title + "](" + track.getInfo().uri + ") [`" + FormatUtil.formatTime(track.getDuration()) + "`]** " +
                        "\n *Requested by " + user.getUser().getAsMention() + "*", Color.GREEN)
                .setAuthor(guild.getName() + " - Requested",guild.getIconUrl());
        message.editMessageEmbeds(creator.build())
                .complete()
                .delete().queueAfter(15, TimeUnit.SECONDS);
        handler.addTrack(new QueuedTrack(track, user.getUser()), message.getChannel());
    }

    private int loadPlayerlist(AudioPlaylist audioPlaylist, AudioTrack exclude) {
        int[] count = {0};
        audioPlaylist.getTracks().forEach((track) -> {
            if (track == exclude) {
                return;
            }
            AudioHandler handler = KreiscraftBot.bot.getPlayerManager().setUpHandler(guild);
            handler.addTrack(new QueuedTrack(track, user.getUser()), message.getChannel());
            count[0]++;
        });
        return count[0];
    }

    @Override
    public void trackLoaded(AudioTrack audioTrack) {
        loadSingle(audioTrack, null);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {
        if (playlist.getTracks().size() == 1 || playlist.isSearchResult()) {
            AudioTrack single = playlist.getSelectedTrack() == null ? playlist.getTracks().get(0) : playlist.getSelectedTrack();
            loadSingle(single, null);
        } else if (playlist.getTracks() != null) {
            AudioTrack single = playlist.getSelectedTrack();
            loadSingle(single, playlist);
        } else {
            int count = loadPlayerlist(playlist, null);
            if (playlist.getTracks().size() == 0) {
                message.getChannel().sendMessageEmbeds(EmbedCreator.createembed("The Playlist is empty", Color.red)).queue();
            } else if (count == 0) {
                message.getChannel().sendMessageEmbeds(EmbedCreator.createembed("Error", Color.red)).queue();
            } else
                message.getChannel().sendMessageEmbeds(EmbedCreator.createembed("Successfully loaded every Song from the Playlist", Color.green)).queue();
        }
    }

    @Override
    public void noMatches() {
        message.getChannel().sendMessageEmbeds(EmbedCreator.createembed("Song or Playlist could not be found!", Color.red)).queue();
    }

    @Override
    public void loadFailed(FriendlyException e) {
        message.getChannel().sendMessageEmbeds(EmbedCreator.createembed("Error: " + e.getMessage(), Color.red)).queue();
    }
}
