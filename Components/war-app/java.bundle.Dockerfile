FROM openjdk:13.0.1-oracle
WORKDIR /app
COPY appsettings*json ./
COPY myPrivateServerCert.pfx ./
COPY ClientApp/dist ./ClientApp/dist
COPY target/app-1.0-bundle.jar ./
# DEBUG add
# -Dorg.eclipse.jetty.util.log.class=org.eclipse.jetty.util.log.StdErrLog -Dorg.eclipse.jetty.LEV=DEBUG
ENTRYPOINT ["java", "-jar", "app-1.0-bundle.jar"]
#RUN jar -xvf app-1.0-bundle.jar
#ENTRYPOINT ["java", "-classpath", "./:WEB-INF/classes/", "app.Program"]
#ENTRYPOINT ["mvn", "exec:exec"]
#ENTRYPOINT ["java", "-version"]
