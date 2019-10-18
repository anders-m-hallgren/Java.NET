package core.controller;

public class ActionResult implements IActionResult {
    private IResponse response;

    public ActionResult() {
        super();
        response = new Response();
    }

    public IResponse getResponse() {
        return response;
    }

    public void setResponse(IResponse response) {
        this.response = response;
    }

    public String GetContent() {
        return getResponse().getServletResponse().getContent();
    }

    public void SetContent(String content) {
        getResponse().getServletResponse().setContent(content);
    }

    public ResultStatus.Status GetStatus() {
        return getResponse().getStatus();
    }

    public void SetStatus(ResultStatus.Status status) {
        getResponse().setStatus(status);
    }

}
