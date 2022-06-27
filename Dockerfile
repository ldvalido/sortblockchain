FROM openjdk:17-alpine
WORKDIR /app
COPY ./ build/libs/challenge-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java","-jar", "challenge-0.0.1-SNAPSHOT.jar"]