package se.clouds.app.javanet.app.domain.handler;

import java.util.Optional;

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

    public FlowResultHandler() {
        Register(new GetFlowResult());
    }

    public IPipeResponse Handle(GetFlowResult request) {
        var flowResult = new FlowEngine<IPipeResponse,IPipeResponse>()
            .ConstructPipeFlow()
            .ProcessPipeFlow(new PipeResponse(), new FirstStep(new Response()));
        return flowResult;
    }

    public void Register(GetFlowResult request) {
        var task = new Task(this, request);
        mediatr.addRequestObserver(request, task);
    }

    public void Publish(GetFlowResult request) {
        //var task = getTask(handler, request);
        mediatr.setValue(request, Handle(request)); //not needed ActionResult?
    }

    public Optional<IPipeResponse> Send(GetFlowResult request) {
        //var task = getTask(handler, request);
        Publish(request);
        var result = mediatr.getValue(request); //not needed ActionResult?
        return result;
    }

    public class Task implements Runnable{
        private FlowResultHandler handler;
        private IPipeResponse response;
        private GetFlowResult request;
        public Task(FlowResultHandler handler, GetFlowResult request) {
            super();
            this.handler = handler;
            this.request = request;
        }
        @Override
        public void run() {
            response = handler.Handle(request);
        }

        public IPipeResponse getResponse() {
            return response;
        }
    }
}
