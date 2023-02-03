package de.rusticprism.kreiscraftbot.utils;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;

public class OptionList{
    private final OptionBuilder[] optionBuilders;
    public OptionList(OptionBuilder... optionBuilders) {
        this.optionBuilders = optionBuilders;
    }
    public void registerOptions(CommandCreateAction action) {
        for(OptionBuilder builder : optionBuilders) {
            if(builder.getType() == OptionType.SUB_COMMAND) {
                action.addSubcommands(new SubcommandData(builder.getName(),builder.getDescription())).queue();
            }else action.addOption(builder.getType(),builder.getName(),builder.getDescription(),builder.isRequired()).queue();
        }
    }
}
