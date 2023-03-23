package de.rusticprism.kreiscraftbot.config;

import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.config.util.FileConfiguration;
import de.rusticprism.kreiscraftbot.config.util.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public abstract class Config {
    private FileConfiguration config;
    private final File file;
    private final String name;

    public Config(String name) {
        this.name = name;
        this.file = new File(new File(URLDecoder.decode(KreiscraftBot.class.getProtectionDomain().getCodeSource().getLocation().getPath(), StandardCharsets.UTF_8)).getParentFile().getAbsolutePath() + "/" + name + ".yml");
        load();
    }

    public void load() {
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                this.config = YamlConfiguration.loadConfiguration(file);
                createDefault();
                saveConfig();
            } catch (IOException e) {
                KreiscraftBot.logger.warn("There was an error creating the Config! (" + name + ")", e.getCause());
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveConfig() {
        config.saveToFile(file, "There was an error saving the Config! (" + name + ")");
    }

    public abstract void createDefault();

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public String getName() {
        return name;
    }
}
