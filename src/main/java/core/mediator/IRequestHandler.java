package core.mediator;

//TODO
public interface IRequestHandler<In,Out> {
    public Out Handle(In request);
}
