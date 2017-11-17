////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package by.bsuir.MY.view;

import by.bsuir.MY.MonoThreadClientHandler;
import by.bsuir.MY.dal.dbcontext.DBContext;
import by.bsuir.MY.dal.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Application class. Contain main functionality.
 */
public class App {
    /**
     * User data.
     */
    private User user;
    /**
     * Database context.
     */
    private DBContext ctx;
    /**
     * .
     */
    private static ExecutorService executeIt = Executors.newFixedThreadPool(10);

    /**
     * Create new instance of {@link App}.
     *
     * @param context .
     */
    public App(final DBContext context) {
        ctx = context;
    }

    /**
     * .
     *
     * @return .
     */
    public DBContext getCtx() {
        return ctx;
    }

    /**
     * Get user data.
     *
     * @return User data.
     */
    public User getUser() {
        return user;
    }

    /**
     * Set user data.
     *
     * @param us User data.
     */
    public void setUser(final User us) {
        this.user = us;
    }

    /**
     * Start App.
     */
    public void start() {
        try (ServerSocket server = new ServerSocket(3345);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println(Thread.currentThread().getId() + " Server socket created, command console reader for listen to server commands");

            while (!server.isClosed()) {

                if (br.ready()) {
                    System.out.println(Thread.currentThread().getId() + " Main Server found any messages in channel, let's look at them.");

                    String serverCommand = br.readLine();
                    if (serverCommand.equalsIgnoreCase("quit")) {
                        System.out.println(Thread.currentThread().getId() + " Main Server initiate exiting...");
                        server.close();
                        break;
                    }
                }

                Socket client = server.accept();
                executeIt.execute(new MonoThreadClientHandler(client, this));
                System.out.println(Thread.currentThread().getId() + " Connection accepted.");
            }

            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
