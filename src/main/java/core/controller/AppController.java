package core.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.di.Di;

public abstract class AppController extends HttpServlet implements IController {
    private String routePath;
    private static final long serialVersionUID = 1L;

    public AppController(String path) {
        super();
        routePath=path;
    }

    public String getRoutePath() {
        return routePath;
    }
    public void init() {
    }

    public void postService() {

    }
   /*  public void service() {
        System.out.println("AppController - service");
        IRequest req = (IRequest) Di.GetSingleton(IRequest.class, Request.class);
        IResponse res = (IResponse) Di.GetSingleton(IResponse.class, Response.class);
        try {
            // res.setResponse();
            res.getServletResponse().setStatus(HttpServletResponse.SC_OK);
            //res.getServletResponse().setContent(message);
            service(req.getRequest(), res.getServletResponse());
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
        // sendToPipeline
    }
 */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // super.service(req, resp);
        PrintWriter writer = res.getWriter();
        // PrintWriter writer = new PrintWriter(System.out);
        var content = writer.toString();
        writer.println(content);
        writer.println(req);
        writer.println(res);
    }

    @Override
    public void destroy() {
        //
    }

    //public abstract String getRoutePath();
}
