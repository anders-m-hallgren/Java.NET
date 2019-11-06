package se.clouds.app.javanet.app.domain.feature.handler;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import se.clouds.app.javanet.app.domain.feature.Feature;
import se.clouds.app.javanet.app.domain.feature.query.GetFeature;
import se.clouds.app.javanet.core.controller.ActionResult;
import se.clouds.app.javanet.core.controller.IActionResult;
import se.clouds.app.javanet.core.controller.ResultStatus;
import se.clouds.app.javanet.core.di.Di;
import se.clouds.app.javanet.core.mediator.IRequestHandler;
import se.clouds.app.javanet.core.mediator.MediatR;
import se.clouds.app.javanet.core.mediator.Task;

public class GetFeatureHandler implements IRequestHandler<GetFeature, IActionResult> {

    private MediatR<IActionResult> mediatr = (MediatR)Di.GetMediator();

    public GetFeatureHandler() {
        mediatr.addRequestHandler(new GetFeature(), new HandlerTask());
    }
    public class HandlerTask implements Task<IActionResult>
    {
        @Override
        public IActionResult call() throws Exception {
            System.out.println("\nUser Handler Task");
            var result = new ActionResult();
            JSONArray arr = new JSONArray();

            var list = new ArrayList<Map<String, Object>>();
            list.add(new Feature("javaOrDotnet", true, "Hello from Java").AsMap());
            list.stream().map(feat ->
                arr.put(new JSONObject(feat)))
                    .collect(Collectors.toList());
            result.SetContent(JSONObject.valueToString(arr));
            result.SetStatus(ResultStatus.Status.OK);
            return result;
        }
    }
}
