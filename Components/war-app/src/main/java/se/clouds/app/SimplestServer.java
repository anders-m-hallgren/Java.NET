package se.clouds.app.javanet;

import org.eclipse.jetty.server.Server;

public class SimplestServer
{
    public static Server createServer(int port)
    {
        Server server = new Server(port);
        // This has a connector listening on port specified
        // and no handlers, meaning all requests will result
        // in a 404 response
        return server;
    }

    public static void main(String[] args) throws Exception
    {
        int port = 8080;
        Server server = createServer(port);
        server.start();
        server.join();
    }
}