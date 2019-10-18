package core.controller;

public interface IActionResult {
    void setResponse(IResponse response);
    IResponse getResponse();
}
