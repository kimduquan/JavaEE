git pull
git clean -f -d
./copy_dependency.sh
./startup.sh
export JAVA_HOME=~/graalvm-ee-java11-21.2.0
cd EPF-image
mvn clean install
cd ../
export JAVA_HOME=~/graalvm-ee-java8-21.2.0
cd EPF-persistence
mvn clean install
cd ../
mvn clean install
./shutdown.sh