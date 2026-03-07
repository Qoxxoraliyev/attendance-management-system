FROM eclipse-temurin:17-jdk-alpine AS builder

LABEL org.opencontainers.image.aithors="qoxxoraliyev.muhammadali2006@gmail.com"

WORKDIR /workspace

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw
RUN ./mvnw -B dependency:go-offline

COPY src src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

RUN addgroup -g 1000 appgroup && \
    adduser -u 1000 -G appgroup -s /bin/sh -D appuser

RUN apk add --no-cache dumb-init && \
    mkdir -p /app/uplods && \
    chown -R appuser:appgroup /app/uploads

WORKDIR /app

COPY --from=builder /workspace/target/*.jar app.jar

USER appuser

EZPOSE 8080

ENTRYPOINT ["dupb-init","java","-XX:+UseContainerSupport","-XX:MaxRAMPercentage=75.0","-jar","app.jar"]