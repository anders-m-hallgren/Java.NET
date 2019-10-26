package app.domain.handler;

import core.app.Router;
import core.controller.ActionResult;
import core.controller.IActionResult;
import core.di.Di;
import core.mediator.IMediator;
import core.mediator.IRequestHandler;
import core.mediator.Mediatr;
import app.domain.query.GetControllerResult;

public class ControllerResultHandler implements IRequestHandler<GetControllerResult, IActionResult> {

    private Mediatr<IActionResult> mediatr = (Mediatr)Di.GetSingleton(IMediator.class, Mediatr.class);

    public ControllerResultHandler() {
        super();
    }

    public IActionResult Handle(GetControllerResult request) {
        var ctrl = Router.GetController(request.getPath());
        var ctrlResult = (IActionResult) ctrl.Get();
        return ctrlResult;
    }

    public Task RegisterAndPublish(ControllerResultHandler handler, GetControllerResult request) {
        var task = getTask(handler, request);
        mediatr.addRequestObserver(request, task);
        mediatr.Send(request, new ActionResult()); //not needed ActionResult?
        return task;
    }

    public Task getTask(ControllerResultHandler handler, GetControllerResult request) {
        return new Task(handler, request);
    }

    public class Task implements Runnable{
        private ControllerResultHandler handler;
        private String content;
        private GetControllerResult request;
        public Task(ControllerResultHandler handler, GetControllerResult request) {
            super();
            this.handler = handler;
            this.request = request;
        }
        @Override
        public void run() {
            content = handler.Handle(request).GetContent();
        }

        public String getContent() {
            return content;
        }
    }
}
