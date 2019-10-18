# Java.NETcore
Attempt to provide unappinionated aid in language transition between one and the other.
Using clean architecture and design patterns, such as Pipeline, IOC, Mediator and CQRS.
Domain and test driven

https://docs.microsoft.com/en-us/dotnet/core/

## Java and .Net Core (c#) project side by side

## Java App start, .NET Core way

### Program.java
```
public class Program {
    public static void main(String[] args) {
        App.Run(new Startup());
    }
}
```
### Startup.java
```
public class Startup {
    public void ConfigureServices(IServiceCollection.Builder services) {
       services
            .AddSingleton(IMessageService.class, EmailService.class)
            .AddSingleton(IShortMessageService.class, SmsService.class)
            .AddController(DataController.class)

            .AddEmail()
            .AddSms();
    }
    public void Configure(IApplication.Builder app){
        app.UseEmail();
        app.UseSms();
    }
}
```
## IDE
with VS code, hit F5
### OR
## Run java
mvn test

## Run dotnet
dotnet run
