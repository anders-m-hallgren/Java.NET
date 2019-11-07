package se.clouds.app.javanet.core.app;

import se.clouds.app.javanet.app.Startup;
import se.clouds.app.javanet.core.configuration.Configuration;
import se.clouds.app.javanet.core.configuration.IConfiguration;
import se.clouds.app.javanet.core.di.Di;
import se.clouds.app.javanet.core.di.IApplication;
import se.clouds.app.javanet.core.di.IServiceCollection;
import se.clouds.app.javanet.core.server.Server;

public class App
{
    public static void Run(Startup startup) {
        Build(startup);

        Server.Run();
    }

    private static void Build(Startup startup) {
        // Configuration
        var configuration = (IConfiguration) Di.GetSingleton(IConfiguration.class, Configuration.class);
        configuration.SetupConfiguration();

        // Services
        IServiceCollection.Builder serviceBuilder = new IServiceCollection.Builder();
        startup.ConfigureServices(serviceBuilder);

        //ServiceLoad Handler, Request, Notification
        var serviceCollection = serviceBuilder
            .AddHandlers("se.clouds.app.javanet.core.app.handler")
            .AddServiceLoadedHandlers()
            .AddServiceLoadedQuerys()
            .AddServiceLoadedNotifications()
            .Build();

        //TODO for core load directly instead
        // serviceBuilder.AddHandlers("se.clouds.app.javanet.app.domain.handler");

        // App
        IApplication.Builder appBuilder = new IApplication.Builder(serviceCollection);
        startup.Configure(appBuilder);
        IApplication app = appBuilder.Build();

        Di.AddSingleton(IApplication.class, app);

    }
}
