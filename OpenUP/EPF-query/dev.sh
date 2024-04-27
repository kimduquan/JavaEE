. ../env.sh
. ./config.sh
mkdir -p ./src/main/jib/home/jboss/
cp $USER_DIR/EPF-query.trace.db ./src/main/jib/home/jboss/
cp $USER_DIR/EPF-query.mv.db ./src/main/jib/home/jboss/
mvn clean install -U -Dquarkus.container-image.build=true
./stop.sh
./start.sh