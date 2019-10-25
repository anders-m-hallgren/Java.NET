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

import core.controller.ActionResult;
import core.controller.IActionResult;
import core.di.Di;
import core.mediator.IMediator;
import core.mediator.Mediator;

@SuppressWarnings("serial")
public class AsyncControllerServlet extends HttpServlet {
    private String ctxPath;
    private Mediator mediator = (Mediator)Di.GetSingleton(IMediator.class, Mediator.class);
    //private static String HEAVY_RESOURCE = "This is some heavy resource that will be served in an async way";
private static String content;
    public AsyncControllerServlet(String ctxPath) {
        super();
        this.ctxPath=ctxPath;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IActionResult result = new ActionResult();

        mediator.AttachControllerResult(ctxPath, result);
        content = result.getResponse().getServletResponse().getContent();
        mediator.AttachFlowResult(result);
        content += result.GetContent();

        ByteBuffer bb = ByteBuffer.wrap(content.getBytes(StandardCharsets.UTF_8));
        AsyncContext async = request.startAsync();
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
