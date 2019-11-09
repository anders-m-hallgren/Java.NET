FROM openjdk:14-alpine AS build-env
RUN apk add maven gradle
WORKDIR /build

COPY Components/shared ./Components/shared
COPY Components/core ./Components/core

COPY Components/app/src/main/java ./Components/app/src/main/java
COPY Components/app/src/main/resources ./Components/app/src/main/resources
COPY Components/app/pom.xml ./Components/app/pom.xml

COPY Components/pom.xml ./Components/pom.xml
COPY assembly.xml ./assembly.xml
COPY pom.xml ./pom.xml

RUN mvn -T 3 package assembly:single

FROM openjdk:14-alpine
WORKDIR /app

COPY appsettings*json ./
COPY myPrivateServerCert.pfx ./
COPY ClientApp/dist ./ClientApp/dist
COPY --from=build-env /build/Components/app/target/Java.NET-1.0-bundle.jar ./
RUN ls
ENTRYPOINT ["java", "-jar"]
CMD ["Java.NET-1.0-bundle.jar"]
