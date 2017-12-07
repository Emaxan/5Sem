package by.bsuir.lab03.service.exception;

public class AccessDeniedException extends Exception {
    private static final long serialVersionUID = 1L;

    public AccessDeniedException(String message) {
            super(message);
        }

    public AccessDeniedException(String message, Exception e) {
            super(message, e);
        }
}
