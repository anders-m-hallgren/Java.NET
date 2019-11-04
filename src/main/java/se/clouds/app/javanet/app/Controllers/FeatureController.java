package se.clouds.app.javanet.app.Controllers;

import se.clouds.app.javanet.app.domain.feature.query.GetFeature;
import se.clouds.app.javanet.core.controller.AppController;
import se.clouds.app.javanet.core.controller.IActionResult;
import se.clouds.app.javanet.core.di.Di;
import se.clouds.app.javanet.core.mediator.IMediator;
import se.clouds.app.javanet.core.mediator.Mediatr;

public class FeatureController extends AppController
{
    private Mediatr<IActionResult> mediatr = (Mediatr)Di.GetSingleton(IMediator.class, Mediatr.class);
    private static String routePath = "/feature";
    private static final long serialVersionUID = 998887766L;

    public FeatureController()
    {
        super(routePath, false); //TODO make this mandatory, for now true to include PipeFlowProcessing
    }

    public IActionResult Get()
    {
        var result = mediatr.getValue(new GetFeature());
        return result.orElseThrow();
    }
}
