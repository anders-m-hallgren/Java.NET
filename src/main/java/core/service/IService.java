package core.service;

import core.di.IServiceCollection;
import core.pipeline.IPipeResponse;
import core.pipeline.Step;

public interface IService extends Step<IPipeResponse, IPipeResponse>{
    IPipeResponse Process(IPipeResponse response);
    IServiceCollection.Builder Add(IServiceCollection.Builder builder);
    //void Use(IServiceCollection serviceCollection, LinkedList<Step<IResponse,IResponse>> app);
    //void setType(Class<?> type);
}
