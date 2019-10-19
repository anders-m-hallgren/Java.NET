package core.app;

import core.di.Di;
import core.di.IApplication;
import core.di.IServiceCollection;
import app.Startup;
import core.configuration.Configuration;
import core.configuration.IConfiguration;
import core.controller.IActionResult;
import core.controller.IController;
import core.controller.IResponse;
import core.pipeline.Pipeline;
import core.server.Server;

public class App {

    public static void Run(Startup startup) {
        Build(startup);
        Pipeline.Start();
        ShowPipelineResult();
        SetupController();
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

        // Pipeline
        Pipeline.Build(app);
    }

    private static void SetupController() {
        IController ctrl = (IController) Di.Get(IController.class);
        //Di.Show();
        Router.Add(ctrl.getRoutePath(), ctrl);
        // controllers should perhaps be pub/sub or observers
        //controller init then send request to pipeline then doPost
        ctrl.init();

        //ctrl.service();

        //Run mode
        //var result = ctrl.Get(); //Reflection?
        //ctrl.postService();
        //Di.Add(IActionResult.class, result);
    }

    public static void ShowCtrlResult(){
        //if controller is in run mode
        //var writer = new PrintWriter(System.out);
        var result = (IActionResult)Di.Get(IActionResult.class);
        System.out.println("Result from ctrl: " + result.getResponse().getServletResponse().getContent());
    }

    public static void ShowPipelineResult(){
        //var writer = new PrintWriter(System.out);
        var response = (IResponse)Di.Get(IResponse.class);
        System.out.println("Result from pipeline: " + response.getServletResponse().getContent());
    }
}
