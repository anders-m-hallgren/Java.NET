package core.di;

import java.util.LinkedList;
import java.util.List;

import core.controller.IController;
import core.service.IMessageService;
import core.service.IService;
import core.service.IShortMessageService;

public class IServiceCollection {
    public List<IService> services = new LinkedList<IService>();

    public static class Builder {

        public Builder AddSingleton(Class<?> type, Class<?> clazz) {
            Di.GetSingleton(type, clazz);
            return this;
        }

        public <DataController> Builder AddController(Class<DataController> clazz) {
            Di.GetSingleton(IController.class, clazz);
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
