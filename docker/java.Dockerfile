FROM openjdk:12-alpine AS build-env
WORKDIR /appbuild
COPY . ./
RUN ./gradlew build install

FROM openjdk:14-alpine
WORKDIR /app
COPY appsettings*json ./
COPY myPrivateServerCert.pfx ./
COPY ClientApp/dist ./ClientApp/dist
COPY --from=build-env ["appbuild/build/install/Java.NET", "./"]
ENTRYPOINT ["bin/Java.NET"]
