package by.bsuir.lab03.dao.exception;

public class DAOQueryException extends DAOException {
    private static final long serialVersionUID = 1L;

    public DAOQueryException(String message) {
        super(message);
    }

    public DAOQueryException(String message, Exception e) {
        super(message, e);
    }
}
