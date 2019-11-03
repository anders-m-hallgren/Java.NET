package se.clouds.app.javanet.core.di;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import se.clouds.app.javanet.core.mediator.IRequest;
import se.clouds.app.javanet.core.mediator.IRequestHandler;

public abstract class Di {
    public static Map<Class<?>, List<Object>> container = new HashMap<Class<?>, List<Object>>();

    public static Object GetSingleton(Class<?> type, Class<?> clazz)
    {
        Object instance = null;
        try {
            instance = container.get(type).stream().findFirst().orElseThrow();
            return instance;
        }
        catch (NullPointerException | NoSuchElementException noElEx)
        {
            try {
                instance = clazz.getConstructor().newInstance();
            }
            catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e)
            {
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

    //transient dedicated Map?
    public void AddTransient(Class<?> type, Object instance){
        if (!container.containsKey(type))
        container.computeIfAbsent(type, k -> new ArrayList<>()).add(instance);
    }
    public static Object GetTransient(Class<?> type) throws NoSuchElementException{
        var classes = container.get(type);
        var classOpt = classes.stream().findFirst();
        var instance = classOpt.orElseThrow();
        return instance;
    }

    public static void Add(Class<?> type, Object instance){
        container.computeIfAbsent(type, k -> new ArrayList<>()).add(instance);
    }

    public static Object Get(Class<?> type) throws NoSuchElementException{
        var classes = container.get(type);
        var classOpt = classes.stream().findFirst();
        var instance = classOpt.orElseThrow();
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
