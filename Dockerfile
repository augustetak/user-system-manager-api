#FROM openjdk:8-jdk-alpine

#VOLUME /tmp

#ARG JAR_FILE=target/*.jar

#COPY ${JAR_FILE} app.jar

#ENTRYPOINT ["java","-jar","/app.jar"]

FROM maven:3.8.2-ibmjava-8-alpine AS build-env
#WORKDIR /app
COPY pom.xml /app/
COPY src /app/src
RUN mvn -Dmaven.test.skip=true -f /app/pom.xml clean package

# Copy the rest of the working directory contents into the container
#COPY . ./

# Compile the application.
#RUN mvn -Dmaven.test.skip=true package

# Build runtime image.
FROM openjdk:8-jre-slim

# Copy the compiled files over.
COPY --from=build-env /app/target/*.jar /app/app.jar
COPY wait-for-it-81b1373.sh /app/wait-for-it-81b1373.sh
#EXPOSE 8088
# Starts java app with debugging server at port 5005.
RUN sh -c 'touch /app/app.jar'
RUN sh -c 'chmod +x /app/wait-for-it-81b1373.sh'
#ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo-db:27018/user-system-manager-db","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]
ENTRYPOINT ["./app/wait-for-it-81b1373.sh", "mongo-db:27017","--","java","-Dspring.profiles.active=docker","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]
