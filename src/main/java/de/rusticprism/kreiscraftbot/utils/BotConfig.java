package de.rusticprism.kreiscraftbot.utils;

import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import de.rusticprism.kreiscraftbot.KreiscraftBot;
import de.rusticprism.kreiscraftbot.settings.RepeatMode;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class BotConfig {
    private final Guild guild;
    private final File file;

    private final HashMap<String,String> config;

    public BotConfig(Guild guild) {
        file = new File(URLDecoder.decode(KreiscraftBot.class.getProtectionDomain().getCodeSource().getLocation().getPath(),StandardCharsets.UTF_8)).getParentFile();
        this.guild = guild;
        config = new HashMap<>();
        load();
    }

    private void load() {
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            Reader reader = Files.newBufferedReader(Path.of(getConfigPath()));

            // convert JSON file to map
            Map<?, ?> map = gson.fromJson(reader, Map.class);
            if (map == null) {
                return;
            }

            // print map entries
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                config.put((String) entry.getKey(), (String) entry.getValue());
            }

            // close reader
            reader.close();
        } catch (IOException e) {
            KreiscraftBot.logger.log(Level.CONFIG,"Couldn't read Config File", e.getCause());
        }
    }
    public void save() {
        File file1 = new File(getConfigPath());
        file1.setWritable(true);
        JsonObject object= new JsonObject();
        config.forEach(object::addProperty);
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        try {
            FileWriter writer = new FileWriter(file1);
            writer.write(builder.create().toJson(object));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            KreiscraftBot.logger.log(Level.CONFIG,"Couldn't save Config File", e.getCause());
        }
    }
    public void set(@NotNull String path, @NotNull String value) {
        config.put(path,value);
        save();
    }
    public String get(@NotNull String path) {
        return config.get(path);
    }
    public void writeDefaultFile() {
        File file = new File(getConfigPath());
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("{ }");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        set("Prefix","!");
        set("StayInChannel","true");
        set("Repeat", String.valueOf(RepeatMode.OFF));
    }
    private String getConfigPath() {
        File file1 = new File(file, "configs\\" + guild.getIdLong() + "\\config.txt");
        if(!file1.exists()) {
            file1.getParentFile().mkdirs();
            try {
                file1.createNewFile();
            } catch (IOException e) {
                KreiscraftBot.logger.log(Level.CONFIG,"Couldn't create Config file", e.getCause());
                e.printStackTrace();
            }
        }
        return file1.getPath();
    }

    public String getPrefix() {
        return get("Prefix");
    }

    public boolean getStay() {
        return Boolean.getBoolean(config.get("StayInChannel"));
    }

    public String getRepeat() {
        return get("Repeat");
    }

    public void setRepeat(String repeat) {
        set("Repeat",repeat);
    }
    public void setPrefix(String prefix) {
        set("Prefix",prefix);
    }

    public void setStayInChannel(boolean stayInChannel) {
        set("StayInChannel", String.valueOf(stayInChannel));
    }

    public void addStempel(String id) {
        int stempel = Integer.parseInt(getStempel(id));
        setStempel(id,stempel + 1);
    }
    public String getStempel(String id) {
        if(config.get(id) == null) {
            setStempel(id,0);
            return "0";
        }
        return config.get(id);
    }
    public void setStempel(String id, int amount) {
        config.put(id, String.valueOf(amount));
        save();
    }
    public void removeStempel(String id) {
        int stempel = Integer.parseInt(getStempel(id));
        setStempel(id,stempel -1);
    }
}

