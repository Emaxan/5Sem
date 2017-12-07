package by.bsuir.lab03.service;

import by.bsuir.lab03.domain.User;
import by.bsuir.lab03.domain.UserRole;
import by.bsuir.lab03.service.exception.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthorizationService {
    void authorizeUser(HttpServletRequest request, User user);
    void deauthorizeUser(HttpServletRequest request);
    UserRole getCurrentUserRole(HttpServletRequest request);
    String getCurrentUserEmail(HttpServletRequest request);
    int getCurrentUserId(HttpServletRequest request);
    void checkRole(HttpServletRequest request, HttpServletResponse response,
            UserRole role, String redirectPath) throws IOException, AccessDeniedException;
}
