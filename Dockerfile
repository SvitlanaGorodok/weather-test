# build stage
FROM maven:3.8.5-openjdk-17-slim AS builder
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
COPY . .
RUN mvn clean package

#app package stage
FROM openjdk:17
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar
EXPOSE 8081
CMD ["java", "-Xms64m", "-Xmx64m", "-jar", "/app/app.jar"]
