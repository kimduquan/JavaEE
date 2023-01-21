. ../env.sh
. ../config.sh
. ./config.sh
mkdir -p ./src/main/jib/tmp/
cp $USER_DIR/EPF-query.trace.db ./src/main/jib/tmp/
cp $USER_DIR/EPF-query.mv.db ./src/main/jib/tmp/
mvn clean install -U -Dquarkus.container-image.build=true
./stop.sh
./start.sh