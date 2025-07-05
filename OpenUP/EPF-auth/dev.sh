. ../env.sh
. ../native_env.sh
./mvnw spring-boot:build-image -Pnative
./stop.sh
./start.sh