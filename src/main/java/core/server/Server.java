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
    public static int sslPort = 8080;
    private static Server server;
    protected static String serverName = "Clouds.se server v1.0";
    protected static String staticHome = "ClientApp/dist";
    protected static String staticHomeDefaultFile = "index.html";

    public static void Run() {
        try {
            if (server == null) {
                server = new Server();
            }
            HttpServer.start(new TlsEngine(), sslPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
