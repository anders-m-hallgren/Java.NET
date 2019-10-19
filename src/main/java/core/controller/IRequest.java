package core.controller;

import core.controller.servlet.ServletRequest;

public interface IRequest {
    ServletRequest getRequest();
    String getProtocol();
    String getMethod();
    String getContextPath();
}
