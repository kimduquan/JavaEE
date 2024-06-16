cd ../EPF-concurrent
./start.sh
cd ../EPF-registry
./start.sh
cd ../EPF-tests
./start.sh
cd ../EPF-gateway
./start.sh
cd ../EPF-lang
. ../env.sh
mvn clean install -U -Dquarkus.container-image.build=true
./stop.sh
./start.sh