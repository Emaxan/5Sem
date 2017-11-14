package by.bsuir.MY.domain.exception;

/**
 * TODO.
 */
public class ArgumentException extends RuntimeException {

    /**
     * TODO.
     *
     * @param message TODO.
     */
    public ArgumentException(final String message) {
        super(message);
    }

    /**
     * TODO.
     *
     * @param message TODO.
     * @param exception TODO.
     */
    public ArgumentException(final String message, final Throwable exception) {
        super(message, exception);
    }
}
