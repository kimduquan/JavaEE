. ../env.sh
. ../native_env.sh
mkdir –p src/main/jib
cp $SOURCE_DIR/public.pem ./src/main/jib/
mvn clean install -U -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true -P native
./stop.sh
./start.sh