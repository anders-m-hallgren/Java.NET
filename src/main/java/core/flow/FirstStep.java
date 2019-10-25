package core.flow;

import core.controller.IResponse;
import core.flow.pipeline.IPipeResponse;
import core.flow.pipeline.PipeResponse;
import core.flow.pipeline.Step;

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
