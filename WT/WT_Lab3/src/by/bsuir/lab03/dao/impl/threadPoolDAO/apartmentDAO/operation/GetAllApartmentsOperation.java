package by.bsuir.lab03.dao.impl.threadPoolDAO.apartmentDAO.operation;

import by.bsuir.lab03.dao.ApartmentDAO;
import by.bsuir.lab03.dao.exception.DAOException;
import by.bsuir.lab03.dao.impl.threadPoolDAO.OperationExecutor;
import by.bsuir.lab03.domain.Apartment;

import java.util.ArrayList;

public class GetAllApartmentsOperation extends OperationExecutor {
    private ApartmentDAO apartmentDAO;
    private ArrayList<Apartment> result;

    public GetAllApartmentsOperation(ApartmentDAO apartmentDAO) {
        this.apartmentDAO = apartmentDAO;
    }

    @Override
    public void run() {
        try {
            result = apartmentDAO.getAllApartments();
        } catch (DAOException e) {
            setException(e);
        }
    }

    public ArrayList<Apartment> getResult() {
        return result;
    }
}
