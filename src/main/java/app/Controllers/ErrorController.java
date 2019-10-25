package app.Controllers;

import core.controller.ActionResult;
import core.controller.AppController;
import core.controller.IActionResult;
import core.controller.ResultStatus;

public class ErrorController extends AppController {
    private static String routePath = "/error";
    public ErrorController() {
        super(routePath);
    }

    private static final long serialVersionUID = 9988866L;

    public IActionResult Get() {
        var result = new ActionResult();

        result.SetContent("Error");
        result.SetStatus(ResultStatus.Status.OK);

        return result;
    }
}
