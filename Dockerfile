FROM adoptopenjdk/openjdk11:alpine-slim

ENV GRADLE_VERSION=7.3

RUN apk add --no-cache curl && \
    curl -L https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -o /tmp/gradle.zip && \
    unzip /tmp/gradle.zip -d /usr/local && \
    rm -rf /tmp/gradle.zip && \
    ln -s /usr/local/gradle-${GRADLE_VERSION}/bin/gradle /usr/bin/gradle

COPY . /usr/src/app
WORKDIR /usr/src/app

RUN gradle build

CMD ["java", "-jar", "build/libs/PlaytoxTest-1.0-SNAPSHOT.jar"]
