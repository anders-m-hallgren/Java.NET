package se.clouds.app.javanet.app.domain.weatherforecast.handler;

import java.util.Optional;
import java.util.concurrent.Callable;

import redis.clients.jedis.Jedis;
import se.clouds.app.javanet.app.domain.weatherforecast.command.StoreInCache;
import se.clouds.app.javanet.core.configuration.Configuration;
import se.clouds.app.javanet.core.di.Di;
import se.clouds.app.javanet.core.mediator.IMediator;
import se.clouds.app.javanet.core.mediator.IRequestHandler;
import se.clouds.app.javanet.core.mediator.MediatR;

public class StoreCacheHandler implements IRequestHandler<StoreInCache, Void>
{
    private MediatR<Void> mediatr = (MediatR)Di.GetSingleton(IMediator.class, MediatR.class);
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
        //mediatr.AddHandler(request, task);
    }

    /* public void Publish(StoreInCache request) {
        mediatr.setValue(request, Handle(request)); //not needed ActionResult?
    } */

    public Optional<Void> Send(StoreInCache request) {
        //Publish(request);
//        var result = mediatr.getValue(request); //not needed ActionResult?
 //       return result;
 return null;
    }

    public class Task implements Callable<Void>{
        private StoreCacheHandler handler;
        private StoreInCache request;
        public Task(StoreCacheHandler handler, StoreInCache request) {
            super();
            this.handler = handler;
            this.request = request;
        }
        @Override
        public Void call() {
            System.out.println("Implement store in cache here, send to Redis");
            Handle(request);
            return null;
        }
    }

}
