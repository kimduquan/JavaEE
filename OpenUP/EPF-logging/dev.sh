rm EPF-logging.log.*
. ../env.sh
mvn clean install -U -Dquarkus.container-image.build=true
./stop.sh
./start.sh