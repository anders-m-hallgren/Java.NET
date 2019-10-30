package app;

import app.Controllers.WeatherForecastController;
import core.di.IApplication;
import core.di.IServiceCollection;
import core.service.IMessageService;
import core.service.IShortMessageService;
import services.EmailService;
import services.SmsService;

public class Startup {

    public void ConfigureServices(IServiceCollection.Builder services) {
       services
            .AddSingleton(IMessageService.class, EmailService.class)
            .AddSingleton(IShortMessageService.class, SmsService.class)
            .AddController("/weatherforecast", WeatherForecastController.class)

            .AddEmail()
            .AddSms();
    }

    //in order as listed
    public void Configure(IApplication.Builder app){
        app.UseEmail();
        app.UseSms();
    }
}
