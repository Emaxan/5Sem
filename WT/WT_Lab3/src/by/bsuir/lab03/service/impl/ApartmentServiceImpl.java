package by.bsuir.lab03.service.impl;

import by.bsuir.lab03.dao.ApartmentDAO;
import by.bsuir.lab03.dao.DAOFactory;
import by.bsuir.lab03.dao.exception.DAOApartmentNotExistsException;
import by.bsuir.lab03.dao.exception.DAOConnectionException;
import by.bsuir.lab03.dao.exception.DAOException;
import by.bsuir.lab03.dao.exception.DAOQueryException;
import by.bsuir.lab03.domain.Apartment;
import by.bsuir.lab03.service.ApartmentService;
import by.bsuir.lab03.service.exception.ServiceException;
import by.bsuir.lab03.service.util.Errors;

import javax.servlet.annotation.ServletSecurity;
import java.util.ArrayList;

public class ApartmentServiceImpl implements ApartmentService {
    @Override
    public ArrayList<Apartment> getAllApartments() throws ServiceException {
        ApartmentDAO apartmentDAO = DAOFactory.getInstance().getApartmentDAO();
        ArrayList<Apartment> apartments;
        try {
            apartments = apartmentDAO.getAllApartments();
            if (apartments.size() == 0) {
                throw new ServiceException(Errors.NO_APARTMENTS);
            }
        } catch (DAOConnectionException e) {
            throw new ServiceException(Errors.DB_CONNECTION_ERROR);
        } catch (DAOQueryException e) {
            throw new ServiceException(Errors.DB_QUERY_ERROR);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return apartments;
    }

    @Override
    public ArrayList<Apartment> getBookedByClientApartments(int clientId) throws ServiceException {
        ApartmentDAO apartmentDAO = DAOFactory.getInstance().getApartmentDAO();
        ArrayList<Apartment> apartments;
        try {
            apartments = apartmentDAO.getBookedApartments(clientId);
            if (apartments.size() == 0) {
                throw new ServiceException(Errors.NO_BOOKED_APARTMENTS);
            }
        } catch (DAOConnectionException e) {
            throw new ServiceException(Errors.DB_CONNECTION_ERROR);
        } catch (DAOQueryException e) {
            throw new ServiceException(Errors.DB_QUERY_ERROR);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return apartments;
    }

    @Override
    public ArrayList<Apartment> getAvailableApartments() throws ServiceException {
        ApartmentDAO apartmentDAO = DAOFactory.getInstance().getApartmentDAO();
        ArrayList<Apartment> apartments;
        try {
            apartments = apartmentDAO.getNotBookedApartments();
            if (apartments.size() == 0) {
                throw new ServiceException(Errors.NO_AVAILABLE_APARTMENTS);
            }
        } catch (DAOConnectionException e) {
            throw new ServiceException(Errors.DB_CONNECTION_ERROR);
        } catch (DAOQueryException e) {
            throw new ServiceException(Errors.DB_QUERY_ERROR);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return apartments;
    }

    @Override
    public void bookApartment(int apartmentId, int clientId) throws ServiceException {
        ApartmentDAO apartmentDAO = DAOFactory.getInstance().getApartmentDAO();
        try {
            apartmentDAO.setBooking(apartmentId, clientId);
        } catch (DAOConnectionException e) {
            throw new ServiceException(Errors.DB_CONNECTION_ERROR);
        } catch (DAOQueryException e) {
            throw new ServiceException(Errors.DB_QUERY_ERROR);
        } catch (DAOApartmentNotExistsException e) {
            throw new ServiceException(Errors.APARTMENT_NOT_EXISTS);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void cancelApartmentBooking(int apartmentId, int clientId) throws ServiceException {
        ApartmentDAO apartmentDAO = DAOFactory.getInstance().getApartmentDAO();
        try {
            apartmentDAO.removeBooking(apartmentId, clientId);
        } catch (DAOConnectionException e) {
            throw new ServiceException(Errors.DB_CONNECTION_ERROR);
        } catch (DAOQueryException e) {
            throw new ServiceException(Errors.DB_QUERY_ERROR);
        } catch (DAOApartmentNotExistsException e) {
            throw new ServiceException(Errors.APARTMENT_NOT_EXISTS);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void removeApartment(int apartmentId) throws ServiceException {
        ApartmentDAO apartmentDAO = DAOFactory.getInstance().getApartmentDAO();
        try {
            apartmentDAO.removeApartment(apartmentId);
        } catch (DAOConnectionException e) {
            throw new ServiceException(Errors.DB_CONNECTION_ERROR);
        } catch (DAOQueryException e) {
            throw new ServiceException(Errors.DB_QUERY_ERROR);
        } catch (DAOApartmentNotExistsException e) {
            throw new ServiceException(Errors.APARTMENT_NOT_EXISTS);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void addApartment(Apartment apartment) throws ServiceException {
        ApartmentDAO apartmentDAO = DAOFactory.getInstance().getApartmentDAO();
        if (!apartment.isValid()) {
            throw new ServiceException(Errors.INCORRECT_DATA);
        }
        try {
            apartmentDAO.addApartment(apartment);
        } catch (DAOConnectionException e) {
            throw new ServiceException(Errors.DB_CONNECTION_ERROR);
        } catch (DAOQueryException e) {
            throw new ServiceException(Errors.DB_QUERY_ERROR);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
