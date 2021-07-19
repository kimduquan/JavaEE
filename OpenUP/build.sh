export JAVA_HOME=~/graalvm-ce-java11-21.1.0
export JAVA_HOME=~/graalvm-ee-java8-21.1.0
git pull
./copy_dependency.sh
cd EPF-persistence
mvn clean install
cd ../
mvn clean install