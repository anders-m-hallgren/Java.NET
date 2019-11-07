package se.clouds.javanet.core.service;

import se.clouds.javanet.core.di.IServiceCollection;
import se.clouds.javanet.core.flow.pipeline.IPipeResponse;
import se.clouds.javanet.core.flow.pipeline.Step;

public interface IService extends Step<IPipeResponse, IPipeResponse>{
    IPipeResponse Process(IPipeResponse response);
    IServiceCollection.Builder Add(IServiceCollection.Builder builder);
    //void Use(IServiceCollection serviceCollection, LinkedList<Step<IResponse,IResponse>> app);
    //void setType(Class<?> type);
}
