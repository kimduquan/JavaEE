FROM icr.io/appcafe/open-liberty:full-java11-openj9-ubi

COPY --chown=1001:0 target/servers/test/jppf_ra_OpenLiberty.rar /config/
COPY --chown=1001:0 target/servers/test/server.env /config/
COPY --chown=1001:0 target/servers/test/server.xml /config/
COPY --chown=1001:0 target/servers/test/bootstrap.properties /config/
COPY --chown=1001:0 target/servers/test/hazelcast-config.xml /config/

COPY --chown=1001:0 target/servers/test/apps /config/apps

COPY --chown=1001:0 target/shared /opt/ol/wlp/usr/shared

COPY --chown=1001:0 EPF-security.trace.db /tmp/
COPY --chown=1001:0 EPF-query.mv.db /tmp/
COPY --chown=1001:0 EPF-query.trace.db /tmp/
COPY --chown=1001:0 EPF-security.mv.db /tmp/
COPY --chown=1001:0 dev.p12 /tmp/

ENV epf_client_net_ssl_trustStore=/tmp/dev.p12

USER root

RUN mv /tmp/EPF-security.trace.db ~/
RUN mv /tmp/EPF-query.mv.db ~/
RUN mv /tmp/EPF-query.trace.db ~/
RUN mv /tmp/EPF-security.mv.db ~/

RUN mkdir /opt/ol/wlp/output/defaultServer/epf.file.root
RUN mkdir /opt/ol/wlp/output/defaultServer/epf.file.cache.root

COPY --chown=1001:0 target/servers/test/epf.file.root /opt/ol/wlp/output/defaultServer/epf.file.root

RUN configure.sh
