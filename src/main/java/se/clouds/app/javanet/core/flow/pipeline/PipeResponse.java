package se.clouds.app.javanet.core.flow.pipeline;

public class PipeResponse implements IPipeResponse {
    private String _response;

    public PipeResponse() {
        super();
        _response = "";
    }
    public PipeResponse(String response) {
        super();
        _response = response;
    }

    @Override
    public String Response() {
        return _response;
    }

    @Override
    public void Add(String in) {
        _response = _response + in;
    }
}
