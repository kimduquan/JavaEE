set epf.registry.url=http://localhost:9080/registry/registry/
set mp.jwt.verify.audiences=http://localhost:9080/,https://localhost:9543/
set quarkus.http.ssl-port=9543
set quarkus.http.insecure-requests=disabled
set quarkus.log.file.enable=true
set quarkus.log.file.path=EPF-gateway.log
set quarkus.log.level=DEBUG
set quarkus.vertx.max-event-loop-execute-time=20S