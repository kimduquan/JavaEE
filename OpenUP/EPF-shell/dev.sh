rm EPF-shell.log.*
cp unix.env env
mv env ".env"
. ../env.sh
. ../config.sh
mvn clean install -U -Depf-shell-native