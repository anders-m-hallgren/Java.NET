package se.clouds.app.javanet.app.domain.command;

import se.clouds.app.javanet.core.mediator.IRequest;

public class StoreInCache implements IRequest<Void> {
    public StoreInCache() {
        System.out.println("Is a command, make handler ready to store in cache");
    }
}
