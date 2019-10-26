package app.domain.query;

import core.controller.IActionResult;
import core.flow.pipeline.IPipeResponse;
import core.mediator.IRequest;

public class GetFlowResult implements IRequest<IPipeResponse> {
    private IActionResult result;

    public GetFlowResult() {
        super();
    }
    @Override
    public boolean equals(Object o) {
        if (this instanceof GetFlowResult)
            return true;
        return false;
    }

    public IActionResult getResult() {
        return result;
    }

    public void setResult(IActionResult result) {
        this.result = result;
    }

}
