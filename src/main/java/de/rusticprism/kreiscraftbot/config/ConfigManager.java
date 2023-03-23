package de.rusticprism.kreiscraftbot.config;

import de.rusticprism.kreiscraftbot.utils.NameableStorage;

public class ConfigManager {
    public static final NameableStorage<Config> configs = new NameableStorage<>(Config::getName);

    public static void register(Config configuration) {
        configs.add(configuration);
    }

    public static void unregister(Config configuration) {
        configs.remove(configuration);
    }

    public static <C extends Config> C getConfig(Class<C> class_) {
        return configs.get(class_);
    }

    public ConfigManager() {
        registerAllConfigs();
    }

    private void registerAllConfigs() {
        register(new MusicConfig());
        register(new PrefixConfig());
    }

}
