package se.clouds.javanet.app.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.xml.catalog.CatalogFeatures.Feature;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import se.clouds.javanet.app.domain.feature.handler.GetFeatureHandler;
import se.clouds.javanet.app.domain.model.SharedFeature;
import se.clouds.javanet.core.controller.ActionResult;
import se.clouds.javanet.core.controller.IActionResult;

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
