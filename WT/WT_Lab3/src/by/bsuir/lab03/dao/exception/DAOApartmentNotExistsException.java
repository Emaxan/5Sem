package by.bsuir.lab03.dao.exception;

public class DAOApartmentNotExistsException extends DAOException {
    private static final long serialVersionUID = 1L;

    public DAOApartmentNotExistsException(String message) {
        super(message);
    }

    public DAOApartmentNotExistsException(String message, Exception e) {
        super(message, e);
    }
}
