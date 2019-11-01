package se.clouds.app.javanet.core.controller;

import se.clouds.app.javanet.core.controller.servlet.ServletRequest;

public interface IRequest {
    ServletRequest getServletRequest();
    String getProtocol();
    String getMethod();
    String getContextPath();
}
