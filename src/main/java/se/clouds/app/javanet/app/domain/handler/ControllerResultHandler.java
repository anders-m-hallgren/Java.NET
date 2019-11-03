package se.clouds.app.javanet.app.domain.handler;

import java.util.Optional;

import se.clouds.app.javanet.app.domain.query.GetControllerResult;
import se.clouds.app.javanet.core.app.Router;
import se.clouds.app.javanet.core.controller.ActionResult;
import se.clouds.app.javanet.core.controller.IActionResult;
import se.clouds.app.javanet.core.di.Di;
import se.clouds.app.javanet.core.mediator.IMediator;
import se.clouds.app.javanet.core.mediator.IRequestHandler;
import se.clouds.app.javanet.core.mediator.Mediatr;

public class ControllerResultHandler implements IRequestHandler<GetControllerResult, IActionResult> {

    private Mediatr<IActionResult> mediatr = (Mediatr)Di.GetSingleton(IMediator.class, Mediatr.class);
    private Task task;

    public ControllerResultHandler() {
        //mediatr.addRequestObserver(requestObject, observer);
        Register(new GetControllerResult());
    }

    public IActionResult Handle(GetControllerResult request) {
        var ctrl = Router.GetController(request.getPath());
        var ctrlResult = (IActionResult) ctrl.Get();
        return ctrlResult;
    }

    /* public Task RegisterAndPublish(ControllerResultHandler handler, GetControllerResult request) {
        task = getTask(handler, request);
        mediatr.addRequestObserver(request, task);
        mediatr.Send(request, new ActionResult()); //not needed ActionResult?
        return task;
    } */

    public void Register(GetControllerResult request) {
        task = new Task(this, request);
        mediatr.addRequestObserver(request, task);
    }

    public void Publish(GetControllerResult request) {
        //var task = getTask(handler, request);
        mediatr.setValue(request, Handle(request)); //not needed ActionResult?
    }

    public Optional<IActionResult> Send(ControllerResultHandler handler, GetControllerResult request) {
        //var task = getTask(handler, request);
        Publish(request);
        var result = mediatr.getValue(request); //not needed ActionResult?
        return result;
    }



    public class Task implements Runnable{
        private ControllerResultHandler handler;
        private IActionResult result;
        private GetControllerResult request;
        public Task(ControllerResultHandler handler, GetControllerResult request) {
            super();
            this.handler = handler;
            this.request = request;
        }
        @Override
        public void run() {
            //result = handler.Handle(request);
            System.out.println("Is observer for ControllerResultHandler");
        }

        public IActionResult GetResult() {
            return result;
        }
    }
}
