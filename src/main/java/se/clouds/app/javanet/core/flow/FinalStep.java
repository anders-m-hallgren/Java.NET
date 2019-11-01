package se.clouds.app.javanet.core.flow;

import se.clouds.app.javanet.core.controller.IResponse;
import se.clouds.app.javanet.core.flow.pipeline.IPipeResponse;
import se.clouds.app.javanet.core.flow.pipeline.PipeResponse;
import se.clouds.app.javanet.core.flow.pipeline.Step;

public class FinalStep implements Step<IPipeResponse, IPipeResponse> {
        IResponse res;
        public FinalStep(IResponse res) {
            super();
            this.res=res;
        }
        public IPipeResponse Process(IPipeResponse input) {
            //var res = (Response)Di.Get(IResponse.class);
            res.getServletResponse().setContent(input.Response() + "\nFinal step");
            return new PipeResponse(input.Response() + "\nFinal step");
        }
    }
