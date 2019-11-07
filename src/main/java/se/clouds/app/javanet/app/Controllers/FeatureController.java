package se.clouds.app.javanet.app.Controllers;

import java.util.Base64.Encoder;

import se.clouds.app.javanet.app.domain.feature.query.GetFeature;
import se.clouds.app.javanet.core.controller.AppController;
import se.clouds.app.javanet.core.controller.IActionResult;
import se.clouds.app.javanet.core.di.Di;
import se.clouds.app.javanet.core.mediator.MediatR;

public class FeatureController extends AppController
{
    private MediatR<IActionResult> mediatr = (MediatR<IActionResult>)Di.GetMediator();
    private static String routePath = "/feature";

    public FeatureController()
    {
        super(routePath);
    }

    public IActionResult Get()
    {
        var result = mediatr.SendRequest(new GetFeature());
        return result.orElseThrow();
    }
}
