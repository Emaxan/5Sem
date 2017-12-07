package by.bsuir.lab03.service;

import by.bsuir.lab03.domain.User;
import by.bsuir.lab03.service.exception.ServiceException;

public interface UserService {
    void signUp(User user) throws ServiceException;
    User signIn(User user) throws ServiceException;
}
