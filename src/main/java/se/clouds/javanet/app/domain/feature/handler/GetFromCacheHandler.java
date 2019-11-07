package se.clouds.javanet.app.domain.feature.handler;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

import redis.clients.jedis.Jedis;
import se.clouds.javanet.app.domain.feature.Feature;
import se.clouds.javanet.app.domain.feature.command.StoreInCache;
import se.clouds.javanet.app.domain.feature.query.GetFeature;
import se.clouds.javanet.app.domain.feature.query.GetFromCache;
import se.clouds.javanet.core.configuration.Configuration;
import se.clouds.javanet.core.controller.ActionResult;
import se.clouds.javanet.core.controller.IActionResult;
import se.clouds.javanet.core.controller.ResultStatus;
import se.clouds.javanet.core.di.Di;
import se.clouds.javanet.core.mediator.IRequestHandler;
import se.clouds.javanet.core.mediator.MediatR;
import se.clouds.javanet.core.mediator.Task;

public class GetFromCacheHandler implements IRequestHandler<GetFromCache, IActionResult>
{
    private Jedis jedis;
    private MediatR<IActionResult> mediatr = (MediatR)Di.GetMediator();

    public GetFromCacheHandler()
    {
        var host = Configuration.Get("Redis", "Host");
        jedis = new Jedis(host);

        mediatr.addRequestHandler(new GetFromCache(), new HandlerTask(jedis)); //TODO get From pool
    }

    public class HandlerTask implements Task<IActionResult>
    {
        private Jedis jedis;

        HandlerTask(Jedis jedis) {
            this.jedis = jedis;
        }

        @Override
        public IActionResult call() throws Exception {
            var result = new ActionResult();
            var cache = jedis.hget("shared-cache-0", "data");
            if (cache != null) {
                result.SetContent(cache);
                result.SetStatus(ResultStatus.Status.OK);
            }
            else
            {
                jedis.hset("shared-cache-0", "data", "hello from shared cache, set by Java");
                jedis.expire("shared-cache-0", 60 * 5);
            }
            result.SetContent(cache);
            result.SetStatus(ResultStatus.Status.OK);
            return result;
        }
    }
}
