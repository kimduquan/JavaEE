export MP_JWT_VERIFY_AUDIENCES=http://localhost:9080/,https://localhost:9543/
export QUARKUS_HTTP_PORT=9181
export QUARKUS_LOG_FILE_ENABLE=true
export QUARKUS_LOG_FILE_PATH=EPF-persistence.log
export JAEGER_SAMPLER_TYPE=const
export JAEGER_SAMPLER_PARAM=1
export JAEGER_SERVICE_NAME=EPF-persistence