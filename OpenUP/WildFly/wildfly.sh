. ../env.sh
cp $SOURCE_DIR/dev.p12 ./
docker build -t wildfly:1.0.0 ./
./stop.sh
./start.sh