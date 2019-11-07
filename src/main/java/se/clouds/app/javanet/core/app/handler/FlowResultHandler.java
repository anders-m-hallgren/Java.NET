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
import se.clouds.app.javanet.core.mediator.Task;

public class FlowResultHandler implements IRequestHandler<GetFlowResult, IPipeResponse>
{
    public FlowResultHandler() {
        var mediatr = ((MediatR<IPipeResponse>)Di.GetMediator());
        mediatr.addRequestHandler(new GetFlowResult(), new HandlerTask());
    }

    public class HandlerTask implements Task<IPipeResponse>
    {
        @Override
        public IPipeResponse call() {
            var flowResult = new FlowEngine<IPipeResponse,IPipeResponse>()
            .ConstructPipeFlow()
            .ProcessPipeFlow(new PipeResponse(), new FirstStep(new Response()));
            return flowResult;
        }
    }
}
