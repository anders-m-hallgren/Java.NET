FROM mcr.microsoft.com/dotnet/core/sdk:3.0-alpine AS build-env
RUN apk -U upgrade -a && apk add libuv nodejs nodejs-npm
WORKDIR /app
COPY *.csproj ./
RUN dotnet restore
COPY . ./
RUN rm -rf ClientApp/node_modules
RUN dotnet publish -c Release -o out

FROM mcr.microsoft.com/dotnet/core/aspnet:3.0-alpine
EXPOSE 443
ENV ASPNETCORE_URLS=https://+
WORKDIR /app
COPY --from=build-env /app/out .

ENTRYPOINT ["dotnet", "Java.NET.dll"]
