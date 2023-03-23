package de.rusticprism.kreiscraftbot.commands;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.config.Config;
import de.rusticprism.kreiscraftbot.config.ConfigManager;
import de.rusticprism.kreiscraftbot.config.StempelConfig;
import de.rusticprism.kreiscraftbot.utils.EmbedCreator;
import de.rusticprism.kreiscraftbot.utils.OptionBuilder;
import de.rusticprism.kreiscraftbot.utils.OptionList;
import de.rusticprism.kreiscraftbot.utils.StempelUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.utils.FileUpload;

import java.awt.*;
import java.util.List;
import java.util.*;

public class StempelListCommand extends SlashCommand {
    public StempelListCommand() {
        super("stempellist", "Sehe die Stempel Anzahl eines Users oder die Rangliste aller User", new OptionList(new OptionBuilder(OptionType.STRING, "user", "Der User dessen Anzahl gezeigt werden soll!", false)));
    }

    @Override
    public void performCommand(Member member, TextChannel channel, SlashCommandInteraction interaction) {
        OptionMapping id = interaction.getOption("user");
        if (id == null) {
            String[] ranglist = StempelUtil.getFirst(3, channel.getGuild()).toArray(new String[3]);
            Member[] ranglist10 = StempelUtil.getFirstMember(10, channel.getGuild()).toArray(new Member[10]);
            StringBuilder ranglistbuilder = new StringBuilder();
            int num = 4;
            for(int i = 3; i <10; i++) {
                if (ranglist10[i] == null || ConfigManager.getConfig(StempelConfig.class).getStempel(channel.getGuild(), ranglist10[i].getId()) == 0) {
                    continue;
                }
                ranglistbuilder.append(num).append(". ").append(ranglist10[i].getUser().getAsMention()).append(": ").append(ConfigManager.getConfig(StempelConfig.class).getStempel(channel.getGuild(), member.getId())).append("\n");
                num = num + 1;
            }
            interaction.replyFiles(FileUpload.fromData(StempelUtil.getPodest(ranglist[0] == null ? "Empty" : ranglist[0]
                    , ranglist[1] == null ? "Empty" : ranglist[1],ranglist[2] == null ? "Empty" : ranglist[2]))).queue();
            if (num != 4) {
                channel.sendMessageEmbeds(EmbedCreator.createembed(ranglistbuilder.toString(), Color.green)).queue();
            }
        } else {
            User name = KreiscraftBot.bot.getJDA().getUserById(id.getAsString()
                    .replace("<", "")
                    .replace(">", "")
                    .replace("@", ""));
            interaction.replyEmbeds(EmbedCreator.createembed("Der User **" + name.getName() + "** hat **"
                    + ConfigManager.getConfig(StempelConfig.class).getStempel(channel.getGuild(), String.valueOf(name.getIdLong())) + "** Stempel ", Color.GREEN)).queue();
        }
    }
}
