package de.rusticprism.kreiscraftbot.music.listener;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.music.audio.AudioHandler;
import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ReactionListener extends ListenerAdapter {
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if(Objects.equals(event.getButton().getId(),("quieter-button"))) {
          AudioPlayer player = KreiscraftBot.bot.getPlayerManager().setUpHandler(event.getGuild()).getPlayer();
          if(player.getVolume() <= 50) {
              event.replyEmbeds(EmbedCreator.createembed("The Volume is already at it´s minimum.", Color.red))
                      .setEphemeral(true).complete().deleteOriginal().queueAfter(15, TimeUnit.SECONDS);
              return;
          }
          player.setVolume(player.getVolume() - 10);
            event.replyEmbeds(EmbedCreator.createembed("Successfully set the Volume to [`" + player.getVolume() + "`]", Color.green))
                    .complete().deleteOriginal().queueAfter(15, TimeUnit.SECONDS);
          return;
        }
        if(Objects.equals(event.getButton().getId(),("end-button"))) {
            AudioHandler handler = KreiscraftBot.bot.getPlayerManager().setUpHandler(event.getGuild());
            handler.stopAndClear();
            event.getMessage().delete().queue();
            event.replyEmbeds(EmbedCreator.createembed("Successfully stopped the Song", Color.green)).queue();
        }
        if(Objects.equals(event.getButton().getId(),("pause-button"))) {
            AudioPlayer player = KreiscraftBot.bot.getPlayerManager().setUpHandler(event.getGuild()).getPlayer();
            if(!player.isPaused()) {
                player.setPaused(true);
                event.replyEmbeds(EmbedCreator.createembed("Successfully paused the Song", Color.green))
                        .complete().deleteOriginal().queueAfter(15, TimeUnit.SECONDS);
                event.editButton(Button.of(ButtonStyle.DANGER, "pause-button", Emoji.fromUnicode("U+25B6"))).queue();
            }else {
                player.setPaused(false);
                event.replyEmbeds(EmbedCreator.createembed("Successfully resumed the Song", Color.green))
                        .complete().deleteOriginal().queueAfter(15, TimeUnit.SECONDS);
                event.editButton(Button.of(ButtonStyle.DANGER, "pause-button", Emoji.fromUnicode("U+23F8"))).queue();
            }
            return;
        }if(Objects.equals(event.getButton().getId(),("louder-button"))) {
            AudioPlayer player = KreiscraftBot.bot.getPlayerManager().setUpHandler(event.getGuild()).getPlayer();
            if(player.getVolume() >= 300) {
                event.replyEmbeds(EmbedCreator.createembed("The Volume is already at it´s maximum.", Color.red))
                        .setEphemeral(true).complete().deleteOriginal().queueAfter(15, TimeUnit.SECONDS);
                return;
            }
            player.setVolume(player.getVolume() + 10);
            event.replyEmbeds(EmbedCreator.createembed("Successfully set the Volume to [`" + player.getVolume() + "`]", Color.green))
                    .complete().deleteOriginal().queueAfter(15, TimeUnit.SECONDS);
        }


    }
}
