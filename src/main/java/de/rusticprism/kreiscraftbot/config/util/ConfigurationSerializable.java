package de.rusticprism.kreiscraftbot.config.util;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface ConfigurationSerializable {

    /**
     * Creates a Map representation of this class.
     * <p>
     * This class must provide a method to restore this class, as defined in
     * the {@link ConfigurationSerializable} interface javadocs.
     *
     * @return Map containing the current state of this class
     */
    @NotNull
    Map<String, Object> serialize();
}
