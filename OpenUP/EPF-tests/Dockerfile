FROM icr.io/appcafe/open-liberty:full-java11-openj9-ubi

CMD ["/opt/ol/wlp/bin/server", "stop", "defaultServer"]

COPY --chown=1001:0 target/servers/test/jppf_ra_OpenLiberty.rar /config/
COPY --chown=1001:0 target/servers/test/server.env /config/
COPY --chown=1001:0 target/servers/test/bootstrap.properties /config/
COPY --chown=1001:0 target/servers/test/hazelcast-config.xml /config/

# COPY --chown=1001:0 target/servers/test/resources/security /config/resources/security

COPY --chown=1001:0 target/shared /liberty/usr/shared

COPY EPF-security.trace.db /tmp/
COPY EPF-query.mv.db /tmp/
COPY EPF-query.trace.db /tmp/
COPY EPF-security.mv.db /tmp/

COPY --chown=1001:0 dev.p12 /tmp/

ENV epf_client_net_ssl_trustStore=/tmp/dev.p12

RUN mkdir /config/epf.file.root
RUN mkdir /config/epf.file.cache.root

COPY --chown=1001:0 target/servers/test/epf.file.root /config/epf.file.root

COPY --chown=1001:0 target/servers/test/server.xml /config/
COPY --chown=1001:0 target/servers/test/apps /config/apps

USER root

RUN mv /tmp/EPF-security.trace.db ~/
RUN mv /tmp/EPF-query.mv.db ~/
RUN mv /tmp/EPF-query.trace.db ~/
RUN mv /tmp/EPF-security.mv.db ~/

# RUN yum -y install opencv

USER 1001

CMD ["/opt/ol/wlp/bin/server", "run", "defaultServer"]

RUN configure.sh
