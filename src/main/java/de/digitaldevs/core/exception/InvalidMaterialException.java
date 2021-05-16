package de.digitaldevs.core.exception;

import de.digitaldevs.core.builder.ArmorBuilder;

/**
 * This exception is thrown when a given material is not allowed for a specific action.
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 * @see RuntimeException
 * @see ArmorBuilder
 */
public class InvalidMaterialException extends RuntimeException {

    public InvalidMaterialException(String message) {
        super(message);
    }

    public InvalidMaterialException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMaterialException(Throwable cause) {
        super(cause);
    }

    protected InvalidMaterialException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
