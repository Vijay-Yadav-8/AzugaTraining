FROM openjdk:11
COPY . /src
WORKDIR /src
RUN ["javac","JenkinsExample.java"]
ENTRYPOINT ["java","JenkinsExample"]
