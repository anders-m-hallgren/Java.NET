package core.mediator;

import core.app.Router;
import core.controller.ActionResult;
import core.controller.IActionResult;
import core.controller.Response;
import core.flow.FirstStep;
import core.flow.FlowEngine;
import core.flow.pipeline.IPipeResponse;
import core.flow.pipeline.PipeResponse;

public class Mediator implements IMediator {
    private FlowEngine flow;

    //public void send(String message, Colleague colleague);
    public Mediator() {
        super();
        //ctrl = Router.GetController("/data");
        //flow = (FlowEngine<IPipeResponse,IPipeResponse>)Di.Get(FlowEngine.class);
    }
    public void AttachControllerResult(String ctrlCtxPath, IActionResult result){
        //TODO is registered as singleton so make sure seperate threads do the controller processing
        var ctrl = Router.GetController(ctrlCtxPath);
        var ctrlResult = (ActionResult) ctrl.Get();
        result.getResponse().getServletResponse().setContent(ctrlResult.GetContent());
    }

    public void AttachFlowResult(IActionResult result){
        //var flowResult = flow.ProcessPipeFlow(new PipeResponse(), new FirstStep(new Response()));
        var flowResult = new FlowEngine<IPipeResponse,IPipeResponse>()
            .ConstructPipeFlow()
            .ProcessPipeFlow(new PipeResponse(), new FirstStep(new Response()));
        result.SetContent(flowResult.Response());
    }
  }
