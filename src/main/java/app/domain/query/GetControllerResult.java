package app.domain.query;

import core.controller.IActionResult;
import core.mediator.IRequest;

public class GetControllerResult implements IRequest<IActionResult> {
    private IActionResult result;

    private String path=null;
    public GetControllerResult(String path) {
        super();
        this.path = path;
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
