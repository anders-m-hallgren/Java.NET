package se.clouds.javanet.app.controllers;

import se.clouds.javanet.app.domain.feature.command.StoreInCache;
import se.clouds.javanet.app.domain.feature.query.GetFeature;
import se.clouds.javanet.app.domain.feature.query.GetFromCache;
import se.clouds.javanet.core.controller.AppController;
import se.clouds.javanet.core.controller.IActionResult;
import se.clouds.javanet.core.di.Di;
import se.clouds.javanet.core.mediator.MediatR;

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
        mediatr.SendRequest(new StoreInCache());

        var result = mediatr.SendRequest(new GetFeature());
        //var cache = mediatr.SendRequest(new GetFromCache());

        //cache.ifPresent(c -> result.ifPresent(r -> r.SetContent(r.GetContent() + c.GetContent())));
        return result.orElseThrow();
    }
}
