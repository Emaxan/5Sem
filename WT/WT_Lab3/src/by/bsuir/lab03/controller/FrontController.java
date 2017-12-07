package by.bsuir.lab03.controller;

import by.bsuir.lab03.domain.UserRole;
import by.bsuir.lab03.service.AuthorizationService;
import by.bsuir.lab03.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {
    public static final String PAGE_PARAM = "page";
    private static final String ACTION_PARAM = "action";

    public static final String MAIN_PAGE = "main";
    public static final String SIGNIN_PAGE = "sign-in";
    public static final String SIGNUP_PAGE = "sign-up";
    public static final String SIGNOUT_PAGE = "sign-out";
    public static final String CLIENT_PAGE = "client";
    public static final String ADMIN_PAGE = "admin";
    public static final String AVAILABLE_APARTMENTS_PAGE = "available-apartments";
    public static final String BOOKED_APARTMENTS_PAGE = "booked-apartments";
    public static final String ADD_APARTMENT_PAGE = "add-apartment";
    public static final String APARTMENTS_LIST_PAGE = "apartments-list";

    private static final String SET_LANGUAGE_ACTION = "set-lang";
    private static final String SIGNUP_ACTION = "sign-up";
    private static final String SIGNIN_ACTION = "sign-in";
    private static final String BOOK_APARTMENT_ACTION = "book-apartment";
    private static final String CANCEL_BOOKING_ACTION = "cancel-booking";
    private static final String ADD_APARTMENT_ACTION = "add-apartment";
    private static final String DELETE_APARTMENT_ACTION = "delete-apartment";

    public static final String ACCESS_DENIED_REDIRECT_PATH = "/";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ControllerFactory factory = ControllerFactory.getInstance();
        String action = request.getParameter(ACTION_PARAM);

        action = action == null ? "" : action;
        switch (action) {
            case SET_LANGUAGE_ACTION:
                factory.getGuestController().setLanguageAction(request, response);
                break;
            case SIGNUP_ACTION:
                factory.getGuestController().signUpAction(request, response);
                break;
            case SIGNIN_ACTION:
                factory.getGuestController().signInAction(request, response);
                break;
            case BOOK_APARTMENT_ACTION:
                factory.getClientController().bookApartmentAction(request, response);
                break;
            case CANCEL_BOOKING_ACTION:
                factory.getClientController().cancelApartmentBookingAction(request, response);
                break;
            case DELETE_APARTMENT_ACTION:
                factory.getAdminController().deleteApartmentAction(request, response);
                break;
            case ADD_APARTMENT_ACTION:
                factory.getAdminController().addApartmentAction(request, response);
                break;
            default:
                response.sendRedirect("/");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ControllerFactory factory = ControllerFactory.getInstance();
        String page = request.getParameter(PAGE_PARAM);

        page = page == null ? MAIN_PAGE : page;
        switch (page) {
            case SIGNIN_PAGE:
                factory.getGuestController().signInPage(request, response);
                break;
            case SIGNUP_PAGE:
                factory.getGuestController().signUpPage(request, response);
                break;
            case SIGNOUT_PAGE:
                factory.getGuestController().signOutAction(request, response);
                break;
            case CLIENT_PAGE:
                factory.getClientController().clientPage(request, response);
                break;
            case ADMIN_PAGE:
                factory.getAdminController().adminPage(request, response);
                break;
            case BOOKED_APARTMENTS_PAGE:
                factory.getClientController().bookedApartmentsPage(request, response);
                break;
            case AVAILABLE_APARTMENTS_PAGE:
                factory.getClientController().availableApartmentsPage(request, response);
                break;
            case APARTMENTS_LIST_PAGE:
                factory.getAdminController().apartmentsListPage(request, response);
                break;
            case ADD_APARTMENT_PAGE:
                factory.getAdminController().addApartmentPage(request, response);
                break;
            default:
                getMainPage(request, response);
        }
    }

    private void getMainPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AuthorizationService authorizationService = ServiceFactory.getInstance().getAuthorizationService();
        ControllerFactory factory = ControllerFactory.getInstance();
        UserRole role = authorizationService.getCurrentUserRole(request);
        switch (role) {
            case GUEST:
                factory.getGuestController().signInPage(request, response);
                break;
            case CLIENT:
                factory.getClientController().clientPage(request, response);
                break;
            case ADMIN:
                factory.getAdminController().adminPage(request, response);
                break;
            default:
                factory.getGuestController().signInPage(request, response);
        }
    }
}
