package se.clouds.javanet.core.controller;

public class ActionResult implements IActionResult {
    private IResponse response;
    private IRequest request;
    private String staticPath;

    public ActionResult() {
        super();
        response = new Response();
        SetStatus(ResultStatus.Status.UNPROCESSED);
    }

    public IResponse getResponse() {
        return response;
    }

    /* public void setResponse(IResponse response) {
        this.response = response;
    }
 */
    public IRequest getRequest() {
        return request;
    }

    public void setRequest(IRequest request) {
        this.request = request;
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

    @Override
    public byte[] GetByteContent() {
        return getResponse().getServletResponse().getByteContent();
    }

    @Override
    public void SetStaticPath(String staticPath) {
        this.staticPath = staticPath;

    }

    @Override
    public String GetStaticPath() {
        return staticPath;
    }

}
