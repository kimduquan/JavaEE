rm EPF-transaction.log.*
rm -R -d ObjectStore
. ../env.sh
. ./config.sh
java -jar ./target/quarkus-app/quarkus-run.jar