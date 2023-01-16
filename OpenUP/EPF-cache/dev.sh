rm EPF-cache.log.*
. ../env.sh
. ./config.sh
mvn clean install -U -Dquarkus.container-image.build=true
./stop.sh
./start.sh