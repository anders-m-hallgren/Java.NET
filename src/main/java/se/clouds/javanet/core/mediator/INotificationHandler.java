package se.clouds.javanet.core.mediator;

public interface INotificationHandler<In> {
    public Void Handle(In notification);
}
