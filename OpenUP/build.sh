git pull
git clean -f -d
cp settings.xml ~/.m2
./clean.sh
./copy_dependency.sh
./startup.sh
cd EPF-persistence
mvn clean install
cd ../
#sudo apt-get install build-essential libz-dev zlib1g-dev
mvn clean install -Depf-shell-native
./shutdown.sh