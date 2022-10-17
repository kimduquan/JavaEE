set quarkus.http.port=9181
set quarkus.log.file.enable=true
set quarkus.log.file.path=EPF-persistence.log
set JAEGER_SAMPLER_TYPE=const
set JAEGER_SAMPLER_PARAM=1
set JAEGER_SERVICE_NAME=EPF-persistence
set mp.messaging.connector.smallrye-kafka.bootstrap.servers=localhost:9092