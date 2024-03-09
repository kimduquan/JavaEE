. ../env.sh
. ../native_env.sh
mvn clean install -U -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true -P native
./stop.sh
./start.sh