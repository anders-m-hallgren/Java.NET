package se.clouds.app.javanet.app.domain.query;

import se.clouds.app.javanet.core.controller.IActionResult;
import se.clouds.app.javanet.core.mediator.IRequest;

public class GetControllerResult implements IRequest<IActionResult> {
    private IActionResult result;

    private String path=null;

    public GetControllerResult() {}

    public GetControllerResult setPath(String path){
        this.path = path;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this instanceof GetControllerResult)
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
