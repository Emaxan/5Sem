package by.bsuir.lab03.dao.impl.threadPoolDAO.userDAO.operation;

import by.bsuir.lab03.dao.UserDAO;
import by.bsuir.lab03.dao.exception.DAOException;
import by.bsuir.lab03.dao.impl.threadPoolDAO.OperationExecutor;
import by.bsuir.lab03.domain.User;

public class AddUserOperation extends OperationExecutor {
    private UserDAO userDAO;
    private User user;

    public AddUserOperation(UserDAO userDAO, User user) {
        this.userDAO = userDAO;
        this.user = user;
    }

    @Override
    public void run() {
        try {
            userDAO.addUser(user);
        } catch (DAOException e) {
            setException(e);
        }
    }
}
