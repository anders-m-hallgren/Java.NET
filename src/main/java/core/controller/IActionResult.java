package core.controller;

public interface IActionResult {
    //void setResponse(IResponse response);
    IResponse getResponse();
    String GetContent();
    void SetContent(String content);
    void setRequest(IRequest request);
    IRequest getRequest();
}
