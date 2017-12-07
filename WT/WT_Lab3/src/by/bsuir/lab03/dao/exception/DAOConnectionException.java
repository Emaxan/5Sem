package by.bsuir.lab03.dao.exception;

public class DAOConnectionException extends DAOException {
    private static final long serialVersionUID = 1L;

    public DAOConnectionException(String message) {
        super(message);
    }

    public DAOConnectionException(String message, Exception e) {
        super(message, e);
    }
}
