set cur_dir=%CD%
cd EPF-persistence-schema
call mvn clean install
call jshell ./delete_db.jsh
cd %cur_dir%
copy EPF-persistence-schema/target/EPF-persistence-schema-1.0.0.war EPF-tests/target/servers/test/dropins/