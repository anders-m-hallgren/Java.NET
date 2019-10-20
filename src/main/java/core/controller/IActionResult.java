package core.controller;

public interface IActionResult {
    //void setResponse(IResponse response);
    IResponse getResponse();
    String GetContent();
    byte[] GetByteContent();
    void SetContent(String content);
    void setRequest(IRequest request);
    IRequest getRequest();
    void SetStaticPath(String path);
    String GetStaticPath();
}
