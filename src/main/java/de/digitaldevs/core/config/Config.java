package de.digitaldevs.core.config;

import org.jetbrains.annotations.NotNull;

/**
 * This class is the abstract base for all config files.
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public interface Config {

    /**
     * Creates a new config file.
     *
     * @return The new config
     */
    Config create();

    /**
     * Sets the default values for the new config.
     */
    void initDefault();

    /**
     * Gets the value at the specific path from the config.
     *
     * @param path The path from where the value should be fetched. Cannot be null.
     * @return The value at the path or {@code null} if no value was found or en empty value is set.
     */
    Object get(@NotNull String path);

    /**
     * Sets the value at the specific path.
     *
     * @param path The path where the value should be set. Cannot be null.
     * @param object The object which should be set. Use {@code null} to remove
     */
    void set(@NotNull String path, Object object);

    /**
     * Saves the config.
     */
    void save();

}
