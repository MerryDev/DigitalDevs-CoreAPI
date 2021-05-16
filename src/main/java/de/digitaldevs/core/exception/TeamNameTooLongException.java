package de.digitaldevs.core.exception;

import de.digitaldevs.core.scoreboard.AbstractScoreboard;

/**
 * This exception is thrown when the name of an given team is longer than 16 characters.
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 * @see RuntimeException
 * @see AbstractScoreboard
 */
public class TeamNameTooLongException extends RuntimeException {

    public TeamNameTooLongException(String message) {
        super(message);
    }

    public TeamNameTooLongException(String message, Throwable cause) {
        super(message, cause);
    }

    public TeamNameTooLongException(Throwable cause) {
        super(cause);
    }

    protected TeamNameTooLongException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
