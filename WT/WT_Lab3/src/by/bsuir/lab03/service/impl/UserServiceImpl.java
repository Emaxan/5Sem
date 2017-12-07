package by.bsuir.lab03.service.impl;

import by.bsuir.lab03.dao.DAOFactory;
import by.bsuir.lab03.dao.UserDAO;
import by.bsuir.lab03.dao.exception.DAOConnectionException;
import by.bsuir.lab03.dao.exception.DAOException;
import by.bsuir.lab03.dao.exception.DAOQueryException;
import by.bsuir.lab03.dao.exception.DAOUserExistsException;
import by.bsuir.lab03.domain.User;
import by.bsuir.lab03.service.AuthorizationService;
import by.bsuir.lab03.service.ServiceFactory;
import by.bsuir.lab03.service.UserService;
import by.bsuir.lab03.service.exception.ServiceException;
import by.bsuir.lab03.service.util.Errors;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserServiceImpl implements UserService {
    @Override
    public void signUp(User user) throws ServiceException {
        String plainPassword;
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        if (!user.isValid()) {
            throw new ServiceException(Errors.INCORRECT_DATA);
        }
        plainPassword = user.getPassword();
        user.setPassword(getPasswordMD5(plainPassword));
        try {
            userDAO.addUser(user);
        } catch (DAOConnectionException e) {
            throw new ServiceException(Errors.DB_CONNECTION_ERROR);
        } catch (DAOQueryException e) {
            throw new ServiceException(Errors.DB_QUERY_ERROR);
        } catch (DAOUserExistsException e) {
            throw new ServiceException(Errors.USER_EXISTS_ERROR);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User signIn(User user) throws ServiceException {
        User dbUser = null;
        String passwordHash;
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        if (!user.isValid()) {
            throw new ServiceException(Errors.INCORRECT_DATA);
        }
        passwordHash = getPasswordMD5(user.getPassword());
        try {
            dbUser = userDAO.getUserByEmail(user.getEmail());
            if (dbUser == null) {
                throw new ServiceException(Errors.USER_NOT_EXISTS_ERROR);
            }
            if (!dbUser.getPassword().equals(passwordHash)) {
                throw new ServiceException(Errors.INCORRECT_PASSWORD);
            }
        } catch (DAOConnectionException e) {
            throw new ServiceException(Errors.DB_CONNECTION_ERROR);
        } catch (DAOQueryException e) {
            throw new ServiceException(Errors.DB_QUERY_ERROR);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return dbUser;
    }

    private String getPasswordMD5(String plainPassword)
    {
        String result;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(plainPassword.getBytes());
            byte[] digest = md5.digest();
            result = DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException ex) {
            result = plainPassword;
        }
        return result;
    }
}
