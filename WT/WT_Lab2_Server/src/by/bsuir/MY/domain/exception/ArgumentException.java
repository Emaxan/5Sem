package by.bsuir.MY.domain.exception;

/**
 * .
 */
public class ArgumentException extends RuntimeException {

    /**
     * .
     *
     * @param message .
     */
    public ArgumentException(final String message) {
        super(message);
    }

    /**
     * .
     *
     * @param message .
     * @param exception .
     */
    public ArgumentException(final String message, final Throwable exception) {
        super(message, exception);
    }
}
