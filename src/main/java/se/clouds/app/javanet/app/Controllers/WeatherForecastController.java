package se.clouds.app.javanet.app.Controllers;

import java.util.Random;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import se.clouds.app.javanet.app.WeatherForecast;
import se.clouds.app.javanet.core.controller.ActionResult;
import se.clouds.app.javanet.core.controller.AppController;
import se.clouds.app.javanet.core.controller.IActionResult;
import se.clouds.app.javanet.core.controller.ResultStatus;

public class WeatherForecastController extends AppController
{
    private static String routePath = "/weatherforecast";
    private static final long serialVersionUID = 998887766L;
    private static String[] Summaries = new String[]
    {
        "Freezing", "Bracing", "Chilly", "Cool", "Mild", "Warm", "Balmy", "Hot", "Sweltering", "Scorching"
    };

    public WeatherForecastController()
    {
        super(routePath, false); //TODO make this mandatory, for now true to include PipeFlowProcessing
    }


    public IActionResult Get()
    {
        var result = new ActionResult();
        var asJson = ToJson();
        System.out.println(asJson);
        result.SetContent(asJson);
        result.SetStatus(ResultStatus.Status.OK);

        return result;
    }

    public static String ToJson()
    {
        JSONArray arr = new JSONArray();

        new Random().ints(5, -20, 50)
            .mapToObj(rnd ->
                arr.put(new JSONObject(
                    new WeatherForecast(rnd, Summaries[new Random().nextInt((Summaries.length))])
                        .AsMap()))).collect(Collectors.toList());

        return JSONObject.valueToString(arr);
    }
}
