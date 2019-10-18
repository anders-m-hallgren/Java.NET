package core.app;

import java.util.HashMap;
import java.util.Map;

import core.controller.IController;

/**
 * Router
 */
public class Router {
    private static Map<String, IController> container = new HashMap<String, IController>();

    public static void Add(String path, IController controller){
        container.put(path, controller);
    }
    public static IController GetController(String path){
        return container.get(path);
    }
}
