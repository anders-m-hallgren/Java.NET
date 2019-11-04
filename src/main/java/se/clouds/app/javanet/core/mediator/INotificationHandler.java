package se.clouds.app.javanet.core.mediator;

public interface INotificationHandler<In> {
    public Void Handle(In notification);
}
