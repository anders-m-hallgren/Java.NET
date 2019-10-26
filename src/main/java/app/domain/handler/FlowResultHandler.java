package app.domain.handler;

import core.controller.Response;
import core.di.Di;
import core.flow.FirstStep;
import core.flow.FlowEngine;
import core.flow.pipeline.IPipeResponse;
import core.flow.pipeline.PipeResponse;
import core.mediator.IMediator;
import core.mediator.IRequestHandler;
import core.mediator.Mediatr;
import app.domain.query.GetFlowResult;

public class FlowResultHandler implements IRequestHandler<GetFlowResult, IPipeResponse> {

    private Mediatr<IPipeResponse> mediatr = (Mediatr)Di.GetSingleton(IMediator.class, Mediatr.class);

    public FlowResultHandler() {
        super();
    }

    public IPipeResponse Handle(GetFlowResult request) {
        var flowResult = new FlowEngine<IPipeResponse,IPipeResponse>()
            .ConstructPipeFlow()
            .ProcessPipeFlow(new PipeResponse(), new FirstStep(new Response()));
        return flowResult;
    }

    public Task RegisterAndPublish(FlowResultHandler handler, GetFlowResult request) {
        var task = getTask(handler, request);
        mediatr.addRequestObserver(request, task);
        mediatr.Send(request, new PipeResponse()); //not needed ActionResult?
        return task;
    }

    public Task getTask(FlowResultHandler handler, GetFlowResult request) {
        return new Task(handler, request);
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
