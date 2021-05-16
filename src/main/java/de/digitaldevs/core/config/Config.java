package de.digitaldevs.core.config;

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
    Object get(String path);

    /**
     * Saves the config.
     */
    void save();

}
