package se.clouds.app.javanet.core.app.handler;

import java.util.Optional;
import java.util.concurrent.Callable;

import se.clouds.app.javanet.core.app.query.GetFlowResult;
import se.clouds.app.javanet.core.controller.Response;
import se.clouds.app.javanet.core.di.Di;
import se.clouds.app.javanet.core.flow.FirstStep;
import se.clouds.app.javanet.core.flow.FlowEngine;
import se.clouds.app.javanet.core.flow.pipeline.IPipeResponse;
import se.clouds.app.javanet.core.flow.pipeline.PipeResponse;
import se.clouds.app.javanet.core.mediator.IMediator;
import se.clouds.app.javanet.core.mediator.IRequestHandler;
import se.clouds.app.javanet.core.mediator.MediatR;

public class FlowResultHandler implements IRequestHandler<GetFlowResult, IPipeResponse> {

    private MediatR<IPipeResponse> mediatr = (MediatR)Di.GetSingleton(IMediator.class, MediatR.class);

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
        var task = new Task(request);
        //mediatr.AddHandler(request, task);
    }

    public void Publish(GetFlowResult request) {
        //mediatr.setValue(request, Handle(request)); //not needed ActionResult?
    }

    public Optional<IPipeResponse> Send(GetFlowResult request) {
        Publish(request);
        //var result = mediatr.getValue(request); //not needed ActionResult?
        //return result;
        return null;
    }

    public class Task implements Callable<IPipeResponse>{
        private IPipeResponse response;
        private GetFlowResult request;
        public Task(GetFlowResult request) {
            super();
            this.request = request;
        }
        @Override
        public IPipeResponse call() {
            return Handle(request);
        }

        public IPipeResponse getResponse() {
            return response;
        }
    }
}
