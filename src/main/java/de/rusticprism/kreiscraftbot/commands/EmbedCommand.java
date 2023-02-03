package de.rusticprism.kreiscraftbot.commands;

import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;

public class EmbedCommand extends SlashCommand{
    public EmbedCommand() {
        super("embed", "Create a Custom Embed");
    }

    @Override
    public void performCommand(Member member, TextChannel channel, SlashCommandInteraction interaction) {
        if(!member.hasPermission(Permission.ADMINISTRATOR)) {
            interaction.replyEmbeds(EmbedCreator.createembed("NoPermission","You don´t have the Permission to perfrom that Command", Color.RED)).setEphemeral(true).queue();
            return;
        }
        TextInput input1 = TextInput.create("embed-name", "Name:", TextInputStyle.SHORT)
                .setRequiredRange(1,50)
                .setRequired(true)
                .setPlaceholder("Your Embed Name")
                .build();

        TextInput input2 = TextInput.create("embed-description", "Description:", TextInputStyle.PARAGRAPH)
                .setRequiredRange(1,4000)
                .setRequired(true)
                .setPlaceholder("Your Embed Description")
                .build();

        TextInput input3 = TextInput.create("embed-image", "Image: (Optional)", TextInputStyle.SHORT)
                .setRequiredRange(1, 1000)
                .setRequired(false)
                .setPlaceholder("https://cdn.discordapp.com/avatars/736623799256219690/49e0627d8301a651e496da1f6ca33e0d.png?size=4096")
                .build();

        Modal modal = Modal.create("embed-modal", "Create a Custom Embed")
                .addActionRow(input1)
                .addActionRow(input2)
                .addActionRow(input3)
                .build();

        interaction.replyModal(modal).queue();
    }
}
