package core.service;

import core.di.IServiceCollection;
import core.flow.pipeline.IPipeResponse;
import core.flow.pipeline.Step;

public interface IService extends Step<IPipeResponse, IPipeResponse>{
    IPipeResponse Process(IPipeResponse response);
    IServiceCollection.Builder Add(IServiceCollection.Builder builder);
    //void Use(IServiceCollection serviceCollection, LinkedList<Step<IResponse,IResponse>> app);
    //void setType(Class<?> type);
}
