FROM gradle:jdk17-alpine as build
WORKDIR /app
COPY . .
RUN ./gradlew bootJar

FROM openjdk:17
WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app

#ВАРИАНТ 1.
COPY --from=build /app/src/main/resources/*.properties /app/prop
#ВАРИАНТ 2.
#COPY --from=build /app/build/resources/*.properties /app
COPY --from=build /app/src/main/resources/static/files/*.pdf /app/files

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "/app/bot.jar"]