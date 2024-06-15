. ../env.sh
. ../EPF-concurrent/start.sh
. ../EPF-registry/start.sh
. ../EPF-gateway/start.sh
mvn clean install -U -Dquarkus.container-image.build=true
./stop.sh
./start.sh