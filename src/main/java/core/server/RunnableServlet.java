package core.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Optional;

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

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void SendResultToClient(IActionResult result, PrintWriter out, BufferedOutputStream data)
            throws IOException {

        var status = result.getResponse().getStatus();
        var statusCode = Status.valueOf(String.valueOf(status)).status;

        switch (status) {
        case STATIC:
            out.println("HTTP/1.1 " + Status.OK.status);
            out.println("Server: " + Server.serverName);
            out.println("Date: " + new Date());

            var path = result.GetStaticPath();
            var content = result.getResponse().getByteContent();
            out.println("Content-Length: " + content.length);

            switch(getFileExtension(path).orElseThrow()){
                case "html":
                    out.println("Content-Type: text/html; charset=utf-8");
                    break;
                case "js":
                    out.println("Content-Type: text/javascript; charset=utf-8");
                    break;
                case "css":
                    out.println("Content-Type: text/css; charset=utf-8");
                    break;
            }
            out.println();
            data.write(content);
            break;

        case UNPROCESSED:
            break;

        case OK:
            out.println("HTTP/1.1 " + statusCode);
            out.println("Content-Type: application/json; charset=utf-8");
            content = result.GetContent().getBytes();
            out.println("Content-Length: " + content.length);
            out.println();
            data.write(content);
            break;

        case NOT_FOUND:
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

    public Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
        .filter(f -> f.contains("."))
        .map(f -> f.substring(filename.lastIndexOf(".") + 1));
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
