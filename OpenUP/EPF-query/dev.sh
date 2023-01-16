rm EPF-query.log.*
. ../env.sh
. ../config.sh
. ./config.sh
cp $USER_DIR/EPF-query.trace.db ./src/main/jib/home/jboss/
cp $USER_DIR/EPF-query.mv.db ./src/main/jib/home/jboss/
mvn clean install -U -Dquarkus.container-image.build=true
./stop.sh
./start.sh