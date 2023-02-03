package de.rusticprism.kreiscraftbot.commands;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

import java.awt.*;

public class ShutDownCommand extends SlashCommand {
    public ShutDownCommand() {
        super("shutdown", "Shut the Bot down!");
    }

    @Override
    public void performCommand(Member member, TextChannel channel, SlashCommandInteraction interaction) {
        if(!member.hasPermission(Permission.ADMINISTRATOR)) {
            interaction.replyEmbeds(EmbedCreator.createembed("NoPermission","You don´t have the Permission to perfrom that Command", Color.RED)).setEphemeral(true).queue();
            return;
        }
        interaction.replyEmbeds(EmbedCreator.createembed("Shutting down the Bot...", Color.ORANGE)).complete();
        KreiscraftBot.bot.shutdown();
    }
}
