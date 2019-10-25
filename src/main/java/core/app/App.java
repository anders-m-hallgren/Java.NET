package core.app;

import app.Startup;
import core.configuration.Configuration;
import core.configuration.IConfiguration;
import core.controller.IActionResult;
import core.controller.IController;
import core.di.Di;
import core.di.IApplication;
import core.di.IServiceCollection;
import core.mediator.Mediator;
import core.server.Server;

public class App {

    public static void Run(Startup startup) {
        Build(startup);
        //new FlowEngine().ProcessMsgFlow();
        //ShowPipelineResult();
        new Mediator();
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
        Di.Show();
        // Pipeline
        //var pipe = new Pipeline<IPipeResponse,IPipeResponse>(new FirstStep(new Response()));
        //pipe.Build(app);
    }

    public static void ShowCtrlResult(){
        //if controller is in run mode
        //var writer = new PrintWriter(System.out);
        var result = (IActionResult)Di.Get(IActionResult.class);
        System.out.println("Result from ctrl: " + result.getResponse().getServletResponse().getContent());
    }


}
