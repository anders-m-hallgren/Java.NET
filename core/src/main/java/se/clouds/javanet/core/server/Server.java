package se.clouds.javanet.core.server;

public class Server {
    public static int sslPort = 8080;
    protected static String serverName = "Clouds.se server v1.0";
    protected static String staticHome = "ClientApp/dist";
    protected static String staticHomeDefaultFile = "index.html";

    public static void Run() {
        try {
            HttpServer.start(new TlsEngine(), sslPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
