package se.clouds.javanet.core.mediator;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import se.clouds.javanet.app.domain.feature.Feature;
import se.clouds.javanet.app.domain.feature.query.GetFeature;
import se.clouds.javanet.app.domain.weatherforecast.WeatherForecast;
import se.clouds.javanet.app.domain.weatherforecast.query.GetWeatherForecast;
import se.clouds.javanet.core.controller.ActionResult;
import se.clouds.javanet.core.controller.IActionResult;
import se.clouds.javanet.core.controller.ResultStatus;
import se.clouds.javanet.core.di.Di;

public class MediatorDemo
{
    public static void main(String[] args)
    {
        var demo = new MediatorDemo();
        var ctrl = demo.new DemoController();
        MediatR<String> mediatrString = new MediatR<String>();
        mediatrString.setValue(GetFeature.class, "hello feature");
        mediatrString.getValue(GetFeature.class).ifPresent(age -> System.out.println("getFeature: " + age));


        mediatrString.addObserver(GetFeature.class, () -> {
            System.out.println("first observer: new feature: " + mediatrString.getValue(GetFeature.class).orElseThrow(RuntimeException::new));
        });
        mediatrString.addObserver(GetFeature.class, () ->  {
            System.out.println("Yet another observer: Executing the Observer task");
            //return "wow got it!";
        });
        /* var callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "wow got it!";
			}
        }; */

        //TODO add multiple mediatr
        MediatR<IActionResult> mediatr = new MediatR<IActionResult>();
        //Di.Add(IMediator.class, mediatr);
        var handlerTask = demo.new DemoHandler().Handle();
        var handlerTask2 = demo.new DemoHandler().Handle2();
        mediatr.addHandler(GetFeature.class, handlerTask);

        MediatR<IActionResult> mediatRequest = new MediatR<IActionResult>();
        Di.Add(IMediator.class, mediatRequest);
        mediatRequest.addRequestHandler(new GetFeature(), handlerTask);
        mediatRequest.addRequestHandler(new GetWeatherForecast(), handlerTask2);


        //var resultFromHandler = mediatr.Send(GetFeature.class).get();
        //.orElseGet(() -> "Error");
        //System.out.println("RESULT:" + resultFromHandler.GetContent());
        /* mediatr.addHandler(GetFeature.class, () ->  {
            System.out.println("Executing the Handler task");
            return "wow got it!";
        }); */

        mediatrString.setValue(GetFeature.class, "hello feature world");

        mediatrString.Show();
        mediatr.Show();

        ctrl.Get();
    }

    public class DemoHandler<T> {
        public Task<T> Handle() {
            return new Task<T>();
        }
        public Task2<T> Handle2() {
            return new Task2<T>();
        }

        public class Task<X> implements Callable<IActionResult>
        {
            public IActionResult result = new ActionResult();
            public Task() {
                super();
            }
            @Override
            public IActionResult call() {
                JSONArray arr = new JSONArray();

                var list = new ArrayList<Map<String, Object>>();
                list.add(new Feature("javaOrDotnet", true, "Hello from Java").AsMap());
                list.stream().map(feat ->
                    arr.put(new JSONObject(feat)))
                        .collect(Collectors.toList());
                //System.out.println("Is observer for GetFeatureHandler");
                result.SetContent(JSONObject.valueToString(arr));
                result.SetStatus(ResultStatus.Status.OK);
                return result;
            }
        }

        public class Task2<X> implements Callable<IActionResult>
        {
            private final String[] Summaries = new String[]
            {
                "Freezing", "Bracing", "Chilly", "Cool", "Mild", "Warm", "Balmy", "Hot", "Sweltering", "Scorching"
            };
            public IActionResult result = new ActionResult();
            public Task2() {
                super();
            }
            @Override
            public IActionResult call() {
                JSONArray arr = new JSONArray();

                new Random().ints(5, -20, 50)
                    .mapToObj(rnd ->
                        arr.put(new JSONObject(
                            new WeatherForecast(rnd, Summaries[new Random().nextInt((Summaries.length))])
                                .AsMap()))).collect(Collectors.toList());
                //System.out.println("Is observer for GetFeatureHandler");
                result.SetContent(JSONObject.valueToString(arr));
                result.SetStatus(ResultStatus.Status.OK);
                return result;
            }
        }
    }

    public class DemoController {

        public void Get()
        {
            //IActionResult result=null;
            //TODO move this to handler
            var mediatr = (MediatR<IActionResult>)Di.GetMediator();
            //var resultFromHandler = mediatr.SendRequest(new GetFeature()).get();
            //System.out.println("RESULT:" + resultFromHandler.GetContent());

        mediatr.SendRequest(new GetWeatherForecast()).ifPresent(v -> System.out.println("WeatherForecast RESULT=" + v.GetContent()));
        mediatr.SendRequest(new GetFeature()).ifPresent(v -> System.out.println("Feature RESULT=" + v.GetContent()));

        }
    }
}
