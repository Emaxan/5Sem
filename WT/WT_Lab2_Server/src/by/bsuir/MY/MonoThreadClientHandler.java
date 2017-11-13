package by.bsuir.MY;

import by.bsuir.MY.view.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 * TODO.
 */
public class MonoThreadClientHandler implements Runnable {

    /**
     * TODO.
     */
    private static Socket clientDialog;
    /**
     * TODO.
     */
    private Controller controller = new Controller();

    /**
     * TODO.
     *
     * @param client TODO.
     */
    public MonoThreadClientHandler(final Socket client) {
        MonoThreadClientHandler.clientDialog = client;
    }

    /**
     * TODO.
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
//                if (entry.equalsIgnoreCase("quit")) {
//                    synchronized (System.out) {
//                        System.out.println(Thread.currentThread().getId() + " Client initialize connections suicide ...");
//                    }
//                    out.writeUTF("Server reply - " + entry + " - OK");
//                    Thread.sleep(3000);
//                    break;
//                }
                String reply = controller.doAction(entry);
                out.writeUTF(reply);
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
