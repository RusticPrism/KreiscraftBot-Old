package de.rusticprism.kreiscraftbot.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public abstract class Command {

    public String command;
    public String description;
    public String[] alias;
    public Command(String command ,String description, String... alias) {
        this.command = command;
        this.description = description;
        this.alias = alias;
    }
    public abstract void performCommand(Member m, TextChannel channel, Message message);

}
