package core.pipeline;

import core.controller.IResponse;
import core.controller.Response;
import core.di.Di;
import core.di.IApplication;

public class Pipeline<In, Out> implements IPipeline{
    private final Step<In, Out> current;
    public Pipeline(Step<In, Out> current) {
        this.current = current;
    }

    public <NewOut> Pipeline<In, NewOut> pipe(Step<Out, NewOut> next) {
        return new Pipeline<>(input -> next.Process(current.Process(input)));
    }

    public Out execute(In input) throws Step.StepException {
        return current.Process(input);
    }

    public static void Start() throws Step.StepException {
        var pipeline = (Pipeline<IPipeResponse, IPipeResponse>)Di.Get(IPipeline.class);
        pipeline.execute(new PipeResponse());
    }

    //replace with context
    public static void Build(IApplication app) {
        IResponse res = (IResponse)Di.GetSingleton(IResponse.class, Response.class);

        var pipeline=new Pipeline<>(new FirstStep(res));
        for (var step : app.app) {
            pipeline = pipeline.pipe(step);
        }
        pipeline = pipeline.pipe(new FinalStep(res));
        Di.Add(IPipeline.class, pipeline);
    }

    public static class FirstStep implements Step<IPipeResponse, IPipeResponse> {
        IResponse res;
        public FirstStep(IResponse res) {
            super();
            this.res=res;
        }
        public IPipeResponse Process(IPipeResponse input) {
            res.getServletResponse().setContent(input.Response() + "\nFirst step\n");
            return new PipeResponse(res.getContent());
        }
    }

    public static class FinalStep implements Step<IPipeResponse, IPipeResponse> {
        IResponse res;
        public FinalStep(IResponse res) {
            super();
            this.res=res;
        }
        public IPipeResponse Process(IPipeResponse input) {
            var res = (Response)Di.Get(IResponse.class);
            res.getServletResponse().setContent(input.Response() + "\nFinal step");
            return new PipeResponse(input.Response() + "\nFinal step");
        }
    }

}
