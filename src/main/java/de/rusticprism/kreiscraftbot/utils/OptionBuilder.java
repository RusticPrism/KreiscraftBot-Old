package de.rusticprism.kreiscraftbot.utils;

import net.dv8tion.jda.api.interactions.commands.OptionType;

public class OptionBuilder {
    private final OptionType type;
    private final String name;
    private boolean required;
    private String description;
    public OptionBuilder(OptionType type, String name) {
        this.name = name;
        this.type = type;
        this.required = false;
        this.description = "A Command Description";
    }
    public OptionBuilder(OptionType type, String name,String description,boolean required) {
        this.name = name;
        this.type = type;
        this.required = required;
        this.description = description;
    }
    public OptionBuilder setRequired(boolean required) {
        this.required = required;
        return this;
    }
    public OptionBuilder setDescription(String desc) {
        this.description = desc;
        return this;
    }

    public String getName() {
        return name;
    }

    public OptionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
    public boolean isRequired() {
        return required;
    }
}
