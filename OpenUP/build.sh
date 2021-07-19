export JAVA_HOME=~/graalvm-ce-java11-21.1.0
./copy_dependency.sh
git pull
cd EPF-persistence
mvn clean install
cd ../
mvn clean install