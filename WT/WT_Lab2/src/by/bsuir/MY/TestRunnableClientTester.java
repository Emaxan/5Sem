package by.bsuir.MY;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 * TODO.
 */
public class TestRunnableClientTester implements Runnable {

    /**
     * TODO.
     */
    private static Socket socket;

    /**
     * TODO.
     */
    public TestRunnableClientTester() {
        try {
            socket = new Socket("localhost", 3345);
            System.out.println(Thread.currentThread().getId() + " Client connected to socket");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO.
     */
    @Override
    public void run() {

        try (
                DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
                DataInputStream ois = new DataInputStream(socket.getInputStream())) {
            System.out.println(Thread.currentThread().getId() + " Client oos & ois initialized");

            int i = 0;
            while (i < 3) {

                oos.writeUTF("clientCommand " + i);

                oos.flush();

                Thread.sleep(10);
                System.out.println(Thread.currentThread().getId() + " Client wrote & start waiting for data from server...");

                System.out.println(Thread.currentThread().getId() + " reading...");
                try {
                    String in = ois.readUTF();
                    System.out.println(Thread.currentThread().getId() + " " + in);
                    i++;
                } catch (EOFException | SocketException e) {
                    System.err.println(Thread.currentThread().getId() + " " + e.getMessage());
                    Thread.sleep(5000);
                    break;
                }
                Thread.sleep(5000);
            }
        } catch (InterruptedException | IOException e) {
            System.err.println(Thread.currentThread().getId() + " " + e.getMessage());
            e.printStackTrace();
        }
    }
}
