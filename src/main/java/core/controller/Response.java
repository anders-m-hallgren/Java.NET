package core.controller;

import javax.servlet.http.HttpServletResponseWrapper;

import core.controller.servlet.ServletResponse;


public class Response implements IResponse {


    private ResultStatus.Status status;
    public ResponseWrapper wrapper = new ResponseWrapper();

    public Response() {
        super();
    }

    public class ResponseWrapper extends HttpServletResponseWrapper {
        public ResponseWrapper() {
            super(new ServletResponse());
		}
    }

    public ResultStatus.Status getStatus() {
        return status;
    }

    public void setStatus(ResultStatus.Status status) {
        this.status = status;
    }

    @Override
    public String getContent() {
        return getServletResponse().getContent();
    }

    @Override
    public ServletResponse getServletResponse() {
        return (ServletResponse)wrapper.getResponse();
    }

}
