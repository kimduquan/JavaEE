. ./env.sh
git pull
git clean -f -d
cp settings.xml ~/.m2
./clean.sh
./copy_dependency.sh
#gu install native-image
. ./config.sh
./startup.sh
#sudo apt-get install build-essential libz-dev zlib1g-dev
mvn clean install -U -Depf-shell-native -Depf-gateway-native -Depf-persistence-native
./shutdown.sh