package se.clouds.app.javanet.app;

import se.clouds.app.javanet.app.Controllers.FeatureController;
import se.clouds.app.javanet.app.Controllers.WeatherForecastController;
import se.clouds.app.javanet.core.di.IApplication;
import se.clouds.app.javanet.core.di.IServiceCollection;
import se.clouds.app.javanet.core.service.IMessageService;
import se.clouds.app.javanet.core.service.IShortMessageService;
import se.clouds.app.javanet.services.EmailService;
import se.clouds.app.javanet.services.SmsService;

public class Startup {

    public void ConfigureServices(IServiceCollection.Builder services) {
       services
            .AddSingleton(IMessageService.class, EmailService.class)
            .AddSingleton(IShortMessageService.class, SmsService.class)
            .AddController("/weatherforecast", WeatherForecastController.class)
            .AddController("/feature", FeatureController.class)

            .AddEmail()
            .AddSms();
    }

    //in order as listed
    public void Configure(IApplication.Builder app){
        app.UseEmail();
        app.UseSms();
    }
}
