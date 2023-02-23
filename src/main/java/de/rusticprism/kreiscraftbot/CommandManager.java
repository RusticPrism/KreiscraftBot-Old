package de.rusticprism.kreiscraftbot;

import de.rusticprism.kreiscraftbot.commands.*;
import de.rusticprism.kreiscraftbot.music.commands.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {
    public ConcurrentHashMap<String, Command> commands;
    public ConcurrentHashMap<String, SlashCommand> slashCommands;

    public CommandManager() {
        this.commands = new ConcurrentHashMap<>();
        this.slashCommands = new ConcurrentHashMap<>();

        //Prefix Commands
        registerCommand(new VolumeCommand());
        registerCommand(new StopCommand());
        registerCommand(new TrackinfoCommand());
        registerCommand(new PlayCommand());
        registerCommand(new SkipCommand());
        registerCommand(new ClearQueueCommand());
        registerCommand(new AdminCommand());

        //SlashCommands
        registerSlashCommand(new EmbedCommand());
        registerSlashCommand(new ShutDownCommand());
        registerSlashCommand(new PrefixCommand());
        registerSlashCommand(new StempelSlashCommand());
        registerSlashCommand(new StempelListCommand());
        registerSlashCommand(new UnStempelCommand());
        registerSlashCommand(new BotChannelCommand());
    }

    public boolean perform(String command, Member m, TextChannel channel, Message message) {

        Command cmd;
        if ((cmd = this.commands.get(command.toLowerCase())) != null) {
            cmd.performCommand(m, channel, message);
            return true;
        } else {
            for (Command command1 : commands.values()) {
                if (Arrays.stream(command1.alias).toList().contains(command.toLowerCase())) {
                    command1.performCommand(m, channel, message);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean performSlash(String command, Member m, TextChannel channel, SlashCommandInteraction interaction) {
        SlashCommand cmd;
        if ((cmd = this.slashCommands.get(command.toLowerCase())) != null) {
            cmd.performCommand(m, channel, interaction);
            return true;
        }
        return false;
    }

    public void registerSlashCommand(SlashCommand command) {
        this.slashCommands.put(command.command, command);
        for(Guild guild : KreiscraftBot.bot.getJDA().getGuilds()) {
            command.option.registerOptions(guild.upsertCommand(command.command,command.description));
            for(String alias : command.alias) {
                command.option.registerOptions(guild.upsertCommand(alias,command.description));
            }
        }
    }

    public void registerCommand(Command command) {
        this.commands.put(command.command, command);
        for (String s : command.alias) {
            this.commands.put(s, command);
        }
    }
}
