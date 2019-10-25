package core.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.util.Date;
import java.util.Enumeration;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory;

import core.app.Router;

public class Server {
    private boolean isRunning = true;
    public static int port = 8080;
    public static int sslPort = 4430;
    private static Server server;
    protected static String serverName = "Clouds.se server v1.0";
    protected static String staticHome = "ClientApp/dist";
    protected static String staticHomeDefaultFile = "index.html";

    public static void Run() {
        try {
            if (server == null) {
                server = new Server();
            }
            Router.GetController("/data");
            TlsEngine tlse = new TlsEngine();
            //server.RunMain();
            HttpServer.start(tlse, 8080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   private void RunMain() throws Exception {
        final ServerSocket server = new ServerSocket(Server.port);
        //final SSLServerSocket sslServer = SSLServer(Server.sslPort);

        //System.out.println("Listening for connection on port: " + Server.port);
        //System.out.println("Listening for connection on port: " + Server.sslPort);
        while (isRunning) {
            //LoadCertificateFromPfx();

            //blocks until connection
            Socket clientSocket = server.accept();
            /* new Thread(
                new RunnableServlet(clientSocket))
                .start(); */
        }
        //server.close();
    }
}
