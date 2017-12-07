package by.bsuir.lab03.controller.specific;

import by.bsuir.lab03.controller.FrontController;
import by.bsuir.lab03.service.exception.AccessDeniedException;
import by.bsuir.lab03.domain.User;
import by.bsuir.lab03.service.AuthorizationService;
import by.bsuir.lab03.service.LocalizationService;
import by.bsuir.lab03.service.ServiceFactory;
import by.bsuir.lab03.domain.UserRole;
import by.bsuir.lab03.service.UserService;
import by.bsuir.lab03.service.exception.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

public class GuestController {
    private final String LANG_PARAM = "language";
    private final String EMAIL_PARAM = "email";
    private final String PASSWORD_PARAM = "password";

    private final String LANG_RUS = "rus";

    private AuthorizationService authorizationService;
    private LocalizationService localizationService;

    public GuestController() {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        authorizationService = serviceFactory.getAuthorizationService();
        localizationService = serviceFactory.getLocalizationService();
    }

    public void signInPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            authorizationService.checkRole(request, response, UserRole.GUEST,
                    FrontController.ACCESS_DENIED_REDIRECT_PATH);
            localizationService.setPageLanguage(request);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/guest/sign-in.jsp");
            dispatcher.forward(request, response);
        } catch (AccessDeniedException e) {}
    }

    public void signUpPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            authorizationService.checkRole(request, response, UserRole.GUEST,
                    FrontController.ACCESS_DENIED_REDIRECT_PATH);
            localizationService.setPageLanguage(request);
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/guest/sign-up.jsp");
            dispatcher.forward(request, response);
        } catch (AccessDeniedException e) {}
    }

    public void setLanguageAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String language = request.getParameter(LANG_PARAM);
        switch (language) {
            case LANG_RUS:
                localizationService.setRussianLanguage(response);
                break;
            default:
                localizationService.setEnglishLanguage(response);
        }
        response.sendRedirect("/?" + FrontController.PAGE_PARAM + "=" + FrontController.MAIN_PAGE);
    }

    public void signInAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user;
        RequestDispatcher dispatcher;
        UserService userService = ServiceFactory.getInstance().getUserService();
        String email = request.getParameter(EMAIL_PARAM);
        String password = request.getParameter(PASSWORD_PARAM);

        try {
            user = userService.signIn(new User(0, email, password, UserRole.GUEST));
            authorizationService.authorizeUser(request, user);
            response.sendRedirect("/?" + FrontController.PAGE_PARAM + "=" + FrontController.MAIN_PAGE);
        } catch (ServiceException e) {
            localizationService.setPageLanguage(request);
            request.setAttribute("error", e.getMessage());
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    public void signUpAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher;
        UserService userService = ServiceFactory.getInstance().getUserService();
        String email = request.getParameter(EMAIL_PARAM);
        String password = request.getParameter(PASSWORD_PARAM);

        localizationService.setPageLanguage(request);
        try {
            userService.signUp(new User(0, email, password, UserRole.CLIENT));
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/guest/sign-up-success.jsp");
            dispatcher.forward(request, response);
        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    public void signOutAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        authorizationService.deauthorizeUser(request);
        response.sendRedirect("/?" + FrontController.PAGE_PARAM + "=" + FrontController.MAIN_PAGE);
    }
}
