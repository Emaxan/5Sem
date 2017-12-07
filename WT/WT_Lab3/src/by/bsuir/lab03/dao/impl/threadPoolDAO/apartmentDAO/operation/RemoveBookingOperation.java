package by.bsuir.lab03.dao.impl.threadPoolDAO.apartmentDAO.operation;

import by.bsuir.lab03.dao.ApartmentDAO;
import by.bsuir.lab03.dao.exception.DAOException;
import by.bsuir.lab03.dao.impl.threadPoolDAO.OperationExecutor;

public class RemoveBookingOperation extends OperationExecutor {
    private ApartmentDAO apartmentDAO;
    private int apartmentId;
    private int clientId;

    public RemoveBookingOperation(ApartmentDAO apartmentDAO, int apartmentId, int clientId) {
        this.apartmentDAO = apartmentDAO;
        this.apartmentId = apartmentId;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        try {
            apartmentDAO.removeBooking(apartmentId, clientId);
        } catch (DAOException e) {
            setException(e);
        }
    }
}
