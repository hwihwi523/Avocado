FROM adoptopenjdk/openjdk11:alpine-jre AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM adoptopenjdk/openjdk11:alpine-jre
COPY --from=builder /build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]