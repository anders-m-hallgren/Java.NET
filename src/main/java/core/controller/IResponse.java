package core.controller;

import core.controller.servlet.ServletResponse;

public interface IResponse {
    String getContent();
    ServletResponse getServletResponse();
    void setStatus(ResultStatus.Status status);
    ResultStatus.Status getStatus();
}
