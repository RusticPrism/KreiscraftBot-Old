package de.rusticprism.kreiscraftbot.music.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.config.ConfigManager;
import de.rusticprism.kreiscraftbot.config.MusicConfig;
import de.rusticprism.kreiscraftbot.queue.FairQueue;
import de.rusticprism.kreiscraftbot.settings.RepeatMode;
import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import de.rusticprism.kreiscraftbot.utils.FormatUtil;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

import java.awt.*;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

public class AudioHandler extends AudioEventAdapter implements AudioSendHandler {

    private final FairQueue<QueuedTrack> queue;

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
        } else if (audioPlayer.getPlayingTrack() == null) {
            audioPlayer.playTrack(queue.pull().getTrack());
            queue.add(qtrack);
        } else {
            queue.add(qtrack);
        }
    }

    public void skipTrack() {
        onTrackEnd(getPlayer(), getPlayer().getPlayingTrack(), AudioTrackEndReason.FINISHED);
    }

    public FairQueue<QueuedTrack> getQueue() {
        return queue;
    }

    public void stopAndClear() {
        queue.clear();
        audioPlayer.stopTrack();
        //current = null;
    }

    public AudioPlayer getPlayer() {
        return audioPlayer;
    }

    // Audio Events
    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        RepeatMode repeatMode = ConfigManager.getConfig(MusicConfig.class).getRepeatMode(guild());
        QueuedTrack clone = new QueuedTrack(track.makeClone(), track.getUserData(RequestMetadata.class));
        if (endReason.equals(AudioTrackEndReason.REPLACED)) return;
        if (endReason.equals(AudioTrackEndReason.FINISHED)) {
            if (repeatMode == RepeatMode.QUEUE) {
                queue.add(clone);
                return;
            }
            if (repeatMode == RepeatMode.SONG) {
                queue.addAt(0, clone);
                return;
            }
            if (queue.isEmpty()) {
                if (!ConfigManager.getConfig(MusicConfig.class).isStayinchannel(guild())) {
                    manager.getBot().closeAudioConnection(guildId);
                    player.setPaused(false);
                }
            } else {
                QueuedTrack queuedTrack = queue.pull();
                audioPlayer.playTrack(queuedTrack.getTrack());
            }
        }
        if (endReason == AudioTrackEndReason.LOAD_FAILED) {
            audioPlayer.playTrack(track);
            TextChannel textChannel = (TextChannel) channel;
            textChannel.sendMessageEmbeds(EmbedCreator.builder("Error loading Song! Trying again in 10 Seconds...", Color.RED).build())
                    .complete().delete().completeAfter(10, TimeUnit.SECONDS);
            KreiscraftBot.bot.getThreadpool().schedule(() -> {
                queue.addAt(0, clone);
                skipTrack();
            }, 10, TimeUnit.SECONDS);
        }
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        String videourl1 = track.getInfo().uri.replace("https://www.youtube.com/watch?v=", "");
        TextChannel textChannel = (TextChannel) channel;
        EmbedCreator creator = EmbedCreator.builder("Queue Length: " + queue.size(), Color.GREEN)
                .setThumbnail("https://img.youtube.com/vi/" + videourl1 + "/hqdefault.jpg")
                .setAuthor(textChannel.getGuild().getName() + " - " + "Now Playing", textChannel.getGuild().getIconUrl())
                .setDescription(":arrow_forward: **[" + track.getInfo().title + "](" + track.getInfo().uri + ")** - [`" + FormatUtil.formatTime(track.getDuration()) + "`]\n" + "Volume: `" + player.getVolume() + "`");
        textChannel.sendMessageEmbeds(creator.build())
                .addActionRow(
                        Button.of(ButtonStyle.PRIMARY, "quieter-button", Emoji.fromUnicode("U+1F509")),
                        Button.of(ButtonStyle.DANGER, "end-button", Emoji.fromUnicode("U+2716")),
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
    private Guild guild() {
        return KreiscraftBot.bot.getJDA().getGuildById(guildId);
    }
}
