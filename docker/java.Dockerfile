FROM openjdk:14-alpine AS build-env
WORKDIR /build
COPY src/ ./src
COPY pom.xml ./
RUN apk add maven
RUN apk add gradle && mvn package assembly:single

FROM openjdk:14-alpine
WORKDIR /app

COPY appsettings*json ./
COPY myPrivateServerCert.pfx ./
COPY ClientApp/dist ./ClientApp/dist
COPY --from=build-env /build/target/javanetapp-1.0-bundle.jar ./
COPY target/javanetapp-1.0-bundle.jar ./

ENTRYPOINT ["java", "-jar"]
CMD ["javanetapp-1.0-bundle.jar"]
