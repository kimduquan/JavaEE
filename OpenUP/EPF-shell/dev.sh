rm EPF-shell.log.*
mv .env env
rm env
cp unix.env env
mv env ".env"
. ../env.sh
. ../native_env.sh
. ../config.sh
mvn clean install -U -Dnative