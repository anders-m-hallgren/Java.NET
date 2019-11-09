package se.clouds.javanet.app.controllers;

import se.clouds.javanet.app.domain.feature.command.AddStartupMessage;
import se.clouds.javanet.core.controller.ActionResult;
import se.clouds.javanet.core.controller.AppController;
import se.clouds.javanet.core.controller.IActionResult;
import se.clouds.javanet.core.di.Di;
import se.clouds.javanet.core.mediator.MediatR;
import se.clouds.javanet.core.controller.ResultStatus;

public class HelloController extends AppController
{
    private MediatR<IActionResult> mediatr = (MediatR<IActionResult>)Di.GetMediator();
    private static String routePath = "/hello";

    public HelloController()
    {
        super(routePath);
    }

    public IActionResult Get()
    {
        mediatr.SendRequest(new AddStartupMessage());
        //var cache = mediatr.SendRequest(new GetFromCache());
        var result = new ActionResult();
        result.SetStatus(ResultStatus.Status.OK);
        result.SetContent("");
        //cache.ifPresent(c -> result.ifPresent(r -> r.SetContent(r.GetContent() + c.GetContent())));
        return result;
    }
}
