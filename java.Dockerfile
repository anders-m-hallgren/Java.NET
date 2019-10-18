#FROM store/oracle/serverjre:7
FROM openjdk:13.0.1-oracle AS build-env
WORKDIR /app
COPY src/ ./src
COPY appsettings*json ./
COPY pom.xml ./
RUN yum install -y wget && wget https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm && rpm -ivh epel-release-latest-7.noarch.rpm
RUN yum install -y unzip zip which
RUN wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
RUN yum install -y apache-maven

#RUN curl -s "https://get.sdkman.io" | bash && source "/root/.sdkman/bin/sdkman-init.sh" && sdk install gradle 5.6.3
RUN mvn compile
ENTRYPOINT ["mvn", "exec:exec"]
# or
#RUN gradle build
#RUN javac app.Program.java

# Build runtime image
## use oracle ? store/oracle/serverjre

#FROM openjdk:13.0.1-oracle
#WORKDIR /app
#COPY --from=build-env /app/target/classes .
#ENTRYPOINT ["java", "app.Program"]
