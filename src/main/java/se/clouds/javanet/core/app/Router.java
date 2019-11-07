package se.clouds.javanet.core.app;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import se.clouds.javanet.core.controller.IController;

public class Router {

    private static Map<String, IController> container = new HashMap<String, IController>();

    public static void Add(String path, Class<? extends IController> clazz){
        IController instance = null;
        try {
            instance = clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        container.put(path, instance);
    }

    public static IController GetController(String path){
        return container.get(path);
    }

    public static Set<String> GetAllControllersPath(){
        return container.keySet();
    }

    public static Set<Entry<String, IController>> GetAllControllers(){
        return container.entrySet();
    }

    public static void Show(){
        container.entrySet().forEach(r -> System.out.println("Route ctrl: " + r));
    }
}
