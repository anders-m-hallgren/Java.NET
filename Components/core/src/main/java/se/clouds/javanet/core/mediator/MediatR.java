package se.clouds.javanet.core.mediator;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.Callable;
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

    void setValue(MediatR<T> mediator, Class<?> storageKey, T value) {
        this.value = value;
        mediator.notifyObservers(storageKey);
    }

    void Publish(MediatR<T> mediator, Class<?> storageKey, T value) {
        this.value = value;
        // mediator.notifyHandlers(storageKey);
    }

    void PublishRequest(MediatR<T> mediator, IRequest<?> storageKey, T value) {
        this.value = value;
        // mediator.notifyHandlers(storageKey);
    }

    public void showStorage() {
        System.out.println("value: " + value);
    }
    /*
     * void publishEvent(Mediatr<INotification> mediator, INotification event) {
     * this.event = event; mediator.notifySubscribers(event); }
     *
     * void cancelEvent(T event) { this.event = null; }
     */
}

public class MediatR<T> implements IMediator {
    private static int nrOfMediatrs=0;
    private final HashMap<Class<?>, Storage<T>> storageMap = new HashMap<>();
    private final HashMap<IRequest<?>, Storage<T>> requestStorageMap = new HashMap<>();
    private final CopyOnWriteArrayList<Consumer<Class<?>>> observers = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<Consumer<Class<?>>> handlers = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<Consumer<IRequest<?>>> requestHandlers = new CopyOnWriteArrayList<>();

    public MediatR() {
        super();
        nrOfMediatrs++;
    }
    public void setValue(Class<?> storageKey, T value) {
        Storage<T> storage = storageMap.computeIfAbsent(storageKey, name -> new Storage<>());
        storage.setValue(this, storageKey, value);
    }

    public Optional<T> Send(Class<?> storageKey) {
        notifyHandlers(storageKey);
        return Optional.ofNullable(storageMap.get(storageKey)).map(Storage::getValue);
    }

    public Optional<T> SendRequest(IRequest<?> storageKey) {
        Storage<T> storage=null;
        notifyRequestHandlers(storageKey);
        var entrySet=requestStorageMap.entrySet();
        System.out.print("Handlers[" + entrySet.size() + "] ");
        System.out.print("Find Handler for: " + storageKey.getClass().getSimpleName() + ",Searching");
        for(var entry : entrySet)
        {
            System.out.print(".");

            if (entry.getKey().getClass() == storageKey.getClass()){
                storage = entry.getValue();
                System.out.println("\n   Found a matching handler: " + storageKey.toString() + ", request: " + entry);
            }
        }
        return Optional.ofNullable(storage).map(Storage::getValue);
    }

    public Optional<T> getValue(Class<?> storageKey) {
        return Optional.ofNullable(storageMap.get(storageKey)).map(Storage::getValue);
    }

    public void addObserver(Class<?> storageKey, Runnable observer) {
        observers.add(eventName -> {
            if (eventName.equals(storageKey)) {
                System.out.println("Execute observer callback Runnable Task");
                observer.run();
            }
        });
    }

    public void addHandler(Class<?> storageKey, Callable<T> task) {
        Storage<T> storage = storageMap.computeIfAbsent(storageKey, name -> new Storage<>());
        handlers.add(eventName -> {
            if (eventName.equals(storageKey)) {
                try {
                    T result = task.call();
                    storage.Publish(this, storageKey, result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addRequestHandler(IRequest<?> storageKey, Callable<T> task) {
        Storage<T> storage = requestStorageMap.computeIfAbsent(storageKey, name -> new Storage<>());
        requestHandlers.add(eventName -> {

            if (eventName.getClass().equals(storageKey.getClass())) {
                try {
                    T result = task.call();
                    storage.PublishRequest(this, storageKey, result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void notifyObservers(Class<?> storageKey) {
        observers.forEach(observer -> observer.accept(storageKey));
    }

    void notifyHandlers(Class<?> storageKey) {
        handlers.forEach(observer -> observer.accept(storageKey));
    }

    void notifyRequestHandlers(IRequest<?> storageKey) {
        requestHandlers.forEach(handlers -> handlers.accept(storageKey));
    }

    public void Show() {
        System.out.println("nrOfMediatrs:" + nrOfMediatrs);
        System.out.println("requestStorageMap:" + requestStorageMap);
        System.out.println("requestHandlers:" + requestHandlers);
        System.out.println("observers:" + observers);
        System.out.println("storageMap:" + storageMap);
        System.out.println("handler:" + handlers);
    }
}
