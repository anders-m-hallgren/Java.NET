package se.clouds.javanet.core.flow;

import se.clouds.javanet.core.controller.IResponse;
import se.clouds.javanet.core.controller.Response;
import se.clouds.javanet.core.di.Di;
import se.clouds.javanet.core.di.IApplication;
import se.clouds.javanet.core.flow.pipeline.IPipeResponse;
import se.clouds.javanet.core.flow.pipeline.Pipeline;
import se.clouds.javanet.core.flow.pipeline.Step;

public class FlowEngine<In,Out> {

    private Pipeline<IPipeResponse,IPipeResponse> pipeline;
    private IResponse response = new Response();
    private IApplication app;

    public FlowEngine<In,Out> ConstructPipeFlow() {
        app = (IApplication)Di.Get(IApplication.class);
        pipeline=new Pipeline<IPipeResponse,IPipeResponse>(new FirstStep(response));
        for (var step : app.app) {
            pipeline = pipeline.pipe(step);
        }
        pipeline = pipeline.pipe(new FinalStep(response));
        //register flow
        Di.AddSingleton(FlowEngine.class, this);

        return this;
    }

    public IPipeResponse ProcessPipeFlow(IPipeResponse input, Step<In,Out> initialStep) {
        return pipeline.execute(input);
    }

    public void ShowPipelineFlow(){
        //var response = (IResponse)Di.Get(IResponse.class);
        app.app.forEach(s -> System.out.println(s));
    }

    public void ShowPipelineResult(){
        //var writer = new PrintWriter(System.out);
        //var response = (IResponse)Di.Get(IResponse.class);
        System.out.println("Result from pipeline: " + response.getServletResponse().getContent());
    }
}
