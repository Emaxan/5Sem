package by.bsuir.lab03.dao;

import by.bsuir.lab03.dao.exception.DAOException;
import by.bsuir.lab03.domain.User;

public interface UserDAO {
    void addUser(User user) throws DAOException;
    User getUserByEmail(String email) throws DAOException;
}
