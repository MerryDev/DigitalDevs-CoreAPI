package de.digitaldevs.license.bungee;

import de.digitaldevs.license.AbstractLicense;
import de.digitaldevs.license.property.RequestConnectionProperty;
import lombok.SneakyThrows;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

/**
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public class BungeeLicense extends AbstractLicense {

    private Configuration configuration;

    public BungeeLicense(String licenseName, RequestConnectionProperty requestConnection) {
        super(licenseName, requestConnection);
        this.createConfig();
        this.value = this.configuration.getString(RequestConnectionProperty.LICENSE_ID_PROPERTY);
        this.serverName = this.configuration.getString(RequestConnectionProperty.LICENSE_SERVER_NAME_PROPERTY).replace("-", "%2D").replace(".", "%2E");
        this.plugin = this.configuration.getString(RequestConnectionProperty.LICENSE_PLUGIN_PROPERTY).replace(" ", "%20").replace("-", "%2D");
        this.requestValidity();
    }

    @Override
    public void validate() {
        switch (this.state) {
            case 1: {
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§6========================================"));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(""));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§aDeine Lizenz wurde bestaetigt."));
                this.sendLicenseDataInfo();
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(""));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§6========================================"));
                break;
            }
            case 2: {
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§6========================================"));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(""));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§cDie Lizenz ist ungueltig!"));
                this.sendLicenseDataInfo();
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(""));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§6========================================"));
                break;
            }
            case 3: {
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§6========================================"));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(""));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§aDie Lizenz wurde aufgehoben!"));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(""));
                this.sendLicenseDataInfo();
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(""));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§4Bitte kontaktiere uns unter der Email support@digitaldevs.de!"));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(""));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§6========================================"));
                break;
            }
            case 4: {
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§6========================================"));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(""));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§4Ein schwerwiegender Fehler ist aufgetreten!"));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§4Dieser dürfte normalerweise §4§lNICHT §r§4vorkommen!"));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§4Bitte kontaktiere uns unter der Email support@digitaldevs.de!"));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(""));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§6========================================"));
                break;
            }
        }
    }

    private void sendLicenseDataInfo() {
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(""));
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§7Server: §e" + this.serverName));
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§7Plugin: §e" + this.plugin));
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§7Lizenz: §e" + this.value));
        return;
    }

    @SneakyThrows
    private void createConfig() {
        this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);

        if (!this.file.exists() && this.file.createNewFile()) {
            this.configuration.set(RequestConnectionProperty.LICENSE_ID_PROPERTY, "id");
            this.configuration.set(RequestConnectionProperty.LICENSE_SERVER_NAME_PROPERTY, "example.com");
            this.configuration.set(RequestConnectionProperty.LICENSE_PLUGIN_PROPERTY, "foo");
            this.save();
        }
    }

    @SneakyThrows
    private void save() {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.configuration, this.file);
    }
}
