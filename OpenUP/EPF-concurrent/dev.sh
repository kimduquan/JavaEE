. ../env.sh
#. ../native_env.sh
#mvn clean install -U -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true -P native
mvn clean install -U -Dquarkus.container-image.build=true
./stop.sh
./start.sh