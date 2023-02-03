package de.rusticprism.kreiscraftbot.commands;

import de.rusticprism.kreiscraftbot.utils.OptionBuilder;
import de.rusticprism.kreiscraftbot.utils.OptionList;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;


public abstract class SlashCommand {
    public String command;

    public String description;

    public String[] alias;

    public OptionList option;

    public SlashCommand(String command, String description, OptionList list,String... alias) {
        this.command = command;
        this.description =description;
        this.alias = alias;
        this.option = list;
    }
    public SlashCommand(String command, String description,String... alias) {
        this.command = command;
        this.description =description;
        this.alias = alias;
        this.option = new OptionList();
    }

    public abstract void performCommand(Member member, TextChannel channel, SlashCommandInteraction interaction);
}
