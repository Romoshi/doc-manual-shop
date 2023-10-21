FROM gradle:jdk17-alpine as build
WORKDIR /app
COPY . .
RUN ./gradlew bootJar

FROM openjdk:17
WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app
COPY --from=build /app/src/java/main/resources/*.properties /app/prop
COPY --from=build /app/src/java/main/resources/static/files/*.pdf /app/files

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "/app/bot.jar"]