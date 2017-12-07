package by.bsuir.lab03.dao.exception;

public class DAOUserExistsException extends DAOException {
    private static final long serialVersionUID = 1L;

    public DAOUserExistsException(String message) {
        super(message);
    }

    public DAOUserExistsException(String message, Exception e) {
        super(message, e);
    }
}
