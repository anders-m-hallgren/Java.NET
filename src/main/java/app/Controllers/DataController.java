package app.Controllers;

import app.DataValues;
import core.controller.ActionResult;
import core.controller.AppController;
import core.controller.IActionResult;
import core.controller.ResultStatus;

public class DataController extends AppController {
    private static String routePath = "/data";

    public DataController() {
        super(routePath);
    }

    private static final long serialVersionUID = 9988877L;

    public IActionResult Get() {
        var result = new ActionResult();
        var asJson = DataValues.toJson();

        result.SetContent(asJson);
        result.SetStatus(ResultStatus.Status.OK);

        return result;
    }
}
