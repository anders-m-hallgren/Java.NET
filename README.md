# Java.NET
Attempt to provide unopinionated aid in language transition between one and the other.
Using clean architecture and design patterns, such as Pipeline, IOC, Mediator and CQRS.
Domain and test driven.

Serving SPA over HTTP2

https://docs.microsoft.com/en-us/dotnet/core/

## Java and .NET Core (c#) project side by side

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
            .AddController("/weatherforecast", WeatherForecastController.class)

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

# Docker
> docker build -t java -f java.Dockerfile .  
> docker build -t dotnet -f dotnet.Dockerfile .  
> docker run --name java --rm -p 8080:8080 -dit java  
> docker run --name dotnet --rm -p 80:80 -dit dotnet  
> docker logs -f java  
> docker logs -f dotnet  

## OR
(docker swarm - docker swarm init)  
> docker-compose -f stack.docker.yml build  
> docker stack deploy -c stack.docker.yml app    
>   docker service ls  
>   docker service logs -f app_dotnet  
>   docker service logs -f app_java  
>   docker service ps app_java --no-trunc
>   docker service inspect app_java

point your browser to  
http://localhost:8080  
http://localhost  

## Option
### Run Angular/Frontend separatly
> cd ClientApp  
> ng serve  

## scale up stacked container
> docker service update --replicas 2 app_redis

## Cleanup 
> docker stack rm app  
> docker system prune  

# TLS
## Caution here !!! with your certificates and private keys even in development mode
## Java
### Generate private server self-signed root certificate
> [java]/bin/keytool -genkey   
> -alias myPrivateServer  
> -keyalg EC  
> -keypass changeit   
> -storepass changeit  
> -validity 30  
> -dname "CN=PrivateServer, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown"  
> -ext san=dns:localhost,dns:Anders-Mac.local,ip:127.0.0.1,ip:::1  
> -storetype PKCS12  
> -keystore myPrivateServerCert.pfx   

### Export public server certificate  
> [java]/bin/keytool -export  
> -alias myPrivateServer  
> -storepass changeit  
> -storetype PKCS12   
> -keystore myPrivateServerCert.pfx  
> -file myPublicServer.cer   

### Add public server certificate to client truststore  
> [java]/bin/keytool -import -v -trustcacerts  
> -alias publicClient  
> -file myPublicClient.cer  
> -keypass changeit  
> -storepass changeit  
> -storetype PKCS12  
> -keystore clienttruststore.pfx  

## Option  
### Add to Mac or where you run request (browser)  
drag myPrivateServerCert.pfx to login keychain  
For Certificate in login keychain, setup Trust for SSL  
add publicClient certificate to request or in code setNeedClientAuth(false) 

### Check
> [java]/bin/keytool -printcert -file myPublicServer.cer  
> [java]/bin/keytool -list -v -keystore myPrivateServerCert.pfx  

### Code check
```
var cer = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream ("myPublicServer.cer"));
var expDate = cer.getNotAfter()
```

## .NET Core
### Export developer certificate
dotnet dev-certs https -ep myPrivateServerCert.pfx -p changeit
### Trust developer certificate
dotnet dev-certs https --trust
### code check certificate (X509) for expiry date
```
var cert = X509Certificate.CreateFromCertFile("myPublicServer.cer");
var expDate = cert.GetExpirationDateString();
```

## Cleanup
> remove certificate from keychain  
> docker stack rm app  
> rm myPrivateServerCert.pfx myPublicServer.cer clienttruststore.pfx
