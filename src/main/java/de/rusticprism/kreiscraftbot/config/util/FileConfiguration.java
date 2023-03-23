package de.rusticprism.kreiscraftbot.config.util;

import com.google.common.base.Preconditions;
import com.google.common.io.Files;
import de.rusticprism.kreiscraftbot.KreiscraftBot;
import kotlin.PreconditionsKt;
import kotlin.io.FilesKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public abstract class FileConfiguration extends MemoryConfiguration {
    public FileConfiguration() {
        super();
    }

    public void saveToFile(File file, String errorMessage) {
        try {
            this.save(file);
        } catch (IOException e) {
            KreiscraftBot.logger.warn(errorMessage, e);
        }
    }

    public void save(File file) throws IOException {
        Preconditions.checkArgument(file != null, "File cannot be null");

        Files.createParentDirs(file);

        String data = saveToString();

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8)) {
            writer.write(data);
        }
    }

    /**
     * Saves this {@link FileConfiguration} to the specified location.
     * <p>
     * If the file does not exist, it will be created. If already exists, it
     * will be overwritten. If it cannot be overwritten or created, an
     * exception will be thrown.
     * <p>
     * This method will save using the system default encoding, or possibly
     * using UTF8.
     *
     * @param file File to save to.
     * @throws IOException              Thrown when the given file cannot be written to for
     *                                  any reason.
     * @throws IllegalArgumentException Thrown when file is null.
     */
    public void save(String file) throws IOException {
        Preconditions.checkArgument(file != null, "File cannot be null");

        save(new File(file));
    }

    /**
     * Saves this {@link FileConfiguration} to a string, and returns it.
     *
     * @return String containing this configuration.
     */
    @NotNull
    public abstract String saveToString();

    /**
     * Loads this {@link FileConfiguration} from the specified location.
     * <p>
     * All the values contained within this configuration will be removed,
     * leaving only settings and defaults, and the new values will be loaded
     * from the given file.
     * <p>
     * If the file cannot be loaded for any reason, an exception will be
     * thrown.
     *
     * @param file File to load from.
     * @throws FileNotFoundException         Thrown when the given file cannot be
     *                                       opened.
     * @throws IOException                   Thrown when the given file cannot be read.
     * @throws InvalidConfigurationException Thrown when the given file is not
     *                                       a valid Configuration.
     * @throws IllegalArgumentException      Thrown when file is null.
     */
    public void load(File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        Preconditions.checkArgument(file != null, "File cannot be null");

        final FileInputStream stream = new FileInputStream(file);

        load(new InputStreamReader(stream, Charsets.UTF_8));
    }

    /**
     * Loads this {@link FileConfiguration} from the specified reader.
     * <p>
     * All the values contained within this configuration will be removed,
     * leaving only settings and defaults, and the new values will be loaded
     * from the given stream.
     *
     * @param reader the reader to load from
     * @throws IOException                   thrown when underlying reader throws an IOException
     * @throws InvalidConfigurationException thrown when the reader does not
     *                                       represent a valid Configuration
     * @throws IllegalArgumentException      thrown when reader is null
     */
    public void load(@NotNull Reader reader) throws IOException, InvalidConfigurationException {
        BufferedReader input = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);

        StringBuilder builder = new StringBuilder();

        try {
            String line;

            while ((line = input.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
        } finally {
            input.close();
        }

        loadFromString(builder.toString());
    }

    /**
     * Loads this {@link FileConfiguration} from the specified location.
     * <p>
     * All the values contained within this configuration will be removed,
     * leaving only settings and defaults, and the new values will be loaded
     * from the given file.
     * <p>
     * If the file cannot be loaded for any reason, an exception will be
     * thrown.
     *
     * @param file File to load from.
     * @throws FileNotFoundException         Thrown when the given file cannot be
     *                                       opened.
     * @throws IOException                   Thrown when the given file cannot be read.
     * @throws InvalidConfigurationException Thrown when the given file is not
     *                                       a valid Configuration.
     * @throws IllegalArgumentException      Thrown when file is null.
     */
    public void load(String file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        Preconditions.checkArgument(file != null, "File cannot be null");

        load(new File(file));
    }

    /**
     * Loads this {@link FileConfiguration} from the specified string, as
     * opposed to from file.
     * <p>
     * All the values contained within this configuration will be removed,
     * leaving only settings and defaults, and the new values will be loaded
     * from the given string.
     * <p>
     * If the string is invalid in any way, an exception will be thrown.
     *
     * @param contents Contents of a Configuration to load.
     * @throws InvalidConfigurationException Thrown if the specified string is
     *                                       invalid.
     * @throws IllegalArgumentException      Thrown if contents is null.
     */
    public abstract void loadFromString(@NotNull String contents) throws InvalidConfigurationException;

    @NotNull
    @Deprecated
    protected String buildHeader() {
        return "";
    }

    @NotNull
    @Override
    public ConfigurationOptions options() {
        if (options == null) {
            options = new ConfigurationOptions(this);
        }

        return options;
    }
}