package by.bsuir.MY;

import by.bsuir.MY.domain.ServiceResponse;
import by.bsuir.MY.view.App;
import by.bsuir.MY.view.Controller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 * .
 */
public class MonoThreadClientHandler implements Runnable {

    /**
     * .
     */
    private static Socket clientDialog;
    /**
     * .
     */
    private Controller controller;

    /**
     * .
     *
     * @param client .
     * @param application .
     */
    public MonoThreadClientHandler(final Socket client, final App application) {
        MonoThreadClientHandler.clientDialog = client;
        controller = new Controller(application);
    }

    /**
     * .
     */
    @Override
    public void run() {

        try {
            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());

            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            while (!clientDialog.isClosed()) {
                String entry;
                try {
                    entry = in.readUTF();
                } catch (EOFException | SocketException e) {
                    Thread.sleep(3000);
                    break;
                }

                synchronized (System.out) {
                    System.out.println(Thread.currentThread().getId() + " READ from clientDialog message - " + entry);
                }
                ServiceResponse reply = controller.doAction(entry);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                String result = gson.toJson(reply);
                System.out.println(result);
                out.writeUTF(result);
                synchronized (System.out) {
                    System.out.println(Thread.currentThread().getId() + " Server Wrote message to clientDialog.");
                }
                out.flush();
            }
            in.close();
            out.close();

            clientDialog.close();
        } catch (IOException | InterruptedException e) {
            synchronized (System.out) {
                System.err.println(Thread.currentThread().getId() + " " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
