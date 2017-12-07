package by.bsuir.lab03.dao.impl.threadPoolDAO.apartmentDAO.operation;

import by.bsuir.lab03.dao.ApartmentDAO;
import by.bsuir.lab03.dao.exception.DAOException;
import by.bsuir.lab03.dao.impl.threadPoolDAO.OperationExecutor;
import by.bsuir.lab03.domain.Apartment;

public class AddApartmentOperation extends OperationExecutor {
    private ApartmentDAO apartmentDAO;
    private Apartment apartment;

    public AddApartmentOperation(ApartmentDAO apartmentDAO, Apartment apartment) {
        this.apartmentDAO = apartmentDAO;
        this.apartment = apartment;
    }

    @Override
    public void run() {
        try {
            apartmentDAO.addApartment(apartment);
        } catch (DAOException e) {
            setException(e);
        }
    }
}
