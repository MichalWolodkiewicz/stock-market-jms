FROM ubuntu:18.04
RUN apt-get update && apt-get install -y openjdk-8-jre
COPY target/stock-consumer-0.0.1-SNAPSHOT.jar /tmp
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/tmp/stock-consumer-0.0.1-SNAPSHOT.jar"]
