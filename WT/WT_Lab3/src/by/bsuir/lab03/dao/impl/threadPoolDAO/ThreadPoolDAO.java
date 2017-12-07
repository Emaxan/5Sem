package by.bsuir.lab03.dao.impl.threadPoolDAO;

import by.bsuir.lab03.dao.exception.DAOException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class ThreadPoolDAO {
    private final int THREADS_AMOUNT = 1;

    protected void execute(OperationExecutor operation) throws DAOException {
        ExecutorService pool = Executors.newFixedThreadPool(THREADS_AMOUNT);
        pool.execute(operation);
        waitForTaskFinished(pool);
        if (operation.getException() != null) {
            throw operation.getException();
        }
    }

    private void waitForTaskFinished(ExecutorService pool) {
        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {}
    }
}
