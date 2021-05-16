package de.digitaldevs.license.bukkit;

import de.digitaldevs.license.AbstractLicense;
import de.digitaldevs.license.property.RequestConnectionProperty;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a license for non bungee-based plugins.
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public class BukkitLicense extends AbstractLicense {

    private FileConfiguration configuration;

    /**
     * Instantiates a new {@code License}. Creates a new file for managing the license data.
     *
     * @param licenseName The name of the Plugin. Cannot be null.
     * @param property    The connection property which contains the relevant data for the request-connection. Cannot be null
     */
    public BukkitLicense(@NotNull String licenseName, @NotNull RequestConnectionProperty property) {
        super(licenseName, property);
        this.createConfig();
        this.value = this.configuration.getString(RequestConnectionProperty.LICENSE_ID_PROPERTY);
        this.serverName = this.configuration.getString(RequestConnectionProperty.LICENSE_SERVER_NAME_PROPERTY).replace("-", "%2D").replace(".", "%2E");
        this.plugin = this.configuration.getString(RequestConnectionProperty.LICENSE_PLUGIN_PROPERTY).replace(" ", "%20").replace("-", "%2D");
        this.requestValidity();
    }

    /**
     * Sends a message whether the license was successfully validated or something went wrong.
     */
    @Override
    public void validate() {
        switch (this.state) {
            case 1: {
                Bukkit.getConsoleSender().sendMessage("§6========================================");
                Bukkit.getConsoleSender().sendMessage("");
                Bukkit.getConsoleSender().sendMessage("§aDeine Lizenz wurde bestaetigt.");
                Bukkit.getConsoleSender().sendMessage("");
                this.sendLicenseDataInfo();
                Bukkit.getConsoleSender().sendMessage("");
                Bukkit.getConsoleSender().sendMessage("§6========================================");
                break;
            }
            case 2: {
                Bukkit.getConsoleSender().sendMessage("§6========================================");
                Bukkit.getConsoleSender().sendMessage("");
                Bukkit.getConsoleSender().sendMessage("§cDie Lizenz ist ungueltig!");
                Bukkit.getConsoleSender().sendMessage("");
                this.sendLicenseDataInfo();
                Bukkit.getConsoleSender().sendMessage("");
                Bukkit.getConsoleSender().sendMessage("§4Bitte checke deine Angaben in der " + this.licenseName + ".yml!");
                Bukkit.getConsoleSender().sendMessage("");
                Bukkit.getConsoleSender().sendMessage("§6========================================");
                break;
            }
            case 3: {
                Bukkit.getConsoleSender().sendMessage("§6========================================");
                Bukkit.getConsoleSender().sendMessage("");
                Bukkit.getConsoleSender().sendMessage("§cDie Lizenz wurde aufgehoben!");
                Bukkit.getConsoleSender().sendMessage("");
                this.sendLicenseDataInfo();
                Bukkit.getConsoleSender().sendMessage("");
                Bukkit.getConsoleSender().sendMessage("§4Bitte kontaktiere uns unter der Email support@digitaldevs.de!");
                Bukkit.getConsoleSender().sendMessage("");
                Bukkit.getConsoleSender().sendMessage("§6========================================");
                break;
            }
            case 4: {
                Bukkit.getConsoleSender().sendMessage("§6========================================");
                Bukkit.getConsoleSender().sendMessage("");
                Bukkit.getConsoleSender().sendMessage("§4Ein schwerwiegender Fehler ist aufgetreten!");
                Bukkit.getConsoleSender().sendMessage("§4Dieser dürfte normalerweise §4§lNICHT §r§4vorkommen!");
                Bukkit.getConsoleSender().sendMessage("§4Bitte kontaktiere uns unter der Email support@digitaldevs.de!");
                Bukkit.getConsoleSender().sendMessage("");
                Bukkit.getConsoleSender().sendMessage("§6========================================");
                break;
            }
        }
    }

    private void sendLicenseDataInfo() {
        Bukkit.getConsoleSender().sendMessage("§7Server: §e" + this.serverName);
        Bukkit.getConsoleSender().sendMessage("§7Plugin: §e" + this.plugin);
        Bukkit.getConsoleSender().sendMessage("§7Lizenz: §e" + this.value);
    }

    @SneakyThrows
    private void createConfig() {
        this.configuration = YamlConfiguration.loadConfiguration(this.file);

        if (!this.file.exists()) {
            if (this.file.createNewFile()) {
                this.configuration.set(RequestConnectionProperty.LICENSE_ID_PROPERTY, "id");
                this.configuration.set(RequestConnectionProperty.LICENSE_SERVER_NAME_PROPERTY, "example.com");
                this.configuration.set(RequestConnectionProperty.LICENSE_PLUGIN_PROPERTY, "foo");
                this.save();
            }
        }
    }

    @SneakyThrows
    private void save() {
        this.configuration.save(this.file);
    }

}
