. ../env.sh
. ../config.sh
cp $USER_DIR/EPF-query.trace.db ./
cp $USER_DIR/EPF-query.mv.db ./
cp $USER_DIR/EPF-security.mv.db ./
cp $USER_DIR/EPF-security.trace.db ./
cp $SOURCE_DIR/dev.p12 ./
cp $SOURCE_DIR/private.pem ./
cp $SOURCE_DIR/public.pem ./
mvn clean install -U -P Container -DskipITs
docker build -t openup:1.0.0 ./
./stop.sh
./start.sh