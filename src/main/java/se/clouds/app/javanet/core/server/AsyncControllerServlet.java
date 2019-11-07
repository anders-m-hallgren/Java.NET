package se.clouds.app.javanet.core.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.clouds.app.javanet.app.domain.feature.query.GetFeature;
import se.clouds.app.javanet.app.domain.weatherforecast.query.GetWeatherForecast;
import se.clouds.app.javanet.core.app.Router;
import se.clouds.app.javanet.core.app.query.GetFlowResult;
import se.clouds.app.javanet.core.controller.IActionResult;
import se.clouds.app.javanet.core.di.Di;
import se.clouds.app.javanet.core.flow.pipeline.IPipeResponse;
import se.clouds.app.javanet.core.mediator.IRequest;
import se.clouds.app.javanet.core.mediator.MediatR;

@SuppressWarnings("serial")
public class AsyncControllerServlet extends HttpServlet implements IControllerServlet{
    //private boolean includePipeProcessingResult = false;
    private static String content;

    public AsyncControllerServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest servletRequest, HttpServletResponse response) throws IOException
    {
        var ctrl = Router.GetController(servletRequest.getServletPath());
        content = ctrl.Get().GetContent();

        System.out.println("\nServlet content: " + content);
            // var cache = (StoreCacheHandler)Di.GetHandler(IRequestHandler.class, StoreCacheHandler.class);
            // cache.Send(new StoreInCache());
        try
        {
            var pipeMediatr = ((MediatR<IPipeResponse>)Di.GetMediator());
            var flowResponse = pipeMediatr.SendRequest(new GetFlowResult()).orElseThrow().Response();
            System.out.println("flowResponse: " + flowResponse);
        } catch (Exception e)
        {
            e.printStackTrace();
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



}
