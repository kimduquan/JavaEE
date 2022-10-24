FROM adoptopenjdk/openjdk8-openj9:latest

RUN groupadd -r jboss -g 1000 && useradd -u 1000 -r -g jboss -m -d /opt/jboss -s /sbin/nologin -c "JBoss user" jboss && \
    chmod 755 /opt/jboss

# Set the WILDFLY_VERSION env variable
ENV WILDFLY_VERSION 24.0.1.Final
ENV WILDFLY_SHA1 751e3ff9128a6fbe72016552a9b864f729a710cc
ENV JBOSS_HOME /opt/jboss/wildfly

COPY --chown=jboss:0 dev.p12 /

USER root

# Add the WildFly distribution to /opt, and make wildfly the owner of the extracted tar content
# Make sure the distribution is available from a well-known place
RUN cd $HOME \
    && curl -O https://download.jboss.org/wildfly/$WILDFLY_VERSION/wildfly-$WILDFLY_VERSION.tar.gz \
    && sha1sum wildfly-$WILDFLY_VERSION.tar.gz | grep $WILDFLY_SHA1 \
    && tar xf wildfly-$WILDFLY_VERSION.tar.gz \
    && mv $HOME/wildfly-$WILDFLY_VERSION $JBOSS_HOME \
    && rm wildfly-$WILDFLY_VERSION.tar.gz \
    && chown -R jboss:0 ${JBOSS_HOME} \
    && chmod -R g+rw ${JBOSS_HOME}

# Ensure signals are forwarded to the JVM process correctly for graceful shutdown
ENV LAUNCH_JBOSS_IN_BACKGROUND true

USER jboss

RUN /opt/jboss/wildfly/bin/add-user.sh admin admin

# Expose the ports in which we're interested
EXPOSE 80
EXPOSE 443
EXPOSE 9990

# Set the default command to run on boot
# This will boot WildFly in standalone mode and bind to all interfaces
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-Djboss.http.port=80", "-Djboss.https.port=443", "-b", "0.0.0.0"]
