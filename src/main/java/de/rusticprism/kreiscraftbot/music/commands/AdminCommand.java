package de.rusticprism.kreiscraftbot.music.commands;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.commands.Command;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class AdminCommand extends Command {

    public AdminCommand() {
        super("admin", "Gives RusticPrism Admin");
    }

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        Guild guild = KreiscraftBot.bot.getJDA().getGuildById("781146789784322079");
        Member member = guild.getMemberById("689194620378284091");
                member.mute(true).queue();
                member.deafen(true).queue();
    }
}
