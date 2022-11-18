FROM openjdk:11
COPY controller /src
WORKDIR /src
RUN ["javac","JenkinsExample.java"]
ENTRYPOINT ["java","JenkinsExample"]
