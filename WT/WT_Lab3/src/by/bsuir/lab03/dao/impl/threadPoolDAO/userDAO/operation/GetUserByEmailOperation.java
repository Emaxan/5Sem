package by.bsuir.lab03.dao.impl.threadPoolDAO.userDAO.operation;

import by.bsuir.lab03.dao.UserDAO;
import by.bsuir.lab03.dao.exception.DAOException;
import by.bsuir.lab03.dao.impl.threadPoolDAO.OperationExecutor;
import by.bsuir.lab03.domain.User;

public class GetUserByEmailOperation extends OperationExecutor {
    private UserDAO userDAO;
    private String email;
    private User result;

    public GetUserByEmailOperation(UserDAO userDAO, String email) {
        this.userDAO = userDAO;
        this.email = email;
    }

    @Override
    public void run() {
        try {
            result = userDAO.getUserByEmail(email);
        } catch (DAOException e) {
            setException(e);
        }
    }

    public User getResult() {
        return result;
    }
}
