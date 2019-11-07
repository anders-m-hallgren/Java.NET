package se.clouds.javanet.app.domain.feature.handler;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import se.clouds.javanet.app.domain.feature.Feature;
import se.clouds.javanet.app.domain.feature.command.StoreInCache;
import se.clouds.javanet.app.domain.feature.query.GetFeature;
import se.clouds.javanet.app.domain.feature.query.GetFromCache;
import se.clouds.javanet.core.controller.ActionResult;
import se.clouds.javanet.core.controller.IActionResult;
import se.clouds.javanet.core.controller.ResultStatus;
import se.clouds.javanet.core.di.Di;
import se.clouds.javanet.core.mediator.IRequestHandler;
import se.clouds.javanet.core.mediator.MediatR;
import se.clouds.javanet.core.mediator.Task;

public class GetFeatureHandler implements IRequestHandler<GetFeature, IActionResult>
{
    private MediatR<IActionResult> mediatr = (MediatR)Di.GetMediator();

    public GetFeatureHandler()
    {
        mediatr.addRequestHandler(new GetFeature(), new HandlerTask());

    }

    public class HandlerTask implements Task<IActionResult>
    {
        @Override
        public IActionResult call() throws Exception {
            var result = new ActionResult();
            JSONArray arr = new JSONArray();

            var cache = mediatr.SendRequest(new GetFromCache());

            var list = new ArrayList<Map<String, Object>>();
            list.add(new Feature("javaOrDotnet", true, "Hello from Java, shared cache:" + cache.orElseThrow().GetContent()).AsMap());
            list.stream().map(feat ->
                arr.put(new JSONObject(feat)))
                    .collect(Collectors.toList());

            result.SetContent(JSONObject.valueToString(arr));
            result.SetStatus(ResultStatus.Status.OK);

            return result;
        }
    }
}
