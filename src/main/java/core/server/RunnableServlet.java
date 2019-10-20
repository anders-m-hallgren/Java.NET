package core.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Date;

import core.controller.IActionResult;
import core.controller.ResultStatus.Status;

public class RunnableServlet implements Runnable {
    private Socket client;

    public RunnableServlet(Socket client) {
        super();
        this.client = client;
    }

    @Override
    public void run() {
        try (var in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                var out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"), true);
                var data = new BufferedOutputStream(client.getOutputStream());) {

            var result = new RequestProcessor().HandleRequest(in, out, data);
            SendResultToClient(result, out, data);
            //ShowResponse(result);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void SendResultToClient(IActionResult result, PrintWriter out, BufferedOutputStream data)
            throws IOException {
        //var defaultContentMimeType = "text/html";

        var status = result.getResponse().getStatus();
        var statusCode = Status.valueOf(String.valueOf(status));

        out.println("HTTP/1.1 " + statusCode.status);
        out.println("Server: " + Server.serverName);
        out.println("Date: " + new Date());

        switch (status) {
        case UNPROCESSED:
            //todo add thread safety
            //static?
            //check not any controller then assume static
            //if(result.getRequest().getContextPath().equals("/")) {
                var path = result.getRequest().getContextPath();
                if (path.equals("/"))
                    path = "/index.html";
                var staticHome="ClientApp/dist";
                var content = Files.readAllBytes(new File(staticHome + path).toPath());
                System.out.println("writing file: " + staticHome + path + ", " + content.length);
                result.getResponse().setStatus(Status.OK);
                //out.println("HTTP/1.1 " + Status.OK);
                //out.println("Server: " + Server.serverName);
                //out.println("Date: " + new Date());
                if (path.endsWith(".js"))
                    out.println("Content-Type: text/javascript; charset=utf-8");
                if (path.endsWith(".css"))
                    out.println("Content-Type: text/css; charset=utf-8");
                else
                    out.println("Content-Type: text/html; charset=utf-8");
                out.println("Content-Length: " + content.length);
                out.println();
                data.write(content);
            //}
            //throw new Exception();
            break;
        case OK:
            out.println("Content-Type: application/json; charset=utf-8");
            content = result.GetContent().getBytes();
            out.println("Content-Length: " + content.length);
            out.println();
            data.write(content);
            break;
        case NOT_FOUND:
            //out.println("Content-type: " + defaultContentMimeType);
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
        out.println();


                //data.flush();
                //data.close();

    }





    private void ShowRequest(IActionResult result) {
        var req = result.getRequest();
        System.out.println(req.getMethod());
        System.out.println(req.getContextPath());
        System.out.println(req.getProtocol());
    }

    private void ShowResponse(IActionResult result) {
        var res = result.getResponse();
        System.out.println(res.getStatus());
        System.out.println(res.getContent());
    }
}
