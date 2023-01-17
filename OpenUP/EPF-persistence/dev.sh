. ../env.sh
. ./config.sh
cp $SOURCE_DIR/public.pem ./src/main/jib/
mvn clean install -U -Dquarkus.container-image.build=true
./stop.sh
./start.sh