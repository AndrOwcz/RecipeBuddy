FROM openjdk:17
EXPOSE $SPRING_CONTAINER_PORT
WORKDIR ../RecipeBuddy
COPY target/RecipeBuddy-0.0.1-SNAPSHOT.jar .
ENTRYPOINT [ "java", "-jar", "RecipeBuddy-0.0.1-SNAPSHOT.jar" ]