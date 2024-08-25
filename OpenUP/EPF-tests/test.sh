. ../env.sh
. ../config.sh
cp $SOURCE_DIR/dev.p12 ./
cp $USER_DIR/geckodriver ./
mvn liberty:dev -U -P Container -DskipTestServer=true