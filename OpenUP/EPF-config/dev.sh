. ../env.sh
. ../native_env.sh
cp $SOURCE_DIR/public.pem ./src/main/jib/home/jboss/
mvn clean install -U -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true -P native
./stop.sh
./start.sh