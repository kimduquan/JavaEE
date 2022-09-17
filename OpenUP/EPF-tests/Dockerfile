FROM icr.io/appcafe/open-liberty:full-java11-openj9-ubi

COPY --chown=1001:0 target/servers/test/jppf_ra_OpenLiberty.rar /config/
COPY --chown=1001:0 target/servers/test/server.env /config/
COPY --chown=1001:0 target/servers/test/server.xml /config/
COPY --chown=1001:0 target/servers/test/bootstrap.properties /config/
COPY --chown=1001:0 target/servers/test/hazelcast-config.xml /config/

COPY --chown=1001:0 EPF-security.trace.db ~/
COPY --chown=1001:0 EPF-query.mv.db ~/
COPY --chown=1001:0 EPF-query.trace.db ~/
COPY --chown=1001:0 EPF-security.mv.db ~/

COPY --chown=1001:0 target/shared /opt/ol/wlp/usr/shared

RUN configure.sh
