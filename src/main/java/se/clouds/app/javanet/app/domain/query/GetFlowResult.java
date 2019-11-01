package se.clouds.app.javanet.app.domain.query;

import se.clouds.app.javanet.core.flow.pipeline.IPipeResponse;
import se.clouds.app.javanet.core.mediator.IRequest;

public class GetFlowResult implements IRequest<IPipeResponse> {

    @Override
    public boolean equals(Object o) {
        if (this instanceof GetFlowResult)
            return true;
        return false;
    }


}
