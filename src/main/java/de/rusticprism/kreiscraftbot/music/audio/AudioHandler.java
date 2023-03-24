package de.rusticprism.kreiscraftbot.music.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import de.rusticprism.kreiscraftbot.config.ConfigManager;
import de.rusticprism.kreiscraftbot.config.MusicConfig;
import de.rusticprism.kreiscraftbot.queue.FairQueue;
import de.rusticprism.kreiscraftbot.settings.RepeatMode;
import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import de.rusticprism.kreiscraftbot.utils.FormatUtil;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

public class AudioHandler extends AudioEventAdapter implements AudioSendHandler {

    private final FairQueue<QueuedTrack> queue;
    private final List<AudioTrack> defaultQueue = new LinkedList<>();

    private final PlayerManager manager;
    private final AudioPlayer audioPlayer;
    private final long guildId;

    private Channel channel;

    private AudioFrame lastFrame;

    public AudioHandler(PlayerManager manager, Guild guild, AudioPlayer player) {
        queue = new FairQueue<>();
        this.manager = manager;
        this.audioPlayer = player;
        this.guildId = guild.getIdLong();
    }

    public int addTrackToFront(QueuedTrack qtrack) {
        if (audioPlayer.getPlayingTrack() == null) {
            audioPlayer.playTrack(qtrack.getTrack());
            return -1;
        } else {
            queue.addAt(0, qtrack);
            return 0;
        }
    }

    public void addTrack(QueuedTrack qtrack, Channel channel) {
        this.channel = channel;
        if (audioPlayer.getPlayingTrack() == null && queue.isEmpty()) {
            audioPlayer.playTrack(qtrack.getTrack());
        } else if(audioPlayer.getPlayingTrack() == null){
            audioPlayer.playTrack(queue.pull().getTrack());
            queue.add(qtrack);
        }else {
            queue.add(qtrack);
        }
    }
    public void skipTrack() {
       onTrackEnd(getPlayer(),getPlayer().getPlayingTrack(),AudioTrackEndReason.FINISHED);
    }

    public FairQueue<QueuedTrack> getQueue() {
        return queue;
    }

    public void stopAndClear() {
        queue.clear();
        defaultQueue.clear();
        audioPlayer.stopTrack();
        //current = null;
    }

    public boolean isMusicPlaying(JDA jda) {
        return guild(jda).getSelfMember().getVoiceState().inAudioChannel() && audioPlayer.getPlayingTrack() != null;
    }

    public AudioPlayer getPlayer() {
        return audioPlayer;
    }

    // Audio Events
    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        System.out.println(endReason);
        if(endReason == AudioTrackEndReason.REPLACED) return;
        RepeatMode repeatMode = ConfigManager.getConfig(MusicConfig.class).getRepeatMode(guild(channel.getJDA()));
        // if the track ended normally, and we're in repeat mode, re-add it to the queue
        if (endReason == AudioTrackEndReason.FINISHED && repeatMode != RepeatMode.OFF) {
            QueuedTrack clone = new QueuedTrack(track.makeClone(), track.getUserData(RequestMetadata.class));
            if (repeatMode == RepeatMode.QUEUE)
                queue.add(clone);
            else queue.addAt(0, clone);
        }

        if (queue.isEmpty()) {
            if (!ConfigManager.getConfig(MusicConfig.class).isStayinchannel(guild(channel.getJDA())))
                manager.getBot().closeAudioConnection(guildId);
            // unpause, in the case when the player was paused and the track has been skipped.
            // this is to prevent the player being paused next time it's being used.
            player.setPaused(false);
        } else {
            QueuedTrack qt = queue.pull();
            audioPlayer.playTrack(qt.getTrack());
        }
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        String videourl1 = track.getInfo().uri.replace("https://www.youtube.com/watch?v=","");
        TextChannel textChannel = (TextChannel) channel;
        EmbedCreator creator = EmbedCreator.builder("Queue Length: " + queue.size(),Color.GREEN)
                .setThumbnail("https://img.youtube.com/vi/"+ videourl1 + "/hqdefault.jpg")
                .setAuthor(textChannel.getGuild().getName() + " - " + "Now Playing", textChannel.getGuild().getIconUrl())
                .setDescription(":arrow_forward: **[" + track.getInfo().title + "](" + track.getInfo().uri + ")** - [`" + FormatUtil.formatTime(track.getDuration()) + "`]\n" + "Volume: `" + player.getVolume() + "`");
      textChannel.sendMessageEmbeds(creator.build())
              .addActionRow(
                      Button.of(ButtonStyle.PRIMARY,"quieter-button", Emoji.fromUnicode("U+1F509")),
                      Button.of(ButtonStyle.DANGER, "end-button",Emoji.fromUnicode("U+2716")),
                      Button.of(ButtonStyle.DANGER, "pause-button", Emoji.fromUnicode("U+23F8")),
                      Button.of(ButtonStyle.PRIMARY, "louder-button", Emoji.fromUnicode("U+1F50A")))
              .queue();
    }


    @Override
    public boolean canProvide() {
        lastFrame = audioPlayer.provide();
        return lastFrame != null;
    }

    @Override
    public ByteBuffer provide20MsAudio() {
        return ByteBuffer.wrap(lastFrame.getData());
    }

    @Override
    public boolean isOpus() {
        return true;
    }


    // Private methods
    private Guild guild(JDA jda) {
        return jda.getGuildById(guildId);
    }
}
