////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package by.bsuir.MY.view;

import by.bsuir.MY.MonoThreadClientHandler;

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
     * TODO.
     */
    private static ExecutorService executeIt = Executors.newFixedThreadPool(10);

    /**
     * Create new instance of {@link App}.
     */
    public App() {
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
                executeIt.execute(new MonoThreadClientHandler(client));
                System.out.println(Thread.currentThread().getId() + " Connection accepted.");
            }

            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
