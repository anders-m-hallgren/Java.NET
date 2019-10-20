package core.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import core.app.Router;
import core.controller.ActionResult;
import core.controller.IActionResult;
import core.controller.Request;
import core.controller.ResultStatus.Status;

/**
 * RequestProcessor
 */
public class RequestProcessor {
    private static final boolean debug = false;
    private IActionResult result = new ActionResult();

    public IActionResult HandleRequest(BufferedReader reader, PrintWriter out, BufferedOutputStream data) throws IOException {
        ReadRequestFromClient(reader);
        ProcessClientRequest(out, data);
        return result;
    }

    private void ReadRequestFromClient(BufferedReader in) throws IOException {
        var httpRequest = in.readLine();
        if (debug) {
            var line = in.readLine();
            while (!line.isEmpty()) {
                System.out.println(line);
                line = in.readLine();
            }
        }

        if (httpRequest == null) {
            result.getResponse().setStatus(Status.BAD_REQUEST);
            return;
        }

        var parse = new StringTokenizer(httpRequest);
        var method = parse.nextToken().toUpperCase();
        var contextPath = parse.nextToken();
        var protocol = parse.nextToken();

        var request = new Request(protocol, method, contextPath);
        result.setRequest(request);

    }
    private void ProcessClientRequest(PrintWriter out, BufferedOutputStream data) throws IOException {
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

        //TODO add handling for  all registered controllers
        if(result.getRequest().getContextPath().equals("/data")) { //other static paths?
            GetControllerResultFromPath(result);
        }
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
}
