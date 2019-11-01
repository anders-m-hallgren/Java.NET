FROM openjdk:14-alpine AS BUILD
WORKDIR /build
COPY src/ ./src
COPY assembly.xml ./
COPY pom.xml ./
RUN apk add maven && apk add gradle && mvn package assembly:single

FROM openjdk:14-alpine
WORKDIR /app

COPY appsettings*json ./
COPY myPrivateServerCert.pfx ./
COPY ClientApp/dist ./ClientApp/dist
COPY --from=BUILD /build/target/javanetapp-1.0-bundle.jar ./
COPY target/javanetapp-1.0-bundle.jar ./

ENTRYPOINT ["java", "-jar"]
CMD ["javanetapp-1.0-bundle.jar"]
