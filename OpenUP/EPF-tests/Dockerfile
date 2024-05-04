FROM icr.io/appcafe/open-liberty:latest

CMD ["/opt/ol/wlp/bin/server", "stop", "defaultServer"]

COPY --chown=1001:0 target/servers/test/jppf_ra_OpenLiberty.rar /config/
COPY --chown=1001:0 target/servers/test/server.env /config/
COPY --chown=1001:0 target/servers/test/bootstrap.properties /config/
COPY --chown=1001:0 target/servers/test/jvm.options /config/

# COPY --chown=1001:0 target/servers/test/resources/security /config/resources/security

COPY --chown=1001:0 target/shared /liberty/usr/shared

COPY --chown=1001:0 dev.p12 /
COPY --chown=1001:0 private.pem /
COPY --chown=1001:0 public.pem /

COPY --chown=1001:0 EPF-security.trace.db /liberty/usr/
COPY --chown=1001:0 EPF-query.mv.db /liberty/usr/
COPY --chown=1001:0 EPF-query.trace.db /liberty/usr/
COPY --chown=1001:0 EPF-security.mv.db /liberty/usr/

RUN mkdir /config/epf.file.root
RUN mkdir /config/epf.file.cache.root

COPY --chown=1001:0 target/servers/test/epf.file.root /config/epf.file.root

COPY --chown=1001:0 target/servers/test/server.xml /config/
COPY --chown=1001:0 target/servers/test/apps /config/apps

# RUN yum -y install opencv

CMD ["/opt/ol/wlp/bin/server", "run", "defaultServer"]

RUN configure.sh
