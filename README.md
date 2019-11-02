# Java.NET
Attempt to provide unopinionated aid in language transition between one and the other.
Using clean architecture and design patterns, such as Pipeline, IOC, Mediator and CQRS.
Domain and test driven.

Serving SPA over HTTP2

https://docs.microsoft.com/en-us/dotnet/core/

### Stack & tools
Java13, .NETcore3 C#8, Angular8, Gradle6, Maven3, Docker3, Jetty9, Redis5, Tinkerpop3, Alpine3

### Run
> docker stack deploy -c stack.docker.yml app  

> point your browser to  
> https://localhost:8080   
> https://localhost  


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
### Run Java
> mvn exec:exec
### Run .NET
> dotnet run

# Docker
(docker swarm - docker swarm init)  
> docker-compose -f stack.docker.yml build  
> docker stack deploy -c stack.docker.yml app  

point your browser to  
https://localhost:8080  
https://localhost  

## Docker hints
#### check
> - docker service ls  
> - docker service logs -f app_dotnet  
> - docker service logs -f app_java  
> - docker service ps app_java --no-trunc  
> - docker service inspect app_java  
> - docker run -it --entrypoint /bin/bash $IMAGE_NAME -s  

#### build & run
> - docker build -t javanetapp -f docker/java.Dockerfile 
> - docker build -t dotnetapp -f docker/dotnet.Dockerfile  
> - docker run -it --rm --name javanetapp -p 8080:8080 javanetapp
> - docker run -it --rm --name dotnetapp -p 80:80 -p 443:443 dotnetapp

## Option
### Run Angular/Frontend separatly
> cd ClientApp  
> ng serve  
### Run Java server with Jetty
> docker run --name jetty --rm -v war:/var/lib/jetty -v [local path to wars]:/myvol vol -it -p 80:8080 jetty:9-jre11  
> cp /myvol/webapps/*.war webapps  
point your browser to  
http://localhost/app-1.0/hello  
## Gradle
> ./gradlew run  
### OR
> ./gradlew install  
> build/install/Java.NETcore/bin/Java.NETcore

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
