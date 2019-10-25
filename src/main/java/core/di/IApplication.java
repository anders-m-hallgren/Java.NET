package core.di;

import java.util.LinkedList;
import java.util.List;

import core.flow.pipeline.IPipeResponse;
import core.flow.pipeline.Step;
import services.EmailService;
import services.SmsService;

public class IApplication {
    public List<Step<IPipeResponse,IPipeResponse>> app = new LinkedList<Step<IPipeResponse,IPipeResponse>>();

    public static class Builder {
        public LinkedList<Step<IPipeResponse,IPipeResponse>> app = new LinkedList<Step<IPipeResponse,IPipeResponse>>();
        private IServiceCollection serviceCollection;

        public Builder(IServiceCollection serviceCollection) {
            this.serviceCollection = serviceCollection;
        }

        //TODO extension methods
        public Builder UseEmail() {
            EmailService.Use(serviceCollection, app);
            return this;
        }

        //TODO extension methods
        public Builder UseSms() {
            SmsService.Use(serviceCollection, app);
            return this;
        }

        public IApplication Build() {
            IApplication app = new IApplication();
            app.app = this.app;
            return app;
        }
    }

    private IApplication() {
    }
}
