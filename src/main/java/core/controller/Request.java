package core.controller;

import javax.servlet.http.HttpServletRequestWrapper;

import core.controller.servlet.ServletRequest;

public class Request implements IRequest {
    RequestWrapper wrapper = new RequestWrapper();

    public Request() {
        super();
    }

    public class RequestWrapper extends HttpServletRequestWrapper {

        public RequestWrapper() {
            super(new ServletRequest());
        }
    }

    @Override
    public ServletRequest getRequest() {
        return (ServletRequest)wrapper.getRequest();
    }
}
