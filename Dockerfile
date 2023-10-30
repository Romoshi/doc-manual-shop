FROM gradle:jdk17-alpine as build
WORKDIR /app
COPY . .
RUN chmod +x gradlew
RUN ./gradlew bootJar

FROM openjdk:17
WORKDIR /app

RUN mkdir -p /app/resources
RUN mkdir -p /app/resources/files

COPY --from=build /app/build/libs/*.jar /app/
COPY --from=build /app/src/main/resources/* /app/resources/

ENTRYPOINT ["java", "-jar", "/app/bot.jar", "--spring.config.location=file:/app/resources/"]