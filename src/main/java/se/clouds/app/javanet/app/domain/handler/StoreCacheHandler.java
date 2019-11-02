package se.clouds.app.javanet.app.domain.handler;

import se.clouds.app.javanet.app.domain.command.StoreInCache;
import se.clouds.app.javanet.core.di.Di;
import se.clouds.app.javanet.core.mediator.IMediator;
import se.clouds.app.javanet.core.mediator.IRequestHandler;
import se.clouds.app.javanet.core.mediator.Mediatr;

public class StoreCacheHandler implements IRequestHandler<StoreInCache, Void> {
//TODO how many Mediatr instances?
    private Mediatr<Void> mediatr = (Mediatr)Di.GetSingleton(IMediator.class, Mediatr.class);

    public StoreCacheHandler() {}

    public Void Handle(StoreInCache request) {
        return null;
        //return flowResult;
    }

    public Task RegisterAndPublish(StoreCacheHandler handler, StoreInCache request) {
        var task = new Task(handler, request);
        mediatr.addRequestObserver(request, task);
        mediatr.Send(request, null); //not needed ActionResult?
        return task;
    }

    public class Task implements Runnable{
        private StoreCacheHandler handler;
        private StoreInCache request;
        public Task(StoreCacheHandler handler, StoreInCache request) {
            super();
            this.handler = handler;
            this.request = request;
        }
        @Override
        public void run() {
            System.out.println("Implement store in cache here, send to Redis");
            //content = handler.Handle(request);
        }
    }
}
