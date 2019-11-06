package se.clouds.app.javanet.core.server;

public class Server {
    private boolean isRunning = true;
    public static int sslPort = 8080;
    private static Server server;
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
