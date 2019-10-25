package core.controller;

import core.controller.servlet.ServletRequest;

public interface IRequest {
    ServletRequest getServletRequest();
    String getProtocol();
    String getMethod();
    String getContextPath();
}
