package de.rusticprism.kreiscraftbot.config.util;

import com.google.common.base.Preconditions;
import kotlin.PreconditionsKt;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConfigurationOptions {
    private List<String> header = Collections.emptyList();
    private List<String> footer = Collections.emptyList();
    private static final boolean PAPER_PARSE_COMMENTS_BY_DEFAULT = Boolean.parseBoolean(System.getProperty("Paper.parseYamlCommentsByDefault", "true"));
    private boolean parseComments = PAPER_PARSE_COMMENTS_BY_DEFAULT;
    private char pathSeparator = '.';
    private boolean copyDefaults = false;
    private final Configuration configuration;
    private int indent = 2;
    private int width = 80;
    private int codePointLimit = Integer.MAX_VALUE;

    protected ConfigurationOptions(@NotNull Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Returns the {@link Configuration} that this object is responsible for.
     *
     * @return Parent configuration
     */
    @NotNull
    public Configuration configuration() {
        return configuration;
    }

    /**
     * Gets the char that will be used to separate {@link
     * ConfigurationSection}s
     * <p>
     * This value does not affect how the {@link Configuration} is stored,
     * only in how you access the data. The default value is '.'.
     *
     * @return Path separator
     */
    public char pathSeparator() {
        return pathSeparator;
    }

    /**
     * Sets the char that will be used to separate {@link
     * ConfigurationSection}s
     * <p>
     * This value does not affect how the {@link Configuration} is stored,
     * only in how you access the data. The default value is '.'.
     *
     * @param value Path separator
     * @return This object, for chaining
     */
    @NotNull
    public ConfigurationOptions pathSeparator(char value) {
        this.pathSeparator = value;
        return this;
    }

    /**
     * Checks if the {@link Configuration} should copy values from its default
     * {@link Configuration} directly.
     * <p>
     * If this is true, all values in the default Configuration will be
     * directly copied, making it impossible to distinguish between values
     * that were set and values that are provided by default. As a result,
     * {@link ConfigurationSection#contains(String)} will always
     * return the same value as {@link
     * ConfigurationSection#isSet(String)}. The default value is
     * false.
     *
     * @return Whether defaults are directly copied
     */
    public boolean copyDefaults() {
        return copyDefaults;
    }

    /**
     * Sets if the {@link Configuration} should copy values from its default
     * {@link Configuration} directly.
     * <p>
     * If this is true, all values in the default Configuration will be
     * directly copied, making it impossible to distinguish between values
     * that were set and values that are provided by default. As a result,
     * {@link ConfigurationSection#contains(String)} will always
     * return the same value as {@link
     * ConfigurationSection#isSet(String)}. The default value is
     * false.
     *
     * @param value Whether defaults are directly copied
     * @return This object, for chaining
     */
    @NotNull
    public ConfigurationOptions copyDefaults(boolean value) {
        this.copyDefaults = value;
        return this;
    }

    @NotNull
    public List<String> getHeader() {
        return header;
    }

    /**
     * @return The string header.
     * @deprecated use getHeader() instead.
     */
    @NotNull
    @Deprecated
    public String header() {
        StringBuilder stringHeader = new StringBuilder();
        for (String line : header) {
            stringHeader.append(line == null ? "\n" : line + "\n");
        }
        return stringHeader.toString();
    }

    /**
     * Sets the header that will be applied to the top of the saved output.
     * <p>
     * This header will be commented out and applied directly at the top of
     * the generated output of the {@link FileConfiguration}. It is not
     * required to include a newline at the end of the header as it will
     * automatically be applied, but you may include one if you wish for extra
     * spacing.
     * <p>
     * If no comments exist, an empty list will be returned. A null entry
     * represents an empty line and an empty String represents an empty comment
     * line.
     *
     * @param value New header, every entry represents one line.
     * @return This object, for chaining
     */
    @NotNull
    public ConfigurationOptions setHeader(List<String> value) {
        this.header = (value == null) ? Collections.emptyList() : Collections.unmodifiableList(value);
        return this;
    }

    /**
     * @param value The string header.
     * @return This object, for chaining.
     * @deprecated use setHeader() instead
     */
    @NotNull
    @Deprecated
    public ConfigurationOptions header(String value) {
        this.header = (value == null) ? Collections.emptyList() : Collections.unmodifiableList(Arrays.asList(value.split("\\n")));
        return this;
    }

    /**
     * Gets the footer that will be applied to the bottom of the saved output.
     * <p>
     * This footer will be commented out and applied directly at the bottom of
     * the generated output of the {@link FileConfiguration}. It is not required
     * to include a newline at the beginning of the footer as it will
     * automatically be applied, but you may include one if you wish for extra
     * spacing.
     * <p>
     * If no comments exist, an empty list will be returned. A null entry
     * represents an empty line and an empty String represents an empty comment
     * line.
     *
     * @return Unmodifiable footer, every entry represents one line.
     */
    @NotNull
    public List<String> getFooter() {
        return footer;
    }

    /**
     * Sets the footer that will be applied to the bottom of the saved output.
     * <p>
     * This footer will be commented out and applied directly at the bottom of
     * the generated output of the {@link FileConfiguration}. It is not required
     * to include a newline at the beginning of the footer as it will
     * automatically be applied, but you may include one if you wish for extra
     * spacing.
     * <p>
     * If no comments exist, an empty list will be returned. A null entry
     * represents an empty line and an empty String represents an empty comment
     * line.
     *
     * @param value New footer, every entry represents one line.
     * @return This object, for chaining
     */
    @NotNull
    public ConfigurationOptions setFooter(List<String> value) {
        this.footer = (value == null) ? Collections.emptyList() : Collections.unmodifiableList(value);
        return this;
    }

    /**
     * Gets whether or not comments should be loaded and saved.
     * <p>
     * Defaults to true.
     *
     * @return Whether or not comments are parsed.
     */
    public boolean parseComments() {
        return parseComments;
    }

    /**
     * Sets whether or not comments should be loaded and saved.
     * <p>
     * Defaults to true.
     *
     * @param value Whether or not comments are parsed.
     * @return This object, for chaining
     */
    @NotNull
    public ConfigurationOptions parseComments(boolean value) {
        parseComments = value;
        return this;
    }

    /**
     * @return Whether or not comments are parsed.
     * @deprecated Call {@link #parseComments()} instead.
     */
    @Deprecated
    public boolean copyHeader() {
        return parseComments;
    }

    /**
     * @param value Should comments be parsed.
     * @return This object, for chaining
     * @deprecated Call {@link #parseComments(boolean)} instead.
     */
    @NotNull
    @Deprecated
    public ConfigurationOptions copyHeader(boolean value) {
        parseComments = value;
        return this;
    }

    public int indent() {
        return indent;
    }

    /**
     * Sets how much spaces should be used to indent each line.
     * <p>
     * The minimum value this may be is 2, and the maximum is 9.
     *
     * @param value New indent
     * @return This object, for chaining
     */
    @NotNull
    public ConfigurationOptions indent(int value) {
        Preconditions.checkArgument(value >= 2, "Indent must be at least 2 characters");
        Preconditions.checkArgument(value <= 9, "Indent cannot be greater than 9 characters");

        this.indent = value;
        return this;
    }

    /**
     * Gets how long a line can be, before it gets split.
     *
     * @return How the max line width
     */
    public int width() {
        return width;
    }

    /**
     * Sets how long a line can be, before it gets split.
     *
     * @param value New width
     * @return This object, for chaining
     */
    @NotNull
    public ConfigurationOptions width(int value) {
        this.width = value;
        return this;
    }

    // Paper start

    /**
     * Gets the maximum code point limit, that being, the maximum length of the document
     * in which the loader will read
     *
     * @return The current value
     */
    public int codePointLimit() {
        return codePointLimit;
    }

    /**
     * Sets the maximum code point limit, that being, the maximum length of the document
     * in which the loader will read
     *
     * @param codePointLimit new codepoint limit
     * @return This object, for chaining
     */
    @NotNull
    public ConfigurationOptions codePointLimit(int codePointLimit) {
        this.codePointLimit = codePointLimit;
        return this;
    }
}