package se.clouds.app.javanet.app.Controllers;

import se.clouds.app.javanet.app.domain.weatherforecast.query.GetWeatherForecast;
import se.clouds.app.javanet.core.controller.AppController;
import se.clouds.app.javanet.core.controller.IActionResult;
import se.clouds.app.javanet.core.di.Di;
import se.clouds.app.javanet.core.mediator.MediatR;

public class WeatherForecastController extends AppController
{
    private MediatR<IActionResult> mediatr = (MediatR)Di.GetMediator();
    private static String routePath = "/weatherforecast";

    public WeatherForecastController()
    {
        super(routePath);
    }

    public IActionResult Get()
    {
        var result = mediatr.SendRequest(new GetWeatherForecast());
        return result.orElseThrow();
    }
}
