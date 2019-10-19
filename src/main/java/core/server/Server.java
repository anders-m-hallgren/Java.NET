package core.server;

import java.net.ServerSocket;

public class Server {

    private boolean isRunning = true;
    private int port = 8080;
    private static Server server;

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
        final ServerSocket server = new ServerSocket(port);
        System.out.println("Listening for connection on port: " + port);
        while (isRunning) {
            var servlet = new RunnableServlet(server.accept());
            Thread thread = new Thread(servlet);
            thread.start();
        }
        server.close();
    }

}
