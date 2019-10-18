FROM mcr.microsoft.com/dotnet/core/sdk:3.0-alpine AS build-env
RUN apk add nodejs nodejs-npm bash python curl --repository=http://dl-cdn.alpinelinux.org/alpine/edge/main
WORKDIR /app
# Copy csproj and restore as distinct layers
COPY *.csproj ./
RUN dotnet restore
# Copy everything else and build
COPY . ./
RUN dotnet publish -c Release -o out

# Build runtime image
FROM mcr.microsoft.com/dotnet/core/aspnet:3.0-alpine
WORKDIR /app
COPY --from=build-env /app/out .

ENTRYPOINT ["dotnet", "Java.NETcore.dll"]
