#
# Set a variable that can be used in all stages.
#
ARG BUILD_HOME=/Cooperative_Pixel_Art_MQ

#
# Gradle image for the build stage.
#
FROM gradle:8.1.1-jdk17-alpine as build-image

#
# Set the working directory.
#
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME
WORKDIR $APP_HOME

#
# Copy the Gradle config, source code, and static analysis config
# into the build container.
#
COPY --chown=gradle:gradle build.gradle.kts settings.gradle.kts $APP_HOME/
COPY --chown=gradle:gradle src $APP_HOME/src
#COPY --chown=gradle:gradle config $APP_HOME/config

#
# Build the application.
#
RUN gradle build
RUN gradle jar

#
# Java image for the application to run in.
#
FROM openjdk:19

#
# Copy the jar file in and name it app.jar.
#
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME
COPY --from=build-image $APP_HOME/build/libs/Cooperative_Pixel_Art_MQ.jar app.jar

#
# The command to run when the container starts.
#
ENTRYPOINT java -Djava.awt.headless=true -jar app.jar