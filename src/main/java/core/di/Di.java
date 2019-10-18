package core.di;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class Di {
    // TODO add meta data DiClassMeta, DiTypeMeta
    public static Map<Class<?>, Object> container = new HashMap<Class<?>, Object>();

    public static Object GetSingleton(Class<?> type, Class<?> clazz) {
        if (Di.container.containsKey(type))
            return container.get(type);
        else {
            Object instance = null;
            try {
                instance = clazz.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
            var service = (Object)instance;
            Di.container.put(type, service);

            return service;
        }
    }

    public static void Add(Class<?> type, Object instance){
        if (!Di.container.containsKey(type))
            Di.container.put(type, instance);
    }

    public static Object Get(Class<?> type){
        Object instance=null;
        if (Di.container.containsKey(type))
            instance = Di.container.get(type);
        return instance;
    }

/*     Object Get(Class<?> clazz) {
        if (!Di.container.containsKey(clazz))
            return container.get(clazz);
        else {
            Object instance = null;
            try {
                instance = clazz.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
            var service = (Object)instance;
            Di.container.put(clazz, service);

            return service;
        }
    } */

    public static void Show() {
        for (var set : container.keySet()){
            System.out.println(set);
        }
    }
}
