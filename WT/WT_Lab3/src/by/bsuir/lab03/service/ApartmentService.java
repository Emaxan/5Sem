package by.bsuir.lab03.service;

import by.bsuir.lab03.domain.Apartment;
import by.bsuir.lab03.service.exception.ServiceException;

import java.util.ArrayList;

public interface ApartmentService {
    ArrayList<Apartment> getAllApartments() throws ServiceException;
    ArrayList<Apartment> getBookedByClientApartments(int clientId) throws ServiceException;
    ArrayList<Apartment> getAvailableApartments() throws ServiceException;
    void bookApartment(int apartmentId, int clientId) throws ServiceException;
    void cancelApartmentBooking(int apartmentId, int clientId) throws ServiceException;
    void removeApartment(int apartmentId) throws ServiceException;
    void addApartment(Apartment apartment) throws ServiceException;
}
