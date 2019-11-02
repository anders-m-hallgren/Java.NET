package se.clouds.app.javanet.app.domain.handler;

import se.clouds.app.javanet.app.domain.query.GetFlowResult;
import se.clouds.app.javanet.core.controller.Response;
import se.clouds.app.javanet.core.di.Di;
import se.clouds.app.javanet.core.flow.FirstStep;
import se.clouds.app.javanet.core.flow.FlowEngine;
import se.clouds.app.javanet.core.flow.pipeline.IPipeResponse;
import se.clouds.app.javanet.core.flow.pipeline.PipeResponse;
import se.clouds.app.javanet.core.mediator.IMediator;
import se.clouds.app.javanet.core.mediator.IRequestHandler;
import se.clouds.app.javanet.core.mediator.Mediatr;

public class FlowResultHandler implements IRequestHandler<GetFlowResult, IPipeResponse> {

    private Mediatr<IPipeResponse> mediatr = (Mediatr)Di.GetSingleton(IMediator.class, Mediatr.class);

    public FlowResultHandler() {}

    public IPipeResponse Handle(GetFlowResult request) {
        var flowResult = new FlowEngine<IPipeResponse,IPipeResponse>()
            .ConstructPipeFlow()
            .ProcessPipeFlow(new PipeResponse(), new FirstStep(new Response()));
        return flowResult;
    }

    public Task RegisterAndPublish(FlowResultHandler handler, GetFlowResult request) {
        var task = new Task(handler, request);
        mediatr.addRequestObserver(request, task);
        mediatr.Send(request, new PipeResponse()); //not needed ActionResult?
        return task;
    }

    public class Task implements Runnable{
        private FlowResultHandler handler;
        private IPipeResponse content;
        private GetFlowResult request;
        public Task(FlowResultHandler handler, GetFlowResult request) {
            super();
            this.handler = handler;
            this.request = request;
        }
        @Override
        public void run() {
            content = handler.Handle(request);
        }

        public IPipeResponse getContent() {
            return content;
        }
    }
}
