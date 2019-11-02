package se.clouds.app.javanet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * SocketServer
 */
public class SocketClient {

    public static void main(String[] args) {
        Socket socket = null;
        try {
            var host = "localhost";
            var port = 25000;
            var address = InetAddress.getByName(host);
            socket = new Socket(address, port);

            var os = socket.getOutputStream();
            var osw = new OutputStreamWriter(os);
            var bw = new BufferedWriter(osw);

            var is = socket.getInputStream();
            var isr = new InputStreamReader(is);
            var br = new BufferedReader(isr);
            
            String msg = "Hello from Client";

            String sendMessage = msg + "\n";
            bw.write(sendMessage);
            bw.flush();
            System.out.println("Message sent to the server : " + sendMessage);

            String message = br.readLine();
            System.out.println("Message received from the server : " + message);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}