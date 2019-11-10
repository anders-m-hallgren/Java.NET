FROM openjdk:12-alpine AS build-env
WORKDIR /build
COPY . ./
RUN ./gradlew build install

FROM openjdk:14-alpine
WORKDIR /app
COPY appsettings*json ./
COPY myPrivateServerCert.pfx ./
COPY ClientApp/dist ./ClientApp/dist
COPY --from=build-env /build/Components/app/build/install/app ./
ENTRYPOINT ["bin/app"]
