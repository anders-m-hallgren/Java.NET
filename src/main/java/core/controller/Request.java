package core.controller;

import javax.servlet.http.HttpServletRequestWrapper;

import core.controller.servlet.ServletRequest;

public class Request implements IRequest {
    RequestWrapper wrapper;

    public Request(String protocol, String method, String contextPath) {
        super();
        wrapper = new RequestWrapper(protocol, method, contextPath);
    }

    public class RequestWrapper extends HttpServletRequestWrapper {

        public RequestWrapper(String protocol, String method, String contextPath) {
            super(new ServletRequest(protocol, method, contextPath));
        }
    }

    @Override
    public ServletRequest getRequest() {
        return (ServletRequest)wrapper.getRequest();
    }

    @Override
    public String getProtocol() {
        return wrapper.getProtocol();
    }

    @Override
    public String getMethod() {
        return wrapper.getMethod();
    }

     @Override
    public String getContextPath() {
        return wrapper.getContextPath();
    }

}
