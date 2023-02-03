package de.rusticprism.kreiscraftbot.listener;

import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ModalListener extends ListenerAdapter {
    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        if(event.getModalId().equals("embed-modal")) {

            String titel = event.getValue("embed-name").getAsString();
            String description = event.getValue("embed-description").getAsString();
            String image = event.getValue("embed-image").getAsString();

            EmbedBuilder builder = new EmbedBuilder()
                    .setTitle(titel)
                    .setDescription(description)
                    .setFooter(EmbedCreator.getFooter())
                    .setColor(Color.DARK_GRAY.getRGB());
            if (image.startsWith("http")) {
                builder.setImage(image);
            }
            event.replyEmbeds(builder.build()).queue();

        }
    }
}
