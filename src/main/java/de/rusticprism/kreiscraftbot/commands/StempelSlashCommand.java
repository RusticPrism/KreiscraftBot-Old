package de.rusticprism.kreiscraftbot.commands;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import de.rusticprism.kreiscraftbot.utils.OptionBuilder;
import de.rusticprism.kreiscraftbot.utils.OptionList;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

import java.awt.*;

public class StempelSlashCommand extends SlashCommand{
    public StempelSlashCommand() {
        super("stempel","Gib irgendwem einen Stempel",
                new OptionList(new OptionBuilder(OptionType.MENTIONABLE,"user","Der User der den Stempel bekommt",true)));
    }
    @Override
    public void performCommand(Member member, TextChannel channel, SlashCommandInteraction interaction) {
        if(!member.hasPermission(Permission.ADMINISTRATOR)) {
            interaction.replyEmbeds(EmbedCreator.createembed("NoPermission","You don´t have the Permission to perfrom that Command", Color.RED)).setEphemeral(true).queue();
            return;
        }
        if(interaction.getOption("user") == null) {
            interaction.replyEmbeds(EmbedCreator.createembed("Kein User gefunden!", Color.RED)).queue();
            return;
        }
        String id = interaction.getOption("user").getAsString();
        User user = KreiscraftBot.bot.getJDA().getUserById(id);
        if(user.isSystem() || user.isBot()) {
            interaction.replyEmbeds(EmbedCreator.createembed("Du kannst keinem Bot einen Stempel geben!",Color.red)).queue();
            return;
        }
        KreiscraftBot.bot.getConfig(channel.getGuild()).addStempel(String.valueOf(user.getIdLong()));
        interaction.replyEmbeds(EmbedCreator.createembed("Ein Stempel wurde dem User \"**" + user.getName() + "**\" hinzugefügt!", Color.GREEN)).queue();
    }
}
