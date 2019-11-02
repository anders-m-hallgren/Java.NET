package se.clouds.app.javanet.core.di;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import se.clouds.app.javanet.core.mediator.IRequest;
import se.clouds.app.javanet.core.mediator.IRequestHandler;

public abstract class Di {
    // TODO add meta data DiClassMeta, DiTypeMeta
    //public static Map<Class<?>, Object> container = new HashMap<Class<?>, Object>();
    public static Map<Class<?>, List<Object>> container = new HashMap<Class<?>, List<Object>>();

    public static Object GetSingleton(Class<?> type, Class<?> clazz) {
        if (container.containsKey(type))
            return ((List)container.get(type)).get(0);
        else {
            Object instance = null;
            try {
                instance = clazz.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
            container.computeIfAbsent(type, k -> new ArrayList<>()).add(instance);

            return instance;
        }
    }

    public static void AddSingleton(Class<?> type, Object instance){
        if (!container.containsKey(type))
        container.computeIfAbsent(type, k -> new ArrayList<>()).add(instance);
    }

    public static void Add(Class<?> type, Object instance){
        //container.put(type, instance);
        container.computeIfAbsent(type, k -> new ArrayList<>()).add(instance);
    }

    public static Object Get(Class<?> type){
        Object instance=null;
        if (container.containsKey(type))
            instance = ((List)container.get(type)).get(0); //TODO multiple for transient and scope
        return instance;
    }

    public static boolean IsOf(Class<?> clazz, Object obj){
        return clazz.isInstance(obj);
    }

    public static IRequest GetQuery(Class<?> type, Class<? extends IRequest> filterQuery){
        IRequest query=null;
        if (container.containsKey(type)){
            var instances = container.entrySet();
            Optional<List<Object>> queriesOpt = instances.stream()
                .filter(i -> i.getKey() == type)
                .map(i -> i.getValue())
                .findFirst();
            var queries = queriesOpt.orElseThrow();
            Optional<Object> queryOpt = queries.stream()
                .filter(h -> IsOf(filterQuery,h))
                .findFirst();
            query = (IRequest)queryOpt.orElseThrow();
        }
        return query;
    }

    public static IRequestHandler GetHandler(Class<?> type, Class<? extends IRequestHandler> filterHandler){
        IRequestHandler handler=null;
        if (container.containsKey(type)){
            var instances = container.entrySet();
            Optional<List<Object>> handlersOpt = instances.stream()
                .filter(i -> i.getKey() == type)
                .map(i -> i.getValue())
                .findFirst();
            var handlers = handlersOpt.orElseThrow();
            Optional<Object> handlerOpt = handlers.stream()
                .filter(h -> IsOf(filterHandler,h))
                .findFirst();
            handler = (IRequestHandler)handlerOpt.orElseThrow();
        }
        return handler;
    }

    public static void Show() {
        for (var set : container.entrySet()){
            System.out.println(set.getKey() + ":" + set.getValue());
        }
    }
}
