package se.clouds.app.javanet.core.app;

import se.clouds.app.javanet.app.Startup;
import se.clouds.app.javanet.core.configuration.Configuration;
import se.clouds.app.javanet.core.configuration.IConfiguration;
import se.clouds.app.javanet.core.controller.IActionResult;
import se.clouds.app.javanet.core.di.Di;
import se.clouds.app.javanet.core.di.IApplication;
import se.clouds.app.javanet.core.di.IServiceCollection;
import se.clouds.app.javanet.core.server.Server;

public class App {

    public static void Run(Startup startup) {
        Build(startup);
        //new FlowEngine().ProcessMsgFlow();
        //ShowPipelineResult();
        //new Mediator();
        //Mediator.attachController()

        //Set controller in run mode if not using HTTP server
        //ShowCtrlResult();
        Server.Run();

    }

    private static void Build(Startup startup) {
        // Configuration
        var configuration = (IConfiguration) Di.GetSingleton(IConfiguration.class, Configuration.class);
        configuration.SetupConfiguration();
        //Di.Show();

        // Services
        IServiceCollection.Builder serviceBuilder = new IServiceCollection.Builder();
        startup.ConfigureServices(serviceBuilder);
        var serviceCollection = serviceBuilder.Build();

        // App
        IApplication.Builder appBuilder = new IApplication.Builder(serviceCollection);
        startup.Configure(appBuilder);
        IApplication app = appBuilder.Build();
        Di.Add(IApplication.class, app);
        
        //Di.Show();
        
    }

    public static void ShowCtrlResult(){
        //if controller is in run mode
        //var writer = new PrintWriter(System.out);
        var result = (IActionResult)Di.Get(IActionResult.class);
        System.out.println("Result from ctrl: " + result.getResponse().getServletResponse().getContent());
    }


}
