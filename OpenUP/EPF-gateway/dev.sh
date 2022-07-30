. ../env.sh
. ../config.sh
. ./config.sh
mvn clean install -U
mvn quarkus:dev -Ddebug=5006