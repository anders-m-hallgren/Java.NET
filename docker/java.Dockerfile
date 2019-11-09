FROM openjdk:14-alpine AS build-env
RUN apk add maven gradle
WORKDIR /build
COPY core/src/main/java ./core/src/main/java
COPY core/src/main/resources ./core/src/main/resources
COPY core/src/assembly.xml ./core/src/assembly.xml
COPY core/pom.xml ./core/pom.xml
COPY shared/src/main/java ./shared/src/main/java
COPY shared/pom.xml ./shared/pom.xml
COPY pom.xml ./
RUN mvn package assembly:single

FROM openjdk:14-alpine
WORKDIR /app

COPY appsettings*json ./
COPY myPrivateServerCert.pfx ./
COPY ClientApp/dist ./ClientApp/dist
COPY --from=build-env /build/target/Java.NET-1.0-bundle.jar ./

ENTRYPOINT ["java", "-jar"]
CMD ["Java.NET-1.0-bundle.jar"]
