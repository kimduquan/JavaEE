rm -R -d ObjectStore
. ../env.sh
. ../native_env.sh
mvn package -Pnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true
./stop.sh
./start.sh