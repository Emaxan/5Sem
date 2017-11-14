package by.bsuir.MY;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO.
 */
public final class Main {

    /**
     * TODO.
     */
    private Main() {
    }

    /**
     * TODO.
     */
    private static int clientCount = 2;

    /**
     * TODO.
     *
     * @param args TODO.
     * @throws IOException          TODO.
     * @throws InterruptedException TODO.
     */
    public static void main(final String[] args) throws IOException, InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(clientCount);
        int j = 0;

        while (j < clientCount) {
            j++;
            exec.execute(new TestRunnableClientTester());
            Thread.sleep(10);
        }

        exec.shutdown();
    }
}
