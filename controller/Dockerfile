FROM openjdk:8
COPY . /src
WORKDIR /src
RUN ["javac","JenkinsExample.java"]
ENTRYPOINT ["java","JenkinsExample"]
