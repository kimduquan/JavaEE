FROM icr.io/appcafe/open-liberty:latest

CMD ["/opt/ol/wlp/bin/server", "stop", "defaultServer"]

COPY --chown=1001:0 target/servers/test/jppf_ra_OpenLiberty.rar /config/
COPY --chown=1001:0 target/servers/test/server.env /config/
COPY --chown=1001:0 target/servers/test/bootstrap.properties /config/
COPY --chown=1001:0 target/servers/test/jvm.options /config/

COPY --chown=1001:0 target/shared /liberty/usr/shared

COPY --chown=1001:0 dev.p12 /
COPY --chown=1001:0 private.pem /
COPY --chown=1001:0 public.pem /

COPY --chown=1001:0 --chmod=777 h2 /h2

COPY --chown=1001:0 target/servers/test/server.xml /config/
COPY --chown=1001:0 target/servers/test/apps /config/apps

# RUN yum -y install opencv

CMD ["/opt/ol/wlp/bin/server", "run", "defaultServer"]

RUN configure.sh
