package by.bsuir.lab03.service.impl;

import by.bsuir.lab03.domain.User;
import by.bsuir.lab03.domain.UserRole;
import by.bsuir.lab03.service.AuthorizationService;
import by.bsuir.lab03.service.ServiceFactory;
import by.bsuir.lab03.service.exception.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationServiceImpl implements AuthorizationService {
    private final String ATTRIBUTE_NAME = "user";

    @Override
    public void authorizeUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(true);
        session.setAttribute(ATTRIBUTE_NAME, user);
    }

    @Override
    public void deauthorizeUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    @Override
    public UserRole getCurrentUserRole(HttpServletRequest request) {
        User user = getUserObjectFromSession(request);
        if (user == null) {
            return UserRole.GUEST;
        } else {
            return user.getRole();
        }
    }

    @Override
    public String getCurrentUserEmail(HttpServletRequest request) {
        User user = getUserObjectFromSession(request);
        if (user == null) {
            return null;
        } else {
            return user.getEmail();
        }
    }

    @Override
    public int getCurrentUserId(HttpServletRequest request) {
        User user = getUserObjectFromSession(request);
        if (user == null) {
            return -1;
        } else {
            return user.getId();
        }
    }

    @Override
    public void checkRole(HttpServletRequest request, HttpServletResponse response,
            UserRole role, String redirectPath) throws IOException, AccessDeniedException {
        AuthorizationService authorizationService = ServiceFactory.getInstance().getAuthorizationService();
        if (authorizationService.getCurrentUserRole(request) != role) {
            response.sendRedirect(redirectPath);
            throw new AccessDeniedException("");
        }
    }

    private User getUserObjectFromSession(HttpServletRequest request) {
        Object attribute;
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        attribute = session.getAttribute(ATTRIBUTE_NAME);
        if ((attribute != null) && (attribute instanceof User)) {
            return (User)attribute;
        } else {
            return null;
        }
    }
}
