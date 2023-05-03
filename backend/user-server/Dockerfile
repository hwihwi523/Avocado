FROM adoptopenjdk/openjdk17:alpine-jre AS builder
RUN apk add --no-cache git
WORKDIR /app
COPY . .
RUN ./gradlew build -x test

FROM adoptopenjdk/openjdk17:alpine-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
