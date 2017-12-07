package by.bsuir.lab03.controller.specific;

import by.bsuir.lab03.controller.FrontController;
import by.bsuir.lab03.domain.Apartment;
import by.bsuir.lab03.service.ApartmentService;
import by.bsuir.lab03.service.LocalizationService;
import by.bsuir.lab03.service.exception.AccessDeniedException;
import by.bsuir.lab03.domain.UserRole;
import by.bsuir.lab03.service.AuthorizationService;
import by.bsuir.lab03.service.ServiceFactory;
import by.bsuir.lab03.service.exception.ServiceException;
import by.bsuir.lab03.service.util.Errors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ClientController {
    private final String APARTMENT_ID_PARAM = "id";

    private AuthorizationService authorizationService;
    private LocalizationService localizationService;

    public ClientController() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        authorizationService = serviceFactory.getAuthorizationService();
        localizationService = serviceFactory.getLocalizationService();
    }

    public void clientPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher;
        try {
            authorizationService.checkRole(request, response, UserRole.CLIENT,
                    FrontController.ACCESS_DENIED_REDIRECT_PATH);
            localizationService.setPageLanguage(request);
            request.setAttribute("email", authorizationService.getCurrentUserEmail(request));
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/client/client.jsp");
            dispatcher.forward(request, response);
        } catch (AccessDeniedException e) {}
    }

    public void bookedApartmentsPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int clientId;
        RequestDispatcher dispatcher;
        ArrayList<Apartment> apartments;
        ApartmentService apartmentService = ServiceFactory.getInstance().getApartmentService();
        try {
            authorizationService.checkRole(request, response, UserRole.CLIENT,
                    FrontController.ACCESS_DENIED_REDIRECT_PATH);
            localizationService.setPageLanguage(request);
            clientId = authorizationService.getCurrentUserId(request);
            apartments = apartmentService.getBookedByClientApartments(clientId);
            request.setAttribute("apartments", apartments);
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/client/booked-apartments.jsp");
            dispatcher.forward(request, response);
        } catch (AccessDeniedException e) {
        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    public void availableApartmentsPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher;
        ArrayList<Apartment> apartments;
        ApartmentService apartmentService = ServiceFactory.getInstance().getApartmentService();
        try {
            authorizationService.checkRole(request, response, UserRole.CLIENT,
                    FrontController.ACCESS_DENIED_REDIRECT_PATH);
            localizationService.setPageLanguage(request);
            apartments = apartmentService.getAvailableApartments();
            request.setAttribute("apartments", apartments);
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/client/available-apartments.jsp");
            dispatcher.forward(request, response);
        } catch (AccessDeniedException e) {
        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    public void bookApartmentAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int apartmentId;
        int clientId;
        String param;
        RequestDispatcher dispatcher;
        ApartmentService apartmentService = ServiceFactory.getInstance().getApartmentService();
        try {
            authorizationService.checkRole(request, response, UserRole.CLIENT,
                    FrontController.ACCESS_DENIED_REDIRECT_PATH);
            param = request.getParameter(APARTMENT_ID_PARAM);
            param = param == null ? "" : param;
            apartmentId = Integer.parseInt(param);
            clientId = authorizationService.getCurrentUserId(request);
            apartmentService.bookApartment(apartmentId, clientId);
            response.sendRedirect("/?" + FrontController.PAGE_PARAM + "=" + FrontController.BOOKED_APARTMENTS_PAGE);
        } catch (NumberFormatException e) {
            localizationService.setPageLanguage(request);
            request.setAttribute("error", Errors.APARTMENT_NOT_EXISTS);
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
            dispatcher.forward(request, response);
        } catch (AccessDeniedException e) {
        } catch (ServiceException e) {
            localizationService.setPageLanguage(request);
            request.setAttribute("error", e.getMessage());
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    public void cancelApartmentBookingAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int apartmentId;
        int clientId;
        String param;
        RequestDispatcher dispatcher;
        ApartmentService apartmentService = ServiceFactory.getInstance().getApartmentService();
        try {
            authorizationService.checkRole(request, response, UserRole.CLIENT,
                    FrontController.ACCESS_DENIED_REDIRECT_PATH);
            param = request.getParameter(APARTMENT_ID_PARAM);
            param = param == null ? "" : param;
            apartmentId = Integer.parseInt(param);
            clientId = authorizationService.getCurrentUserId(request);
            apartmentService.cancelApartmentBooking(apartmentId, clientId);
            response.sendRedirect("/?" + FrontController.PAGE_PARAM + "=" + FrontController.BOOKED_APARTMENTS_PAGE);
        } catch (NumberFormatException e) {
            localizationService.setPageLanguage(request);
            request.setAttribute("error", Errors.APARTMENT_NOT_EXISTS);
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
            dispatcher.forward(request, response);
        } catch (AccessDeniedException e) {
        } catch (ServiceException e) {
            localizationService.setPageLanguage(request);
            request.setAttribute("error", e.getMessage());
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}
