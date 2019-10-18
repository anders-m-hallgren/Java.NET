package app.Controllers;

import core.controller.ActionResult;
import core.controller.AppController;
import core.controller.IActionResult;

public class ErrorController extends AppController {
    private static String routePath = "/error";
    public ErrorController() {
        super(routePath);
        // TODO Auto-generated constructor stub
    }

    private static final long serialVersionUID = 9988877L;

    public IActionResult Get() {
        return new ActionResult();
    }

    @Override
    public String getRoutePath() {
        return null;
    }
}
