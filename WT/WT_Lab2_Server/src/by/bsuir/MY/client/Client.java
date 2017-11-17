package by.bsuir.MY.client;

import by.bsuir.MY.domain.File;
import by.bsuir.MY.domain.Interf.ServiceResponseCode;
import by.bsuir.MY.domain.ServiceResponse;
import by.bsuir.MY.domain.exception.WrongDataException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * .
 */
public class Client {

    /**
     * .
     */
    private static Socket socket;

    /**
     * .
     */
    public Client() {
        try {
            socket = new Socket("localhost", 3345);
            System.out.println(Thread.currentThread().getId() + " Client connected to socket");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * .
     */
    public void run() {

        BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));

        try (DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
             DataInputStream ois = new DataInputStream(socket.getInputStream())) {
            while (true) {
                while (!rd.ready()){}
                String command = rd.readLine();
                if (command.equals("quit")) {
                    break;
                }
                oos.writeUTF(command);
                oos.flush();

                Thread.sleep(10);
                try {
                    String in = ois.readUTF();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    ServiceResponse result = gson.fromJson(in, ServiceResponse.class);
                    String res = (String)result.getContent();
                    if (result.getCode() == ServiceResponseCode.Ok) {
                        System.out.println("\n");
                        if (res.startsWith("<Array>")) {
                            printArray(res);
                        } else {
                            printFile(res);
                        }
                        System.out.println("\n");
                    } else {
                        System.err.println(res);
                    }
//                    System.out.println(in);
                } catch (EOFException | SocketException e) {
                    System.err.println(e.getMessage());
                    Thread.sleep(5000);
                    continue;
                }
                Thread.sleep(5000);
            }
            rd.close();
        } catch (InterruptedException | IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * .
     *
     * @param res .
     */
    private void printFile(final String res) {
        File file = new File();
        try {
            file.fromString(res);
        } catch (WrongDataException e) {
            System.err.println("Wrong data.");
        }
        System.out.println(
                "Id: "
                + file.getId()
                + "\tFirstName: "
                + file.getFirstName()
                + "\tLastName: "
                + file.getLastName()
                + "\tSurname: "
                + file.getSurnameName()
                + "\tPhrase: "
                + file.getPhrase()
        );
    }

    /**
     * .
     *
     * @param res .
     */
    private void printArray(final String res) {
        String r = res.substring("<Array>".length(), res.length() - "</Array>".length());
        Pattern p = Pattern.compile("(<File>.*?</File>)");
        Matcher m = p.matcher(res);
        while (m.find()) {
            printFile(m.group(1));
        }
    }
}
