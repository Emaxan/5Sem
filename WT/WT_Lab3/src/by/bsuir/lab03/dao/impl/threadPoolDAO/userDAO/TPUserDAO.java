package by.bsuir.lab03.dao.impl.threadPoolDAO.userDAO;

import by.bsuir.lab03.dao.UserDAO;
import by.bsuir.lab03.dao.exception.DAOException;
import by.bsuir.lab03.dao.impl.threadPoolDAO.ThreadPoolDAO;
import by.bsuir.lab03.dao.impl.threadPoolDAO.userDAO.operation.AddUserOperation;
import by.bsuir.lab03.dao.impl.threadPoolDAO.userDAO.operation.GetUserByEmailOperation;
import by.bsuir.lab03.domain.User;

public class TPUserDAO extends ThreadPoolDAO implements UserDAO {
    private UserDAO userDAO;

    public TPUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void addUser(User user) throws DAOException {
        AddUserOperation operation = new AddUserOperation(userDAO, user);
        execute(operation);
    }

    @Override
    public User getUserByEmail(String email) throws DAOException {
        GetUserByEmailOperation operation = new GetUserByEmailOperation(userDAO, email);
        execute(operation);
        return operation.getResult();
    }
}
