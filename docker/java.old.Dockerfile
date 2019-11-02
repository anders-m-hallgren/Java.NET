#FROM store/oracle/serverjre:7
FROM openjdk:13.0.1-oracle AS build-env
WORKDIR /build
COPY src/ ./src
COPY assembly.xml ./
COPY pom.xml ./
RUN yum install -y wget && wget https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm && rpm -ivh epel-release-latest-7.noarch.rpm
RUN yum install -y unzip zip which
RUN wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
RUN yum install -y apache-maven
#RUN curl -s "https://get.sdkman.io" | bash && source "/root/.sdkman/bin/sdkman-init.sh" && sdk install gradle 5.6.3
#RUN mvn compile
#RUN mvn dependency:go-offline -B
#RUN mvn install
RUN mvn package assembly:single
#ENTRYPOINT ["mvn", "exec:exec"]
# or
#RUN gradle build
#RUN javac app.Program.java

# Build runtime image
## use oracle ? store/oracle/serverjre

FROM openjdk:13.0.1-oracle
WORKDIR /app
EXPOSE 8080
COPY appsettings*json ./
COPY myPrivateServerCert.pfx ./
COPY ClientApp/dist ./ClientApp/dist
#COPY --from=build-env /app/target/classes .
COPY --from=build-env /build/target/app-1.0-bundle.jar ./
# DEBUG add
# -Dorg.eclipse.jetty.util.log.class=org.eclipse.jetty.util.log.StdErrLog -Dorg.eclipse.jetty.LEV=DEBUG
#ENTRYPOINT ["java", "-jar", "app-1.0-bundle.jar"]
RUN jar -xvf app-1.0-bundle.jar
ENTRYPOINT ["java", "-classpath", "./:WEB-INF/classes/", "app.Program"]
#ENTRYPOINT ["mvn", "exec:exec"]
#ENTRYPOINT ["java", "-version"]
