export EPF_REGISTRY_URL=http://localhost:9080/registry/registry/
export MP_JWT_VERIFY_AUDIENCES=http://localhost:9080/,https://localhost:9543/
export QUARKUS_HTTP_SSL_PORT=9543
export QUARKUS_HTTP_INSECURE_REQUESTS=disabled
export QUARKUS_LOG_FILE_ENABLE=true
export QUARKUS_LOG_FILE_PATH=EPF-gateway.log
export QUARKUS_LOG_LEVEL=DEBUG
export QUARKUS_VERTX_MAX_EVENT_LOOP_EXECUTE_TIME=20S