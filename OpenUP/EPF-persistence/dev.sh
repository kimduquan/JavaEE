. ../env.sh
. ../native_env.sh
. ./config.sh
mvn clean install -U -Pnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true
./stop.sh
./start.sh