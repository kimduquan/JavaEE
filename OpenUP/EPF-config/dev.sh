rm EPF-config.log.*
. ../env.sh
cp $SOURCE_DIR/public.pem ./src/main/jib/
mvn clean install -U -Dquarkus.container-image.build=true
./stop.bat
./start.bat