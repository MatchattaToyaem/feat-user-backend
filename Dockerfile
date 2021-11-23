FROM maven:3.6.3-openjdk-15-slim AS build

COPY src /home/app/src

COPY pom.xml /home/app

ENV DATABASE_URL=mongodb://db:27017/

ENV DATABASE=user

ENV ESTIMATION_URL=https://estimation-servece.herokuapp.com/estimate

RUN mvn -f /home/app/pom.xml clean package

FROM adoptopenjdk:12.0.1_12-jdk-openj9-0.14.1

VOLUME /tmp

EXPOSE 8080

COPY --from=build /home/app/target/feat_user-0.0.1-SNAPSHOT.jar feat_user.jar

# RUN apk update && apk add --no-cache libc6-compat

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/feat_user.jar"]