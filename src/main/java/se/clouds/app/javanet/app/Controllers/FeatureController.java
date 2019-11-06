package se.clouds.app.javanet.app.Controllers;

import se.clouds.app.javanet.app.domain.feature.query.GetFeature;
import se.clouds.app.javanet.core.controller.AppController;
import se.clouds.app.javanet.core.controller.IActionResult;
import se.clouds.app.javanet.core.di.Di;
import se.clouds.app.javanet.core.mediator.MediatR;

public class FeatureController extends AppController
{
    private MediatR<IActionResult> mediatr = (MediatR)Di.GetMediator();
    private static String routePath = "/feature";
    private static final long serialVersionUID = 998887766L;

    public FeatureController()
    {
        super(routePath, false); //TODO make this mandatory, for now true to include PipeFlowProcessing
    }

    public IActionResult Get()
    {
        System.out.println("\n   --- Controller");
        System.out.println("User Controller: " + this.getClass().getSimpleName());
        var result = mediatr.SendRequest(new GetFeature());
        return result.orElseThrow();
    }
}
