package de.rusticprism.kreiscraftbot.settings;

public abstract class Setting {
    private Object value;
    private final String name;
    public Setting(String name) {
        this.name = name;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
