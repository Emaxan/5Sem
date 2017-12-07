package by.bsuir.lab03.dao.impl.threadPoolDAO.apartmentDAO.operation;

import by.bsuir.lab03.dao.ApartmentDAO;
import by.bsuir.lab03.dao.exception.DAOException;
import by.bsuir.lab03.dao.impl.threadPoolDAO.OperationExecutor;
import by.bsuir.lab03.domain.Apartment;

import java.util.ArrayList;

public class GetNotBookedApartmentsOperation extends OperationExecutor {
    private ApartmentDAO apartmentDAO;
    private ArrayList<Apartment> result;

    public GetNotBookedApartmentsOperation(ApartmentDAO apartmentDAO) {
        this.apartmentDAO = apartmentDAO;
    }

    @Override
    public void run() {
        try {
            result = apartmentDAO.getNotBookedApartments();
        } catch (DAOException e) {
            setException(e);
        }
    }

    public ArrayList<Apartment> getResult() {
        return result;
    }
}
