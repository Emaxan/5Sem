package by.bsuir.lab03.dao.impl.threadPoolDAO;

import by.bsuir.lab03.dao.exception.DAOException;

public abstract class OperationExecutor implements Runnable {
    private DAOException exception;

    @Override
    public abstract void run();

    protected void setException(DAOException e) {
        exception = e;
    }

    public DAOException getException() {
        return exception;
    }
}
