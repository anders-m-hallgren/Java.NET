package se.clouds.app.javanet.app.Controllers;

import java.util.Random;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import se.clouds.app.javanet.app.domain.weatherforecast.WeatherForecast;
import se.clouds.app.javanet.app.domain.weatherforecast.query.GetWeatherForecast;
import se.clouds.app.javanet.core.controller.ActionResult;
import se.clouds.app.javanet.core.controller.AppController;
import se.clouds.app.javanet.core.controller.IActionResult;
import se.clouds.app.javanet.core.controller.ResultStatus;
import se.clouds.app.javanet.core.di.Di;
import se.clouds.app.javanet.core.mediator.IMediator;
import se.clouds.app.javanet.core.mediator.MediatR;

public class WeatherForecastController extends AppController
{
    private MediatR<IActionResult> mediatr = (MediatR)Di.GetMediator();
    private static String routePath = "/weatherforecast";
    private static final long serialVersionUID = 998887766L;

    public WeatherForecastController()
    {
        super(routePath, false); //TODO make this mandatory, for now true to include PipeFlowProcessing
    }

    public IActionResult Get()
    {
        System.out.println("\n   --- Controller");
        System.out.println("User Controller: " + this.getClass().getSimpleName());
        var result = mediatr.SendRequest(new GetWeatherForecast());
        return result.orElseThrow();
    }
}
