package se.clouds.app.javanet.core.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.clouds.app.javanet.app.domain.handler.ControllerResultHandler;
import se.clouds.app.javanet.app.domain.weatherforecast.command.StoreInCache;
import se.clouds.app.javanet.app.domain.weatherforecast.handler.FlowResultHandler;
import se.clouds.app.javanet.app.domain.weatherforecast.handler.StoreCacheHandler;
import se.clouds.app.javanet.app.domain.weatherforecast.query.GetControllerResult;
import se.clouds.app.javanet.app.domain.weatherforecast.query.GetFlowResult;
import se.clouds.app.javanet.core.controller.IController;
import se.clouds.app.javanet.core.di.Di;
import se.clouds.app.javanet.core.mediator.IRequestHandler;

@SuppressWarnings("serial")
public class AsyncControllerServlet extends HttpServlet {
    private boolean includePipeProcessingResult = false;
    private String ctxPath;
    private static String content;

    public AsyncControllerServlet(IController ctrl) {
        super();
        this.ctxPath=ctrl.getRoutePath();
        includePipeProcessingResult = ctrl.getIncludePipeProcessing();
        this.includePipeProcessingResult = ctrl.getIncludePipeProcessing();
    }

    @Override
    protected void doGet(HttpServletRequest servletRequest, HttpServletResponse response) throws IOException {
        //todo no dependencies to domain here, fix !
        var request = new GetControllerResult().setPath(ctxPath);
        Di.Show();
        var handler = (ControllerResultHandler)Di.GetHandler(IRequestHandler.class, ControllerResultHandler.class);
        content  = handler.Send(handler, request).orElseThrow().GetContent();

        if (includePipeProcessingResult){
            var cache = (StoreCacheHandler)Di.GetHandler(IRequestHandler.class, StoreCacheHandler.class);
            cache.Send(new StoreInCache());

            var flow = (FlowResultHandler)Di.GetHandler(IRequestHandler.class, FlowResultHandler.class);
            var flowResponse = flow.Send(new GetFlowResult()).orElseThrow().Response();
            System.out.println("flowResponse: " + flowResponse);
        }

        ByteBuffer bb = ByteBuffer.wrap(content.getBytes(StandardCharsets.UTF_8));
        AsyncContext async = servletRequest.startAsync();
        ServletOutputStream out = response.getOutputStream();

        out.setWriteListener(new WriteListener() {
            @Override
            public void onWritePossible() throws IOException {
                while (out.isReady()) {
                    if (!bb.hasRemaining()) {
                        response.setStatus(200);
                        async.complete();
                        return;
                    }
                    out.write(bb.get());
                }
            }

            @Override
            public void onError(Throwable t) {
                getServletContext().log("Async Error", t);
                async.complete();
            }
        });
    }

    public String getName() {
        return ctxPath;
    }

    public void setCtxPath(String ctxPath) {
        this.ctxPath = ctxPath;
    }


}
