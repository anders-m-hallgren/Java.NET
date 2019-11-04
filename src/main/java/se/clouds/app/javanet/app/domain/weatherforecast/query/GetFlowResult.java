package se.clouds.app.javanet.app.domain.weatherforecast.query;

import se.clouds.app.javanet.core.flow.pipeline.IPipeResponse;
import se.clouds.app.javanet.core.mediator.IRequest;

public class GetFlowResult implements IRequest<IPipeResponse>
{
    public GetFlowResult() {}

    @Override
    public boolean equals(Object o) {
        if (this instanceof GetFlowResult)
            return true;
        return false;
    }
}
