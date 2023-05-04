FROM openjdk:17-jdk AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJAR -Dorg.gradle.java.home=/usr/lib/jvm/java-17-openjdk-amd64

FROM openjdk:17-jdk
COPY --from=builder build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]