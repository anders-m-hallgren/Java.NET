package se.clouds.app.javanet.core.di;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;

import se.clouds.app.javanet.core.app.Router;
import se.clouds.app.javanet.core.controller.IController;
import se.clouds.app.javanet.core.mediator.IRequest;
import se.clouds.app.javanet.core.mediator.IRequestHandler;
import se.clouds.app.javanet.core.service.IMessageService;
import se.clouds.app.javanet.core.service.IService;
import se.clouds.app.javanet.core.service.IShortMessageService;

public class IServiceCollection {
    public List<IService> services = new LinkedList<IService>();
    private static ServiceLoader<IRequestHandler> handlerLoader = ServiceLoader.load(IRequestHandler.class);
    private static ServiceLoader<IRequest> queryLoader = ServiceLoader.load(IRequest.class);

    public static class Builder {

        public Builder AddSingleton(Class<?> type, Class<?> clazz) {
            Di.GetSingleton(type, clazz);
            return this;
        }

        public <DataController> Builder AddController(String path, Class<? extends IController> clazz) {
            // Di.GetSingleton(IController.class, clazz);
            Router.Add(path, clazz);
            return this;
        }

        // TODO
        public Builder AddTransient(Class<?> type, Class<?> clazz) throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

        // TODO
        public Builder AddScope(Class<?> type, Class<?> clazz) throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

        public List<IService> services = new LinkedList<IService>();

        public Builder AddServiceLoadedHandlers() {
            for (IRequestHandler handler : handlerLoader) {
                Di.Add(IRequestHandler.class, handler);
            }
            return this;
        }
        public Builder AddServiceLoadedQuerys() {
            for (IRequest query : queryLoader) {
                Di.Add(IRequest.class, query);
            }
            return this;
        }
        public Builder AddHandlers(String domainHandlersPackage) {
            Class<?>[] classes=null;
            Object instance = null;
            try {
                classes = ClassDiscoveryHelper.getClasses(domainHandlersPackage);
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
            for(Class<?> clazz : Arrays.asList(classes)){
                try {
                    Constructor<?>[] ctors = clazz.getConstructors();
                    if (ctors == null || ctors.length > 1 || ctors[0].getParameterCount() > 0) //TODO add support
                        continue;
                    instance = ctors[0].newInstance();
                    if (Di.IsOf(IRequestHandler.class, instance))
                        Di.Add(IRequestHandler.class, instance);
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | SecurityException e) {
                    e.printStackTrace();
                }
            }
                //Class.forName("se.clouds.app.javanet.app.domain.handler.FlowResultHandler").getConstructor()
                  //      .newInstance();
            return this;
        }

        //TODO extension methods
        public Builder AddEmail() {
            var service = (IService)Di.GetSingleton(IMessageService.class, null);
            return service.Add(this);
        }

        //TODO extension methods
        public Builder AddSms() {
            var service = (IService)Di.GetSingleton(IShortMessageService.class, null);
            return service.Add(this);
        }

        public IServiceCollection Build() {
            IServiceCollection serviceCollection = new IServiceCollection();
            serviceCollection.services = this.services;
            return serviceCollection;
        }


    }

    private IServiceCollection() {
    }
}
