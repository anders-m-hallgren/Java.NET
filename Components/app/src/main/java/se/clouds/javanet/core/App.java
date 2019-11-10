package se.clouds.javanet.core;

import se.clouds.javanet.app.Startup;
import se.clouds.javanet.core.configuration.Configuration;
import se.clouds.javanet.core.configuration.IConfiguration;
import se.clouds.javanet.core.di.Di;
import se.clouds.javanet.core.di.IApplication;
import se.clouds.javanet.core.di.IServiceCollection;
import se.clouds.javanet.core.server.Server;

public class App
{
    private static boolean trace = false;

    public static void Run(Startup startup)
    {
        Build(startup);

        Server.Run();
    }

    private static void Build(Startup startup)
    {
        // Configuration
        var configuration = (IConfiguration) Di.GetSingleton(IConfiguration.class, Configuration.class);
        configuration.SetupConfiguration();

        // Services
        IServiceCollection.Builder serviceBuilder = new IServiceCollection.Builder();
        startup.ConfigureServices(serviceBuilder);

        //ServiceLoad Handler, Request, Notification
        var serviceCollection = serviceBuilder
            .AddHandlers("se.clouds.javanet.core.app.handler")
            .AddServiceLoadedHandlers()
            .AddServiceLoadedQuerys()
            .AddServiceLoadedNotifications()
            .Build();

        // App
        IApplication.Builder appBuilder = new IApplication.Builder(serviceCollection);
        startup.Configure(appBuilder);
        IApplication app = appBuilder.Build();

        Di.AddSingleton(IApplication.class, app);

        if(App.trace) Di.Show();

    }
}
