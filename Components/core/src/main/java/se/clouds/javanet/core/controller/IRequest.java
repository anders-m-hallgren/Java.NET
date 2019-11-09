package se.clouds.javanet.core.controller;

import se.clouds.javanet.core.controller.servlet.ServletRequest;

public interface IRequest {
    ServletRequest getServletRequest();
    String getProtocol();
    String getMethod();
    String getContextPath();
}
