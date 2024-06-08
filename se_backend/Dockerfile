FROM --platform=$BUILDPLATFORM maven:3.8.5-eclipse-temurin-17 AS development
WORKDIR /web
COPY /se-server/target/se-server-1.0-SNAPSHOT.jar /web/se-server-1.0-SNAPSHOT.jar

FROM development AS dev-envs
CMD ["java", "-jar", "/web/se-server-1.0-SNAPSHOT.jar"]