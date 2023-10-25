FROM gradle:jdk17-alpine as build
WORKDIR /app
COPY . .
RUN ./gradlew bootJar

FROM openjdk:17
WORKDIR /app

RUN mkdir /app/resources

COPY --from=build /app/build/libs/*.jar /app/
COPY --from=build /app/src/main/resources/* /app/resources/

ENTRYPOINT ["java", "-jar", "/app/bot.jar"]