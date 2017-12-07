package by.bsuir.lab03.dao;

import by.bsuir.lab03.dao.exception.DAOException;
import by.bsuir.lab03.domain.Apartment;

import java.util.ArrayList;

public interface ApartmentDAO {
    ArrayList<Apartment> getAllApartments() throws DAOException;
    ArrayList<Apartment> getBookedApartments(int clientId) throws DAOException;
    ArrayList<Apartment> getNotBookedApartments() throws DAOException;
    void setBooking(int apartmentId, int clientId) throws DAOException;
    void removeBooking(int apartmentId, int clientId) throws DAOException;
    void removeApartment(int apartmentId) throws DAOException;
    void addApartment(Apartment apartment) throws DAOException;
}
