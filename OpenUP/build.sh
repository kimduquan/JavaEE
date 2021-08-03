export PATH=~/jdk-11.0.11+9/bin:$PATH
export JAVA_HOME=~/jdk-11.0.11+9
git pull
git clean -f -d
./clean.sh
./copy_dependency.sh
./startup.sh
cd EPF-function
mvn clean install
cd ../
cd EPF-image
mvn clean install
cd ../
cd EPF-persistence
mvn clean install
cd ../
mvn clean install
./shutdown.sh