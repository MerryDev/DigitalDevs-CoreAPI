package de.digitaldevs.license.property;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
@AllArgsConstructor
public enum Protocol {

    HTTP("http"),
    HTTPS("https");

    @Getter private final String key;

}
