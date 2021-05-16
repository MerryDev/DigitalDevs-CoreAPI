package de.digitaldevs.license;

import de.digitaldevs.license.property.RequestConnectionProperty;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * This class is an abstract base for all types of licenses.
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public abstract class AbstractLicense {

    /**
     * The name of the license. Defines the filename for a license.
     */
    protected final String licenseName;
    /**
     * The property containing the relevant data for a request-connection.
     */
    protected final RequestConnectionProperty property;
    /**
     * The id of the license. Makes a license unique.
     */
    protected String value;
    /**
     * The partner's name.
     */
    protected String serverName;
    /**
     * The type of the plugin the license is for.
     */
    protected String plugin;
    /**
     * The state whether the license is valid or not.
     */
    protected int state;
    /**
     * The file where the license data is stored in.
     */
    protected File file;

    /**
     * Instantiates a new {@code AbstractLicense}-object owning the relevant non-server-based data.
     *
     * @param licenseName The name of the license. Cannot be null.
     * @param property    The connection property containing the relevant data for the request connection. Cannot be null.
     */
    public AbstractLicense(@NotNull String licenseName, @NotNull RequestConnectionProperty property) {
        this.licenseName = licenseName;
        this.property = property;
        this.file = new File("plugins/DigitalDevs/licenses/" + this.licenseName + ".yml");
    }

    /**
     * Abstract base for server-based validation messages.
     */
    public abstract void validate();

    /**
     * Checks if the license is valid.
     *
     * @return {@code true} if the license is valid or {@code false} if not.
     */
    public boolean isValid() {
        return this.state == 1;
    }

    /**
     * Requests the validity from the session server and caches the result for further operations.
     *
     * @see SneakyThrows
     */
    @SneakyThrows
    protected void requestValidity() {
        URL url = new URL(this.property.getProtocol().getKey() + "://" + this.property.getUrlHost() + "/session/session.php?id=" + this.value + "&server=" + this.serverName + "&extra=" + this.plugin);
        URLConnection connection = url.openConnection();
        connection.setUseCaches(false);
        connection.setDefaultUseCaches(false);
        this.addRequestProperties(connection);

        String inputString = new Scanner(connection.getInputStream(), "UTF-8").useDelimiter("\\A").next();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(inputString);

        JSONArray jsonArray = (JSONArray) obj;
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);

        this.state = Integer.parseInt(String.valueOf(jsonObject.get("state")));
    }

    /**
     * Adds {@code RequestProperties} to the given {@code URLConnection}.
     *
     * @param connection The {@code URLConnection} to which the properties should be added.
     * @see URLConnection
     */
    private void addRequestProperties(URLConnection connection) {
        connection.addRequestProperty("User-Agent", "Mozilla/5.0");
        connection.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
        connection.addRequestProperty("Pragma", "no-cache");
    }
}
