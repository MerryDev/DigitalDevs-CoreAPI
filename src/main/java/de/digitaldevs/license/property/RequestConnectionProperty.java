package de.digitaldevs.license.property;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
@AllArgsConstructor
public class RequestConnectionProperty {

    public static final String LICENSE_ID_PROPERTY = "LizenzID";
    public static final String LICENSE_SERVER_NAME_PROPERTY = "ServerName";
    public static final String LICENSE_PLUGIN_PROPERTY = "Plugin";

    @Getter private final Protocol protocol;
    @Getter private final String urlHost;

}
