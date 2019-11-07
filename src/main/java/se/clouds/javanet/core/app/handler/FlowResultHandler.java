package se.clouds.javanet.core.app.handler;

import java.util.Optional;
import java.util.concurrent.Callable;

import se.clouds.javanet.core.app.query.GetFlowResult;
import se.clouds.javanet.core.controller.Response;
import se.clouds.javanet.core.di.Di;
import se.clouds.javanet.core.flow.FirstStep;
import se.clouds.javanet.core.flow.FlowEngine;
import se.clouds.javanet.core.flow.pipeline.IPipeResponse;
import se.clouds.javanet.core.flow.pipeline.PipeResponse;
import se.clouds.javanet.core.mediator.IMediator;
import se.clouds.javanet.core.mediator.IRequestHandler;
import se.clouds.javanet.core.mediator.MediatR;
import se.clouds.javanet.core.mediator.Task;

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
