rm EPF-gateway.log.*
. ../env.sh
. ../config.sh
. ./config.sh
mvn quarkus:dev -Ddebug=5006