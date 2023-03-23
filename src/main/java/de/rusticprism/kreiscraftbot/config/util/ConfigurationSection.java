package de.rusticprism.kreiscraftbot.config.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ConfigurationSection {

    @NotNull
    Set<String> getKeys(boolean deep);


    @NotNull
    Map<String, Object> getValues(boolean deep);


    boolean contains(@NotNull String path);


    boolean contains(@NotNull String path, boolean ignoreDefault);


    boolean isSet(@NotNull String path);


    String getCurrentPath();


    @NotNull
    String getName();

    ConfigurationSection getParent();

    Configuration getRoot();


    Object get(@NotNull String path);

    @Contract("_, !null -> !null")
    Object get(@NotNull String path, Object def);

    void set(@NotNull String path, Object value);


    @NotNull
    ConfigurationSection createSection(@NotNull String path);


    @NotNull
    ConfigurationSection createSection(@NotNull String path, @NotNull Map<?, ?> map);

    // Primitives


    String getString(@NotNull String path);


    @Contract("_, !null -> !null")
    String getString(@NotNull String path, String def);


    boolean isString(@NotNull String path);


    int getInt(@NotNull String path);


    int getInt(@NotNull String path, int def);


    boolean isInt(@NotNull String path);


    boolean getBoolean(@NotNull String path);


    boolean getBoolean(@NotNull String path, boolean def);


    boolean isBoolean(@NotNull String path);


    double getDouble(@NotNull String path);


    double getDouble(@NotNull String path, double def);


    boolean isDouble(@NotNull String path);


    long getLong(@NotNull String path);


    long getLong(@NotNull String path, long def);


    boolean isLong(@NotNull String path);

    // Java


    List<?> getList(@NotNull String path);


    @Contract("_, !null -> !null")
    List<?> getList(@NotNull String path, List<?> def);


    boolean isList(@NotNull String path);


    @NotNull
    List<String> getStringList(@NotNull String path);


    @NotNull
    List<Integer> getIntegerList(@NotNull String path);


    @NotNull
    List<Boolean> getBooleanList(@NotNull String path);


    @NotNull
    List<Double> getDoubleList(@NotNull String path);


    @NotNull
    List<Float> getFloatList(@NotNull String path);


    @NotNull
    List<Long> getLongList(@NotNull String path);


    @NotNull
    List<Byte> getByteList(@NotNull String path);


    @NotNull
    List<Character> getCharacterList(@NotNull String path);


    @NotNull
    List<Short> getShortList(@NotNull String path);


    @NotNull
    List<Map<?, ?>> getMapList(@NotNull String path);

    // Bukkit


    <T extends Object> T getObject(@NotNull String path, @NotNull Class<T> clazz);


    @Contract("_, _, !null -> !null")
    <T extends Object> T getObject(@NotNull String path, @NotNull Class<T> clazz, T def);


    <T extends ConfigurationSerializable> T getSerializable(@NotNull String path, @NotNull Class<T> clazz);


    @Contract("_, _, !null -> !null")
    <T extends ConfigurationSerializable> T getSerializable(@NotNull String path, @NotNull Class<T> clazz, T def);


    ConfigurationSection getConfigurationSection(@NotNull String path);


    boolean isConfigurationSection(@NotNull String path);


    ConfigurationSection getDefaultSection();


    void addDefault(@NotNull String path, Object value);


    @NotNull
    List<String> getComments(@NotNull String path);


    @NotNull
    List<String> getInlineComments(@NotNull String path);


    void setComments(@NotNull String path, List<String> comments);


    void setInlineComments(@NotNull String path, List<String> comments);
}