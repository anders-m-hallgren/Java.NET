FROM mcr.microsoft.com/dotnet/core/sdk:3.0-alpine AS build-env
RUN apk -U upgrade -a && apk add libuv nodejs nodejs-npm bash python curl
WORKDIR /app
# Copy csproj and restore as distinct layers
COPY *.csproj ./
RUN dotnet restore
# Copy everything else and build
COPY . ./
#RUN rm -rf ClientApp/node_modules
RUN dotnet publish -c Release -o out

# Build runtime image
FROM mcr.microsoft.com/dotnet/core/aspnet:3.0-alpine
EXPOSE 80
EXPOSE 443
ENV ASPNETCORE_URLS=https://+;http://+
WORKDIR /app
COPY --from=build-env /app/out .

ENTRYPOINT ["dotnet", "Java.NETcore.dll"]
