package se.clouds.app.javanet.app.domain.handler;

import java.util.Arrays;
import java.util.Optional;

import redis.clients.jedis.Jedis;
import se.clouds.app.javanet.app.domain.command.StoreInCache;
import se.clouds.app.javanet.core.configuration.Configuration;
import se.clouds.app.javanet.core.di.Di;
import se.clouds.app.javanet.core.mediator.IMediator;
import se.clouds.app.javanet.core.mediator.IRequestHandler;
import se.clouds.app.javanet.core.mediator.Mediatr;

public class StoreCacheHandler implements IRequestHandler<StoreInCache, Void>
{
    private Mediatr<Void> mediatr = (Mediatr)Di.GetSingleton(IMediator.class, Mediatr.class);
    private Jedis jedis;

    public StoreCacheHandler() {
        var host = Configuration.Get("Redis", "Host");
        jedis = new Jedis(host); //TODO use host from configuration
    }

    public Void Handle(StoreInCache request) {
        jedis.set("java-cache-0","hello from storeCache");
        return null;
    }

    public void Register(StoreInCache request) {
        var task = new Task(this, request);
        mediatr.addRequestObserver(request, task);
    }

    /* public void Publish(StoreInCache request) {
        mediatr.setValue(request, Handle(request)); //not needed ActionResult?
    } */

    public Optional<Void> Send(StoreInCache request) {
        //var task = getTask(handler, request);
        //Publish(request);
        var result = mediatr.getValue(request); //not needed ActionResult?
        return result;
    }

    public class Task implements Runnable{
        private StoreCacheHandler handler;
        private StoreInCache request;
        public Task(StoreCacheHandler handler, StoreInCache request) {
            super();
            this.handler = handler;
            this.request = request;
        }
        @Override
        public void run() {
            System.out.println("Implement store in cache here, send to Redis");
            //content = handler.Handle(request);
        }
    }
}
