. ../env.sh
. ../config.sh
cp $SOURCE_DIR/dev.p12 ./
mvn liberty:dev -U -P Container -DskipTestServer=true