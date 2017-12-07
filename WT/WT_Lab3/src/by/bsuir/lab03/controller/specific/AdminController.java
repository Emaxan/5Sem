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

public class AdminController {
    private final String APARTMENT_ID_PARAM = "id";
    private final String HOTEL_PARAM = "hotel";
    private final String ROOM_PARAM = "room";

    private AuthorizationService authorizationService;
    private LocalizationService localizationService;

    public AdminController() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        authorizationService = serviceFactory.getAuthorizationService();
        localizationService = serviceFactory.getLocalizationService();
    }

    public void adminPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher;
        try {
            authorizationService.checkRole(request, response, UserRole.ADMIN,
                    FrontController.ACCESS_DENIED_REDIRECT_PATH);
            localizationService.setPageLanguage(request);
            request.setAttribute("email", authorizationService.getCurrentUserEmail(request));
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/admin/admin.jsp");
            dispatcher.forward(request, response);
        } catch (AccessDeniedException e) {}
    }

    public void apartmentsListPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher;
        ArrayList<Apartment> apartments;
        ApartmentService apartmentService = ServiceFactory.getInstance().getApartmentService();
        try {
            authorizationService.checkRole(request, response, UserRole.ADMIN,
                    FrontController.ACCESS_DENIED_REDIRECT_PATH);
            localizationService.setPageLanguage(request);
            apartments = apartmentService.getAllApartments();
            request.setAttribute("apartments", apartments);
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/admin/apartments-list.jsp");
            dispatcher.forward(request, response);
        } catch (AccessDeniedException e) {
        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    public void addApartmentPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            authorizationService.checkRole(request, response, UserRole.ADMIN,
                    FrontController.ACCESS_DENIED_REDIRECT_PATH);
            localizationService.setPageLanguage(request);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/admin/add-apartment.jsp");
            dispatcher.forward(request, response);
        } catch (AccessDeniedException e) {}
    }

    public void deleteApartmentAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int apartmentId;
        String param;
        RequestDispatcher dispatcher;
        ApartmentService apartmentService = ServiceFactory.getInstance().getApartmentService();
        try {
            authorizationService.checkRole(request, response, UserRole.ADMIN,
                    FrontController.ACCESS_DENIED_REDIRECT_PATH);
            param = request.getParameter(APARTMENT_ID_PARAM);
            param = param == null ? "" : param;
            apartmentId = Integer.parseInt(param);
            apartmentService.removeApartment(apartmentId);
            response.sendRedirect("/?" + FrontController.PAGE_PARAM + "=" + FrontController.APARTMENTS_LIST_PAGE);
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

    public void addApartmentAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher;
        ApartmentService apartmentService = ServiceFactory.getInstance().getApartmentService();
        String hotel;
        String room;
        try {
            authorizationService.checkRole(request, response, UserRole.ADMIN,
                    FrontController.ACCESS_DENIED_REDIRECT_PATH);
            hotel = request.getParameter(HOTEL_PARAM);
            room = request.getParameter(ROOM_PARAM);
            apartmentService.addApartment(new Apartment(0, hotel, room, 0));
            response.sendRedirect("/?" + FrontController.PAGE_PARAM + "=" + FrontController.APARTMENTS_LIST_PAGE);
        } catch (AccessDeniedException e) {
        } catch (ServiceException e) {
            localizationService.setPageLanguage(request);
            request.setAttribute("error", e.getMessage());
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}
