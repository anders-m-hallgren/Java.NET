package se.clouds.javanet.app.domain.feature.handler;

import redis.clients.jedis.Jedis;
import se.clouds.javanet.app.domain.feature.command.StoreInCache;
import se.clouds.javanet.core.configuration.Configuration;
import se.clouds.javanet.core.di.Di;
import se.clouds.javanet.core.mediator.IRequestHandler;
import se.clouds.javanet.core.mediator.MediatR;
import se.clouds.javanet.core.mediator.Task;

public class StoreCacheHandler implements IRequestHandler<StoreInCache, Void>
{
    private Jedis jedis;

    public StoreCacheHandler() {
        var host = Configuration.Get("Redis", "Host");
        jedis = new Jedis(host);

        var mediatr = ((MediatR<Void>)Di.GetMediator()); //TODO only one MediatR
        mediatr.addRequestHandler(new StoreInCache(), new HandlerTask(jedis)); //TODO get connection from pool
    }

    public class HandlerTask implements Task<Void>
    {
        private Jedis jedis;

        HandlerTask(Jedis jedis) {
            this.jedis = jedis;
        }

        @Override
        public Void call() throws Exception {
            //jedis.set("shared-cache-0","hello from storeCache");
            return null;
        }
    }
}
