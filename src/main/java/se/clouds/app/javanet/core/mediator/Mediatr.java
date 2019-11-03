package se.clouds.app.javanet.core.mediator;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import se.clouds.app.javanet.app.domain.query.GetControllerResult;



class Storage<T> {

    T value;

    T getValue() {
        return value;
    }

    void setValue(Mediatr<T> mediator, IRequest<T> requestObject, T value) {
        this.value = value;
        mediator.notifyObservers(requestObject);
    }
}

public class Mediatr<T> implements IMediator{
    static int nrOfMediatr;
    private final HashMap<IRequest<T>, Storage<T>> requestMap = new HashMap<>();
    private final CopyOnWriteArrayList<Consumer<Object>> requestObservers = new CopyOnWriteArrayList<>();

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

    public Optional<T> getRequest(IRequest<T> requestObject) {
        Storage storage = requestMap.get(requestObject.getClass().componentType());
        return requestMap
            .entrySet()
            .stream()
            .filter(item -> item.getKey() instanceof GetControllerResult)
            .map(v -> (T) v.getValue().getValue())
            .findFirst();
  }

  public void addRequestObserver(IRequest<T> requestObject, Runnable observer) {
    requestObservers.add(request -> {
      if (request.equals(requestObject)) {
        observer.run();
      }
    });
  }

  void notifyObservers(IRequest<T> request) {
    requestObservers.forEach(observer -> observer.accept(request));
  }

  public void ShowMediatr() {
      System.out.println("nrOfMediatr:" + nrOfMediatr);
      System.out.println("requestMap:" + requestMap);
      System.out.println("requestObservers:" + requestObservers);
  }
}
