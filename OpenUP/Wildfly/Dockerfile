FROM jboss/wildfly:24.0.0.Final

USER jboss

RUN /opt/jboss/wildfly/bin/add-user.sh admin admin

EXPOSE 80
EXPOSE 443
EXPOSE 9990

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-Djboss.http.port=80", "-Djboss.https.port=443", "-b", "0.0.0.0"]