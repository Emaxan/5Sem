package by.bsuir.lab03.dao.impl.threadPoolDAO.apartmentDAO.operation;

import by.bsuir.lab03.dao.ApartmentDAO;
import by.bsuir.lab03.dao.exception.DAOException;
import by.bsuir.lab03.dao.impl.threadPoolDAO.OperationExecutor;
import by.bsuir.lab03.domain.Apartment;

import java.util.ArrayList;

public class GetBookedApartmentsOperation extends OperationExecutor {
    private ApartmentDAO apartmentDAO;
    private int clientId;
    private ArrayList<Apartment> result;

    public GetBookedApartmentsOperation(ApartmentDAO apartmentDAO, int clientId) {
        this.apartmentDAO = apartmentDAO;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        try {
            result = apartmentDAO.getBookedApartments(clientId);
        } catch (DAOException e) {
            setException(e);
        }
    }

    public ArrayList<Apartment> getResult() {
        return result;
    }
}
