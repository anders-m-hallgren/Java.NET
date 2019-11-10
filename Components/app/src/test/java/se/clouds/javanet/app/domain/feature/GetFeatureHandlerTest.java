package se.clouds.javanet.app.domain.feature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import se.clouds.javanet.app.domain.feature.handler.GetFeatureHandler;
import se.clouds.javanet.app.domain.model.SharedFeature;
import se.clouds.javanet.core.controller.ActionResult;

public class GetFeatureHandlerTest {
    private final GetFeatureHandler handler = new GetFeatureHandler();

    @Test
    void FeatureHandlerShouldReturnJavaFeature() throws Exception{

        var result = handler.new HandlerTask().call();

        var jarr = new JSONArray(result.GetContent());
        var jobj = jarr.getJSONObject(0);
        var feature = SharedFeature.FromMap(jobj.toMap());

        assertEquals(ActionResult.class, result.getClass());
        assertEquals(JSONArray.class, jarr.getClass());
        assertEquals(JSONObject.class, jobj.getClass());
        assertEquals("Java", feature.Value);
    }
}
