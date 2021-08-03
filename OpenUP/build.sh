git pull
git clean -f -d
./clean.sh
./copy_dependency.sh
./startup.sh
export JAVA_HOME=~/jdk-11.0.11+9
cd EPF-image
mvn clean install
cd ../
export JAVA_HOME=~/jdk8u292-b10
cd EPF-persistence
mvn clean install
cd ../
mvn clean install
./shutdown.sh