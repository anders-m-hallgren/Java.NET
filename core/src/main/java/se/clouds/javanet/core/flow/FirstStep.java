package se.clouds.javanet.core.flow;

import se.clouds.javanet.core.controller.IResponse;
import se.clouds.javanet.core.flow.pipeline.IPipeResponse;
import se.clouds.javanet.core.flow.pipeline.PipeResponse;
import se.clouds.javanet.core.flow.pipeline.Step;

public class FirstStep implements Step<IPipeResponse, IPipeResponse> {
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
