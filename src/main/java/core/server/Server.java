package core.server;

import java.net.ServerSocket;

public class Server {
    private boolean isRunning = true;
    private static int port = 8080;
    private static Server server;
    protected static String serverName = "Clouds.se server v1.0";
    protected static String staticHome = "ClientApp/dist";
    protected static String staticHomeDefaultFile = "index.html";

    public static void Run() {
        try {
            if (server == null) {
                server = new Server();
            }
            server.RunMain();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void RunMain() throws Exception {
        final ServerSocket server = new ServerSocket(Server.port);
        System.out.println("Listening for connection on port: " + Server.port);
        while (isRunning) {
            var servlet = new RunnableServlet(server.accept());
            Thread thread = new Thread(servlet);
            thread.start();
        }
        server.close();
    }
}
