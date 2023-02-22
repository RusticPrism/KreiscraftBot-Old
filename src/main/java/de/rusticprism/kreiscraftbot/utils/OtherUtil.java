package de.rusticprism.kreiscraftbot.utils;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import de.rusticprism.kreiscraftbot.KreiscraftBot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.Date;

public class OtherUtil {
    private final static String WINDOWS_INVALID_PATH = "c:\\windows\\system32\\";
    public static Path getPath(String path) {
        Path result = Paths.get(path);
        // special logic to prevent trying to access system32
        if (result.toAbsolutePath().toString().toLowerCase().startsWith(WINDOWS_INVALID_PATH)) {
            try {
                result = Paths.get(new File(KreiscraftBot.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath() + File.separator + path);
            } catch (URISyntaxException ignored) {
            }
        }
        return result;
    }
    public static void logError(Exception e) {
        StringBuilder message = new StringBuilder();
        message.append(e.getMessage()).append("\n");
        for(StackTraceElement element : e.getStackTrace()) {
            message.append(element).append("\n");
        }
        System.out.println(message);
        Date date = Date.from(Instant.now());
        File file = getPath("errors/" + DateFormat.getDateInstance().format(date) + ".log").toFile();
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException ignored) {}
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(String.valueOf(message));
            writer.flush();
            writer.close();
        } catch (IOException ignored) {
        }
    }

}
