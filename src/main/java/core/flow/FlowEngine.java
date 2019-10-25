package core.flow;

import core.controller.IResponse;
import core.controller.Response;
import core.di.Di;
import core.di.IApplication;
import core.flow.pipeline.IPipeResponse;
import core.flow.pipeline.Pipeline;
import core.flow.pipeline.Step;

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
        Di.Add(FlowEngine.class, this);
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
