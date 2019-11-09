package se.clouds.javanet.app.domain.weatherforecast.handler;

import java.util.Random;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import se.clouds.javanet.app.domain.weatherforecast.WeatherForecast;
import se.clouds.javanet.app.domain.weatherforecast.query.GetWeatherForecast;
import se.clouds.javanet.core.controller.ActionResult;
import se.clouds.javanet.core.controller.IActionResult;
import se.clouds.javanet.core.di.Di;
import se.clouds.javanet.core.mediator.IRequestHandler;
import se.clouds.javanet.core.mediator.MediatR;
import se.clouds.javanet.core.mediator.Task;
import se.clouds.javanet.core.controller.ResultStatus;


public class GetWeatherForecastHandler implements IRequestHandler<GetWeatherForecast, IActionResult> {

    //private MediatR<IActionResult> mediatr = (MediatR)Di.GetSingleton(IMediator.class, MediatR.class);
    private static String[] Summaries = new String[]
    {
        "Freezing", "Bracing", "Chilly", "Cool", "Mild", "Warm", "Balmy", "Hot", "Sweltering", "Scorching"
    };
    public GetWeatherForecastHandler() {
        var mediatr = ((MediatR<IActionResult>)Di.GetMediator());
        mediatr.addRequestHandler(new GetWeatherForecast(), new HandlerTask());
    }

    public class HandlerTask implements Task<IActionResult>
    {
        @Override
        public IActionResult call() throws Exception
        {
            var result = new ActionResult();
            JSONArray arr = new JSONArray();

            new Random().ints(5, -20, 50)
                .mapToObj(rnd ->
                    arr.put(new JSONObject(
                        new WeatherForecast(rnd, Summaries[new Random().nextInt((Summaries.length))])
                            .AsMap()))).collect(Collectors.toList());

            result.SetContent(JSONObject.valueToString(arr));
            result.SetStatus(ResultStatus.Status.OK);
            return result;
        }
    }

}
