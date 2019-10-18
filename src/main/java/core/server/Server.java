package core.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

import core.app.Router;
import core.controller.ActionResult;
import static core.controller.ResultStatus.Status;

public class Server {
    private static final boolean debug = false;
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
            final Socket client = server.accept();
            try (var in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    var out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"), true);
                    var data = new BufferedOutputStream(client.getOutputStream());) {
                sendToClient(in, out, data);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
        server.close();
    }

    public void sendToClient(BufferedReader in, PrintWriter out, BufferedOutputStream data) throws IOException {
        // write the file contents, if serving static
        // dataOut.write(data, 0, data.length); dataOut.flush();
        var defaultContentMimeType = "text/html";
        var serverName = "Clouds.se server v1.0";
        var result = prepareClientResult(in);
        var status = result.getResponse().getStatus();
        var statusCode = Status.valueOf(String.valueOf(status));

        out.println("HTTP/1.1 " + statusCode);
        out.println("Server: " + serverName);
        out.println("Date: " + new Date());

        switch (status) {
            case OK:
                out.println("Content-Type: application/json; charset=utf-8");
                var content = result.GetContent().getBytes();
                out.println("Content-Length: " + content.length);
                out.println();
                data.write(content);
            break;
            case NOT_FOUND:
                out.println("Content-type: " + defaultContentMimeType);
                out.println();
            break;
            case BAD_REQUEST:
            break;
            case NOT_IMPLEMENTED:
            break;
            case METHOD_NOT_ALLOWED:
            break;
            case SERVICE_UNAVAILABLE:
            break;
        }
    }

    public ActionResult prepareClientResult(BufferedReader reader) throws IOException {
        ActionResult result = new ActionResult();

        var httpRequest = reader.readLine();
        if (httpRequest == null) {
            result.getResponse().setStatus(Status.BAD_REQUEST);
            return result;
        }
        var parse = new StringTokenizer(httpRequest);
        var method = parse.nextToken().toUpperCase();
        var path = parse.nextToken();
        var prot = parse.nextToken();

        if (debug) {
            System.out.println(method);
            System.out.println(path);
            System.out.println(prot);
            var line = reader.readLine();
            while (!line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
            }
        }
        // we support only GET for now
        if (method.equals("POST") && path.equals("/inform")) {
            result.getResponse().setStatus(Status.OK);
            System.out.println("initial HTTP setup: inform");
            result.SetContent("");
            return result;
        }
        if (!method.equals("GET")) {
            result.getResponse().setStatus(Status.METHOD_NOT_ALLOWED);
            result.SetContent("");
            return result;
        }

        result = GetControllerResultFromPath(path);
        return result;
    }

    public ActionResult GetControllerResultFromPath(String path) {
        ActionResult result = new ActionResult();
        // Todo add validations
        System.out.println("Trying to find controller in router for path:" + path);
        var ctrl = Router.GetController(path);
        if (ctrl == null) {
            result.SetContent("");
            result.getResponse().setStatus(Status.NOT_FOUND);
            System.out.println("Check controller router path, add error throws information");
            return result;
        }

        result = (ActionResult)ctrl.Get();

        if (result == null || result.toString().isEmpty()) {
            System.out.println("Check controller result, add error throws information");
            result.getResponse().setStatus(Status.SERVICE_UNAVAILABLE);
            return result;
        }
        return result;
    }
}
