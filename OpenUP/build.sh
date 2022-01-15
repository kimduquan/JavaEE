git pull
git clean -f -d
cp settings.xml ~/.m2
./clean.sh
./copy_dependency.sh
cd EPF-shell
mvn clean install
cd ../
./startup.sh
cd EPF-persistence
mvn clean install
cd ../
mvn clean install
./shutdown.sh