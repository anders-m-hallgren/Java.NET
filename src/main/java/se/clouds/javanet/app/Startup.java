package se.clouds.javanet.app;

import se.clouds.javanet.app.controllers.FeatureController;
import se.clouds.javanet.app.controllers.WeatherForecastController;
import se.clouds.javanet.core.di.IApplication;
import se.clouds.javanet.core.di.IServiceCollection;
import se.clouds.javanet.core.service.IMessageService;
import se.clouds.javanet.core.service.IShortMessageService;
import se.clouds.javanet.services.EmailService;
import se.clouds.javanet.services.SmsService;

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
