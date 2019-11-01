package se.clouds.app.javanet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private static Socket socket;
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        try {

            int port = 25000;
            serverSocket = new ServerSocket(port);
            System.out.println("Server Started and listening to the port 25000");

            while (true) {
                socket = serverSocket.accept(); //blocking
                var is = socket.getInputStream();
                var isr = new InputStreamReader(is);
                var br = new BufferedReader(isr);

                var os = socket.getOutputStream();
                var osw = new OutputStreamWriter(os);
                var bw = new BufferedWriter(osw);

                String number = br.readLine();
                System.out.println("Message received from client is " + number);
                String returnMessage = "Hello from Server";
                String sendreturnMessage = returnMessage + "\n";

                bw.write(sendreturnMessage);
                bw.flush();
                System.out.println("Message sent to the client is " + returnMessage);
                System.out.println("\nProcess Complete\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                socket.close();
            } catch (Exception e) {
            }
        }
    }
}