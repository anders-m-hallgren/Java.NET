package core.mediator;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import app.domain.query.GetControllerResult;


class Storage<T> {

    T value;

    T getValue() {
        return value;
    }

    void setValue(Mediatr<T> mediator, IRequest requestObject, T value) {
        this.value = value;
        mediator.notifyObservers(requestObject);
    }
}

public class Mediatr<T> implements IMediator{
    private final HashMap<IRequest<T>, Storage<T>> requestMap = new HashMap<>();
    private final CopyOnWriteArrayList<Consumer<Object>> requestObservers = new CopyOnWriteArrayList<>();

    public void Send(IRequest<T> requestObject, T value) {
        Storage storage = requestMap.computeIfAbsent(requestObject, key -> new Storage());
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
}
