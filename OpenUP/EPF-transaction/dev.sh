rm -R -d ObjectStore
. ../env.sh
mvn clean install -U -Dquarkus.container-image.build=true
./stop.sh
./start.sh