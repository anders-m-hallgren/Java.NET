package core.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

import core.app.Router;
import core.controller.ActionResult;
import core.controller.IActionResult;
import core.controller.Request;
import core.controller.ResultStatus.Status;

public class RunnableServlet implements Runnable {
    private Socket client;
    private static final boolean debug = false;

    public RunnableServlet(Socket client) {
        super();
        this.client = client;
    }

    @Override
    public void run() {
        try (var in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                var out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"), true);
                var data = new BufferedOutputStream(client.getOutputStream());) {

            ActionResult result = new ActionResult();

            ReadRequestFromClient(result, in);
            ShowRequest(result);

            SendResultToClient(result, out, data);
            ShowResponse(result);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void SendResultToClient(IActionResult result, PrintWriter out, BufferedOutputStream data)
            throws IOException {
        // write the file contents, if serving static
        // dataOut.write(data, 0, data.length); dataOut.flush();
        var defaultContentMimeType = "text/html";
        var serverName = "Clouds.se server v1.0";

        prepareClientResult(result);
        var status = result.getResponse().getStatus();
        var statusCode = Status.valueOf(String.valueOf(status));

        out.println("HTTP/1.1 " + statusCode);
        out.println("Server: " + serverName);
        out.println("Date: " + new Date());

        switch (status) {
        case UNPROCESSED:
            //throw new Exception();
            break;
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

    public void ReadRequestFromClient(IActionResult result, BufferedReader reader) throws IOException {
        var httpRequest = reader.readLine();
        if (httpRequest == null) {
            result.getResponse().setStatus(Status.BAD_REQUEST);
        }

        var parse = new StringTokenizer(httpRequest);
        var method = parse.nextToken().toUpperCase();
        var contextPath = parse.nextToken();
        var protocol = parse.nextToken();

        var request = new Request(protocol, method, contextPath);
        result.setRequest(request);
        if (debug) {

            var line = reader.readLine();
            while (!line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
            }
        }
    }

    public void prepareClientResult(IActionResult result) throws IOException {
        // we support only GET for now
        if (result.getRequest().getMethod().equals("POST") && result.getRequest().getContextPath().equals("/inform")) {
            result.getResponse().setStatus(Status.OK);
            System.out.println("initial HTTP setup: inform");
            result.SetContent("");
            return;
        }
        if (!result.getRequest().getMethod().equals("GET")) {
            result.getResponse().setStatus(Status.METHOD_NOT_ALLOWED);
            result.SetContent("");
            return;
        }
        if (result.getRequest().getContextPath().equals("/favicon.ico")) {
            result.getResponse().setStatus(Status.NOT_FOUND);
            result.SetContent("");
            return;
        }

        GetControllerResultFromPath(result);
        return;
    }

    public void GetControllerResultFromPath(IActionResult result) {
        // Todo add validations
        System.out.println("Trying to find controller in router for path:" + result.getRequest().getContextPath());
        var ctrl = Router.GetController(result.getRequest().getContextPath());
        if (ctrl == null) {
            result.SetContent("");
            result.getResponse().setStatus(Status.NOT_FOUND);
            System.out.println("Check controller router path, add error throws information");
            //return result;
        }

        var ctrlResult = (ActionResult) ctrl.Get();

        if (result == null || result.toString().isEmpty()) {
            System.out.println("Check controller result, add error throws information");
            result.getResponse().setStatus(Status.SERVICE_UNAVAILABLE);
            //return result;
        }
        System.out.println("OK");
        result.getResponse().setStatus(Status.OK);
        result.SetContent(ctrlResult.GetContent());
        //return result;
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
