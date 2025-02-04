# Use the official Tomcat image
FROM tomcat:8.5-jdk8-openjdk-slim

# Remove the default web apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your WAR file into the Tomcat webapps directory
COPY target/*.war /usr/local/tomcat/webapps/your-app.war

# Expose the port Tomcat runs on
EXPOSE 8080
