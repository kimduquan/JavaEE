git pull
git clean -f -d
cp settings.xml ~/.m2
./clean.sh
./copy_dependency.sh
. ./env.sh
#gu install native-image
. ./config.sh
./startup.sh
cd EPF-persistence
mvn clean install -U
cd ../
#sudo apt-get install build-essential libz-dev zlib1g-dev
mvn clean install -U -Depf-shell-native -Depf-gateway-native
./shutdown.sh