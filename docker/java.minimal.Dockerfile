FROM openjdk:12
WORKDIR /app
COPY src/main/java/se/clouds/app/javanet/SocketServer.java ./se/clouds/app/javanet/
RUN javac se/clouds/app/javanet/SocketServer.java
EXPOSE 25000
ENTRYPOINT ["java"]
CMD ["se.clouds.app.javanet.SocketServer"]