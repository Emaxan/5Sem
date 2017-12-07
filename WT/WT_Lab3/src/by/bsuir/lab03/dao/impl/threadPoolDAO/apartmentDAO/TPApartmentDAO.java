package by.bsuir.lab03.dao.impl.threadPoolDAO.apartmentDAO;

import by.bsuir.lab03.dao.ApartmentDAO;
import by.bsuir.lab03.dao.exception.DAOException;
import by.bsuir.lab03.dao.impl.threadPoolDAO.ThreadPoolDAO;
import by.bsuir.lab03.dao.impl.threadPoolDAO.apartmentDAO.operation.*;
import by.bsuir.lab03.domain.Apartment;

import java.util.ArrayList;

public class TPApartmentDAO extends ThreadPoolDAO implements ApartmentDAO {
    private ApartmentDAO apartmentDAO;

    public TPApartmentDAO(ApartmentDAO apartmentDAO) {
        this.apartmentDAO = apartmentDAO;
    }

    @Override
    public ArrayList<Apartment> getAllApartments() throws DAOException {
        GetAllApartmentsOperation operation = new GetAllApartmentsOperation(apartmentDAO);
        execute(operation);
        return operation.getResult();
    }

    @Override
    public ArrayList<Apartment> getBookedApartments(int clientId) throws DAOException {
        GetBookedApartmentsOperation operation = new GetBookedApartmentsOperation(apartmentDAO, clientId);
        execute(operation);
        return operation.getResult();
    }

    @Override
    public ArrayList<Apartment> getNotBookedApartments() throws DAOException {
        GetNotBookedApartmentsOperation operation = new GetNotBookedApartmentsOperation(apartmentDAO);
        execute(operation);
        return operation.getResult();
    }

    @Override
    public void setBooking(int apartmentId, int clientId) throws DAOException {
        SetBookingOperation operation = new SetBookingOperation(apartmentDAO, apartmentId, clientId);
        execute(operation);
    }

    @Override
    public void removeBooking(int apartmentId, int clientId) throws DAOException {
        RemoveBookingOperation operation = new RemoveBookingOperation(apartmentDAO, apartmentId, clientId);
        execute(operation);
    }

    @Override
    public void removeApartment(int apartmentId) throws DAOException {
        RemoveApartmentOperation operation = new RemoveApartmentOperation(apartmentDAO, apartmentId);
        execute(operation);
    }

    @Override
    public void addApartment(Apartment apartment) throws DAOException {
        AddApartmentOperation operation = new AddApartmentOperation(apartmentDAO, apartment);
        execute(operation);
    }
}
