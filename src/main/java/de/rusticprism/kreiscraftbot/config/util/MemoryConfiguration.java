package de.rusticprism.kreiscraftbot.config.util;

import com.google.common.base.Preconditions;
import kotlin.PreconditionsKt;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class MemoryConfiguration extends MemorySection implements Configuration {
    protected Configuration defaults;
    protected ConfigurationOptions options;

    /**
     * Creates an empty {@link MemoryConfiguration} with no default values.
     */
    public MemoryConfiguration() {
    }

    /**
     * Creates an empty {@link MemoryConfiguration} using the specified {@link
     * Configuration} as a source for all default values.
     *
     * @param defaults Default value provider
     * @throws IllegalArgumentException Thrown if defaults is null
     */
    public MemoryConfiguration(Configuration defaults) {
        this.defaults = defaults;
    }

    @Override
    public void addDefault(@NotNull String path, Object value) {
        Preconditions.checkArgument(path != null, "Path may not be null");

        if (defaults == null) {
            defaults = new MemoryConfiguration();
        }

        defaults.set(path, value);
    }

    @Override
    public void addDefaults(@NotNull Map<String, Object> defaults) {
        Preconditions.checkArgument(defaults != null, "Defaults may not be null");

        for (Map.Entry<String, Object> entry : defaults.entrySet()) {
            addDefault(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void addDefaults(@NotNull Configuration defaults) {
        Preconditions.checkArgument(defaults != null, "Defaults may not be null");

        for (String key : defaults.getKeys(true)) {
            if (!defaults.isConfigurationSection(key)) {
                addDefault(key, defaults.get(key));
            }
        }
    }

    @Override
    public void setDefaults(@NotNull Configuration defaults) {
        Preconditions.checkArgument(defaults != null, "Defaults may not be null");

        this.defaults = defaults;
    }

    @Override

    public Configuration getDefaults() {
        return defaults;
    }


    @Override
    public ConfigurationSection getParent() {
        return null;
    }

    @Override
    @NotNull
    public ConfigurationOptions options() {
        if (options == null) {
            options = new ConfigurationOptions(this);
        }

        return options;
    }
}