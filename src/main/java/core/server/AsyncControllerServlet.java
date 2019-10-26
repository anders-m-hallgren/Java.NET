package core.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.domain.handler.ControllerResultHandler;
import app.domain.handler.FlowResultHandler;
import app.domain.query.GetControllerResult;
import app.domain.query.GetFlowResult;

@SuppressWarnings("serial")
public class AsyncControllerServlet extends HttpServlet {

    private String ctxPath;
    private static String content;

    public AsyncControllerServlet(String ctxPath) {
        super();
        this.ctxPath=ctxPath;
    }

    @Override
    protected void doGet(HttpServletRequest servletRequest, HttpServletResponse response) throws IOException {

        var request = new GetControllerResult(ctxPath); //shared i.e. Notification
        var handler = new ControllerResultHandler();
        content = handler.RegisterAndPublish(handler, request).getContent();

        var flowRequest = new GetFlowResult(); //shared i.e. Notification
        var flowHandler = new FlowResultHandler();
        content += flowHandler.RegisterAndPublish(flowHandler, flowRequest).getContent().Response();

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
