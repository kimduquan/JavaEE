FROM icr.io/appcafe/ibm-semeru-runtimes:open-11-jdk-ubi
ARG KAFKA_VERSION=3.3.1
ARG SCALA_VERSION=2.13
ARG KAFKA_DOWNLOAD_URL=https://downloads.apache.org/kafka/$KAFKA_VERSION/kafka_$SCALA_VERSION-$KAFKA_VERSION.tgz
ARG OPENJ9_SCC=true
ARG VERBOSE=false

# Install Apache Kafka
RUN yum -y install wget \
    && wget $KAFKA_DOWNLOAD_URL \
    && tar -xzf kafka_$SCALA_VERSION-$KAFKA_VERSION.tgz \
    && rm kafka_$SCALA_VERSION-$KAFKA_VERSION.tgz \
	&& mv kafka_$SCALA_VERSION-$KAFKA_VERSION kafka \
    && adduser -u 1001 -r -g root -s /usr/sbin/nologin default \
    && yum -y remove wget \
    && yum clean all \
    && chown -R 1001:0 kafka \
    && chmod -R g+rw kafka

ENV OPENJ9_SCC=$OPENJ9_SCC

#These settings are needed so that we can run as a different user than 1001 after server warmup
ENV RANDFILE=/tmp/.rnd \
    OPENJ9_JAVA_OPTIONS="-XX:+IgnoreUnrecognizedVMOptions -XX:+IdleTuningGcOnIdle -Xshareclasses:name=openj9_system_scc,cacheDir=/opt/java/.scc,readonly,nonFatal -Dosgi.checkConfiguration=false"

USER 1001

EXPOSE 2181 9092

ENTRYPOINT ["kafka/bin/zookeeper-server-start.sh", "config/zookeeper.properties"]
CMD ["kafka/bin/kafka-server-start.sh", "config/server.properties"]
