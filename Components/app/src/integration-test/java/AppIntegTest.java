package se.clouds.javanet.app;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import se.clouds.javanet.app.Startup;
import se.clouds.javanet.core.App;

public class AppIntegTest {
    private final Startup startup = new Startup();

    public static boolean serverListening(String host, int port) {
        Socket s = null;
        try {
            s = new Socket(host, port);
            return s.isConnected();
        }
        catch (Exception e)
        {
            return false;
        }
        finally
        {
            if(s != null)
                try {s.close();}
                catch(Exception e){}
        }
    }

    @Test
    void AppShouldRun() {
        Path path = FileSystems.getDefault().getPath(".");
        System.out.println(path);
        //App.Run(startup);
        //assertTrue(false);
        assertTrue(serverListening("localhost", 8080));
    }
}
