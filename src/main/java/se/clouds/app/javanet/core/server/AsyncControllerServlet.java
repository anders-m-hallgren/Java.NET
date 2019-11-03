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

import se.clouds.app.javanet.app.domain.command.StoreInCache;
import se.clouds.app.javanet.app.domain.handler.ControllerResultHandler;
import se.clouds.app.javanet.app.domain.handler.FlowResultHandler;
import se.clouds.app.javanet.app.domain.handler.StoreCacheHandler;
import se.clouds.app.javanet.app.domain.query.GetControllerResult;
import se.clouds.app.javanet.app.domain.query.GetFlowResult;
import se.clouds.app.javanet.core.controller.IController;
import se.clouds.app.javanet.core.di.Di;
import se.clouds.app.javanet.core.mediator.IMediator;
import se.clouds.app.javanet.core.mediator.IRequest;
import se.clouds.app.javanet.core.mediator.IRequestHandler;
import se.clouds.app.javanet.core.mediator.Mediatr;

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

        var request = new GetControllerResult().setPath(ctxPath);
        var handler = (ControllerResultHandler)Di.GetHandler(IRequestHandler.class, ControllerResultHandler.class);
        content  = handler.Send(handler, request).orElseThrow().GetContent();

        if (includePipeProcessingResult){
            var cache = (StoreCacheHandler)Di.GetHandler(IRequestHandler.class, StoreCacheHandler.class);
            cache.RegisterAndPublish(cache, new StoreInCache());

            var flowRequest = new GetFlowResult(); //shared i.e. Notification
            var flowHandler = (FlowResultHandler)Di.GetHandler(IRequestHandler.class, FlowResultHandler.class);
           //TODO do some actions instead of adding to content
            // content += flowHandler.RegisterAndPublish(flowHandler, flowRequest).getContent().Response();
            System.out.println("AsyncControllerServlet: " + flowHandler.RegisterAndPublish(flowHandler, flowRequest).getContent().Response());
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
