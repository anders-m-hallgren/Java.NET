package se.clouds.app.javanet.core.mediator;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;


class Storage<T> {

    T value;
    INotification event;

    T getValue() {
        return value;
    }

    INotification getEvent() {
        return event;
    }

    void setValue(Mediatr<T> mediator, IRequest<T> requestObject, T value) {
        this.value = value;
        mediator.notifyObservers(requestObject);
    }

    void publishEvent(Mediatr<INotification> mediator, INotification event) {
        this.event = event;
        mediator.notifySubscribers(event);
    }
    void cancelEvent(T event) {
        this.event = null;
    }
}

public class Mediatr<T> implements IMediator{
    static int nrOfMediatr;
    private final HashMap<IRequest<T>, Storage<T>> requestMap = new HashMap<>();
    private final HashMap<INotification, Storage<INotification>> notificationMap = new HashMap<>();
    private final CopyOnWriteArrayList<Consumer<Object>> requestObservers = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<Consumer<Object>> subscribers = new CopyOnWriteArrayList<>();

    public Mediatr() {
        nrOfMediatr++;
    }
    public void setValue(IRequest<T> requestObject, T value) {
        Storage<T> storage = requestMap.computeIfAbsent(requestObject, name -> new Storage<T>());
        storage.setValue(this, requestObject, value);
      }

      public Optional<T> getValue(IRequest<T> requestObject) {
        return Optional.ofNullable(requestMap.get(requestObject)).map(Storage::getValue);
      }

    public void Send(IRequest<T> requestObject, T value) {
        Storage<T> storage = requestMap.computeIfAbsent(requestObject, key -> new Storage<T>());
        storage.setValue(this, requestObject, value);
    }

    public void Publish(INotification event) {
        Storage<INotification> storage = notificationMap.computeIfAbsent(event, key -> new Storage<INotification>());
        storage.publishEvent((Mediatr<INotification>)this, event);
        //storage.cancelEvent();
    }



  public void addRequestObserver(IRequest<T> requestObject, Runnable observer) {
    requestObservers.add(request -> {
      if (request.equals(requestObject)) {
        observer.run();
      }
    });
  }

  public void addSubscriber(INotification event, Runnable subscriber) {
    subscribers.add(ev -> {
      if (ev.equals(event)) {
        subscriber.run();
      }
    });
  }

  void notifyObservers(IRequest<T> request) {
    requestObservers.forEach(observer -> observer.accept(request));
  }

  void notifySubscribers(INotification event) {
    subscribers.forEach(subscriber -> subscriber.accept(event));
  }

  public void ShowMediatr() {
      System.out.println("nrOfMediatr:" + nrOfMediatr);
      System.out.println("requestMap:" + requestMap);
      System.out.println("requestObservers:" + requestObservers);
  }
}
