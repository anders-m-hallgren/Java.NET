package se.clouds.app.javanet.app.domain.feature.handler;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import se.clouds.app.javanet.app.domain.feature.Feature;
import se.clouds.app.javanet.app.domain.feature.query.GetFeature;
import se.clouds.app.javanet.core.controller.ActionResult;
import se.clouds.app.javanet.core.controller.IActionResult;
import se.clouds.app.javanet.core.di.Di;
import se.clouds.app.javanet.core.mediator.IMediator;
import se.clouds.app.javanet.core.mediator.IRequestHandler;
import se.clouds.app.javanet.core.mediator.Mediatr;
import se.clouds.app.javanet.core.controller.ResultStatus;


public class GetFeatureHandler implements IRequestHandler<GetFeature, IActionResult> {

    private Mediatr<IActionResult> mediatr = (Mediatr)Di.GetSingleton(IMediator.class, Mediatr.class);

    public GetFeatureHandler() {
        //mediatr.addRequestObserver(requestObject, observer);
        Register(new GetFeature());
    }

    public IActionResult Handle(GetFeature request) {
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

    public void Register(GetFeature request) {
        var task = new Task(request);
        mediatr.addRequestObserver(request, task);
    }

    public void Publish(GetFeature request) {
        //var task = getTask(handler, request);
        mediatr.setValue(request, Handle(request)); //not needed ActionResult?
    }

    public Optional<IActionResult> Send(GetFeature request) {
        //var task = getTask(handler, request);
        Publish(request);
        var result = mediatr.getValue(request); //not needed ActionResult?
        return result;
    }

    public class Task implements Runnable{
        private IActionResult result;
        private GetFeature request;
        public Task(GetFeature request) {
            super();
            this.request = request;
        }
        @Override
        public void run() {
            //result = handler.Handle(request);
            System.out.println("Is observer for GetFeatureHandler");
        }

        public IActionResult GetResult() {
            return result;
        }
    }
}
