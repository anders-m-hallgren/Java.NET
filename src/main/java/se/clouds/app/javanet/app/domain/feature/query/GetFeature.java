package se.clouds.app.javanet.app.domain.feature.query;

import se.clouds.app.javanet.core.controller.IActionResult;
import se.clouds.app.javanet.core.mediator.IRequest;

public class GetFeature implements IRequest<IActionResult> {
    private IActionResult result;

    private String path=null;

    public GetFeature() {}

    public GetFeature setPath(String path){
        this.path = path;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this instanceof GetFeature)
            return true;
        return false;
    }

    public String getPath() {
        return path;
    }

    public IActionResult getResult() {
        return result;
    }

    public void setResult(IActionResult result) {
        this.result = result;
    }
}
