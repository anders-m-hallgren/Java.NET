package se.clouds.javanet.core.controller;

import se.clouds.javanet.core.controller.servlet.ServletResponse;

public interface IResponse {
    byte[] getByteContent();
    String getContent();
    ServletResponse getServletResponse();
    void setStatus(ResultStatus.Status status);
    ResultStatus.Status getStatus();
}
