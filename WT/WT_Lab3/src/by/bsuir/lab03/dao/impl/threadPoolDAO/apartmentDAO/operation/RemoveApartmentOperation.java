package by.bsuir.lab03.dao.impl.threadPoolDAO.apartmentDAO.operation;

import by.bsuir.lab03.dao.ApartmentDAO;
import by.bsuir.lab03.dao.exception.DAOException;
import by.bsuir.lab03.dao.impl.threadPoolDAO.OperationExecutor;

public class RemoveApartmentOperation extends OperationExecutor {
    private ApartmentDAO apartmentDAO;
    private int apartmentId;

    public RemoveApartmentOperation(ApartmentDAO apartmentDAO, int apartmentId) {
        this.apartmentDAO = apartmentDAO;
        this.apartmentId = apartmentId;
    }

    @Override
    public void run() {
        try {
            apartmentDAO.removeApartment(apartmentId);
        } catch (DAOException e) {
            setException(e);
        }
    }
}
