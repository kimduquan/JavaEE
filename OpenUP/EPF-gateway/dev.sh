. ../env.sh
mkdir –p src/main/jib
cp $SOURCE_DIR/dev.p12 ./src/main/jib/
cp $SOURCE_DIR/public.pem ./src/main/jib/
mvn clean install -U -Dquarkus.container-image.build=true
./stop.sh
./start.sh