cd EPF-persistence-schema
mvn clean install
jshell ./delete_db.jsh
cd ../
cp EPF-persistence-schema/target/EPF-persistence-schema-1.0.0.war EPF-tests/target/servers/test/dropins/
./dev.sh