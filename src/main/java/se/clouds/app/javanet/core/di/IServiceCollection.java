package se.clouds.app.javanet.core.di;

import java.util.LinkedList;
import java.util.List;

import se.clouds.app.javanet.core.app.Router;
import se.clouds.app.javanet.core.controller.IController;
import se.clouds.app.javanet.core.service.IMessageService;
import se.clouds.app.javanet.core.service.IService;
import se.clouds.app.javanet.core.service.IShortMessageService;

public class IServiceCollection {
    public List<IService> services = new LinkedList<IService>();

    public static class Builder {

        public Builder AddSingleton(Class<?> type, Class<?> clazz) {
            Di.GetSingleton(type, clazz);
            return this;
        }

        public <DataController> Builder AddController(String path, Class<? extends IController> clazz) {
            //Di.GetSingleton(IController.class, clazz);
            Router.Add(path, clazz);
			return this;
        }

        //TODO
        public Builder AddTransient(Class<?> type, Class<?> clazz) throws UnsupportedOperationException{
            throw new UnsupportedOperationException();
        }

        //TODO
        public Builder AddScope(Class<?> type, Class<?> clazz) throws UnsupportedOperationException{
            throw new UnsupportedOperationException();
        }

        public List<IService> services = new LinkedList<IService>();

        //TODO extension methods
        public Builder AddEmail() {
            var service = (IService)Di.container.get(IMessageService.class);
            return service.Add(this);
        }

        //TODO extension methods
        public Builder AddSms() {
            return ((IService)Di.container.get(IShortMessageService.class)).Add(this);
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
