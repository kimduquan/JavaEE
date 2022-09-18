FROM icr.io/appcafe/open-liberty:full-java11-openj9-ubi

COPY --chown=1001:0 target/servers/test/jppf_ra_OpenLiberty.rar /config/
COPY --chown=1001:0 target/servers/test/server.env /config/
COPY --chown=1001:0 target/servers/test/server.xml /config/
COPY --chown=1001:0 target/servers/test/bootstrap.properties /config/
COPY --chown=1001:0 target/servers/test/hazelcast-config.xml /config/

RUN mkdir -p /tmp/config/apps

COPY --chown=1001:0 target/servers/test/apps /tmp/config/apps

COPY --chown=1001:0 EPF-security.trace.db /tmp/
COPY --chown=1001:0 EPF-query.mv.db /tmp/
COPY --chown=1001:0 EPF-query.trace.db /tmp/
COPY --chown=1001:0 EPF-security.mv.db /tmp/

USER root

RUN mv /tmp/config/apps /opt/ol/wlp/usr/servers/defaultServer/

RUN mv /tmp/EPF-security.trace.db ~/
RUN mv /tmp/EPF-query.mv.db ~/
RUN mv /tmp/EPF-query.trace.db ~/
RUN mv /tmp/EPF-security.mv.db ~/

COPY --chown=1001:0 target/shared /opt/ol/wlp/usr/shared

RUN configure.sh
