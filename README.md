# Java.NETcore
Attempt to provide unopinionated aid in language transition between one and the other.
Using clean architecture and design patterns, such as Pipeline, IOC, Mediator and CQRS.
Domain and test driven

https://docs.microsoft.com/en-us/dotnet/core/

## Java and .Net Core (c#) project side by side

## Java App start the .NET Core way

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
## OR
### Run java
> mvn exec:exec
### Run dotnet
> dotnet run

## Docker
> docker build -t java -f java.Dockerfile .  
> docker build -t dotnet -f dotnet.Dockerfile .  
> docker run --name java --rm -p 8080:8080 -dit java  
> docker run --name dotnet --rm -p 80:80 -dit dotnet  
> docker logs -f java  
> docker logs -f dotnet  
  
## OR  
> docker-compose up  
  
## OR  
(docker swarm - docker swarm init)  
> docker stack deploy -c stack.docker.yml app  
> docker service ls  
> docker service logs -f app_dotnet  
> docker service logs -f app_java  
  
point your browser to  
http://localhost:8080/data  
http://localhost  

## Option
### Run Angular/Frontend separatly
> cd ClientApp  
> ng serve  
  
## scale up stacked container
> docker service update --replica 2 app_redis  

## Cleanup
> docker-compose stop  
> docker-compose rm  
> docker stack rm app  
> docker system prune  
