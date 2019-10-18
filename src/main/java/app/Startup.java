package app;

import core.di.IApplication;
import core.di.IServiceCollection;
import app.Controllers.DataController;
import core.Services.EmailService;
import core.service.IMessageService;
import core.service.IShortMessageService;
import core.Services.SmsService;

public class Startup {

    public void ConfigureServices(IServiceCollection.Builder services) {
       services
            .AddSingleton(IMessageService.class, EmailService.class)
            .AddSingleton(IShortMessageService.class, SmsService.class)
            .AddController(DataController.class)

            .AddEmail()
            .AddSms();
    }

    //in order as listed
    public void Configure(IApplication.Builder app){
        app.UseEmail();
        app.UseSms();
    }
}
