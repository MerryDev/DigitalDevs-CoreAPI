package de.digitaldevs.core.exception;

import de.digitaldevs.core.scoreboard.AbstractScoreboard;

/**
 * This exception is thrown when a given line of a scoreboard is longer than 64 characters.
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 * @see RuntimeException
 * @see AbstractScoreboard
 */
public class LineTooLongException extends RuntimeException {

    public LineTooLongException(String message) {
        super(message);
    }

    public LineTooLongException(String message, Throwable cause) {
        super(message, cause);
    }

    public LineTooLongException(Throwable cause) {
        super(cause);
    }

    protected LineTooLongException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
