# Build exec jar file
mvn package assembly:single

# dependencies
mvn clean dependency:copy-dependencies package assembly:single

## Run
java -jar target/javanetapp-1.0-bundle.jar 

## Docker
docker build -t javanetapp -f java.bundle.Dockerfile .
docker run -p 8080:8080 -it --rm --name javanetapp javanetapp


docker service create --replicas 1 --name infinite-loop <image>
docker service inspect --pretty infinite-loop
docker service scale infinite-loop=2