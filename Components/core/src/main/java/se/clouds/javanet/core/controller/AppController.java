package se.clouds.javanet.core.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AppController extends HttpServlet implements IController {
    private String routePath;
    private boolean includePipeProcessingResult = true;
    private static final long serialVersionUID = 1L;

    public AppController(String path, boolean includePipeProcessingResult) {
        super();
        this.routePath=path;
        this.includePipeProcessingResult = includePipeProcessingResult;
    }

    public AppController(String path) {
        super();
        this.routePath=path;
    }

    public String getRoutePath() {
        return routePath;
    }

    public boolean getIncludePipeProcessing() {
        return includePipeProcessingResult;
    }
    public void init() {
    }

    public void postService() {

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        /* PrintWriter writer = res.getWriter();
        var content = writer.toString();
        writer.println(content);
        writer.println(req);
        writer.println(res); */
    }

    @Override
    public void destroy() {
    }

    public boolean isIncludePipeProcessingResult() {
        return includePipeProcessingResult;
    }

    public void setIncludePipeProcessingResult(boolean includePipeProcessingResult) {
        this.includePipeProcessingResult = includePipeProcessingResult;
    }
}
