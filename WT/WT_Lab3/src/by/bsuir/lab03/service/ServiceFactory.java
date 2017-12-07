package by.bsuir.lab03.service;

import by.bsuir.lab03.service.impl.ApartmentServiceImpl;
import by.bsuir.lab03.service.impl.AuthorizationServiceImpl;
import by.bsuir.lab03.service.impl.LocalizationServiceImpl;
import by.bsuir.lab03.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory factory = new ServiceFactory();

    private final LocalizationService localizationService = new LocalizationServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private final AuthorizationService authorizationService = new AuthorizationServiceImpl();
    private final ApartmentService apartmentService = new ApartmentServiceImpl();

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return factory;
    }

    public LocalizationService getLocalizationService() {
        return localizationService;
    }

    public UserService getUserService() {
        return userService;
    }

    public AuthorizationService getAuthorizationService() {
        return authorizationService;
    }

    public ApartmentService getApartmentService() {
        return apartmentService;
    }
}
