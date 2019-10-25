package core.app;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import core.controller.AppController;
import core.controller.IController;

/**
 * Router
 */
public class Router {

    private static Map<String, IController> container = new HashMap<String, IController>();

    public static void Add(String path, IController controller){
        container.put(path, controller);
    }

    public static void Add(String path, Class<? extends IController> clazz){
        IController instance = null;
        try {
            instance = clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        //var path=instance.getRoutePath();
        container.put(path, instance);
    }

    public static IController GetController(String path){
        return container.get(path);
    }

    public static Set<String> GetAllControllers(){
        return container.keySet();
    }
}
