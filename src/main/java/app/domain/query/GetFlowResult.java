package app.domain.query;

import core.flow.pipeline.IPipeResponse;
import core.mediator.IRequest;

public class GetFlowResult implements IRequest<IPipeResponse> {

    @Override
    public boolean equals(Object o) {
        if (this instanceof GetFlowResult)
            return true;
        return false;
    }


}
