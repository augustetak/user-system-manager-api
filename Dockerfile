FROM maven:3.8.2-ibmjava-8-alpine AS build-env
#WORKDIR /app
COPY pom.xml /app/
COPY src /app/src
RUN mvn -Dmaven.test.skip=true -f /app/pom.xml clean package

# Build runtime image.
FROM openjdk:8-jre-slim

# Copy the compiled files over.
COPY --from=build-env /app/target/*.jar /app/app.jar
COPY wait-for-it-81b1373.sh /app/wait-for-it-81b1373.sh
#EXPOSE $SERVER_HOST_PORT
RUN sh -c 'touch /app/app.jar'
RUN sh -c 'chmod +x /app/wait-for-it-81b1373.sh'
ENTRYPOINT ["./app/wait-for-it-81b1373.sh", "mongo-db:27017","--","java","-Dspring.profiles.active=docker","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]
