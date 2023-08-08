rm EPF-shell.log.*
mv .env env
rm env
cp unix.env env
mv env ".env"
. ../env.sh
. ../native_env.sh
. ../config.sh
. ../config_ssl.sh
export QUARKUS_NATIVE_ADDITIONAL_BUILD_ARGS=--initialize-at-run-time=org.h2.fulltext.FullTextLucene
mvn clean install -U -Dnative