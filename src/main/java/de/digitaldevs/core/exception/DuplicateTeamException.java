package de.digitaldevs.core.exception;

import de.digitaldevs.core.scoreboard.AbstractScoreboard;

/**
 * This exception is thrown when an team already exists in an registered scoreboard.
 *
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 * @see RuntimeException
 * @see AbstractScoreboard
 */
public class DuplicateTeamException extends RuntimeException {

    public DuplicateTeamException(String message) {
        super(message);
    }

    public DuplicateTeamException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateTeamException(Throwable cause) {
        super(cause);
    }

    protected DuplicateTeamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
