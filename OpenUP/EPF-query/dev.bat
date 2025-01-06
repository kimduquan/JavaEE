setlocal
call ../env.bat
call ../config.bat
call config.bat
mkdir -p ./src/main/jib/home/jboss/
copy %USER_DIR%\EPF-query.trace.db ./src/main/jib/home/jboss/
copy %USER_DIR%\EPF-query.mv.db ./src/main/jib/home/jboss/
call mvn clean install -U
call mvn quarkus:dev -Ddebug=5188
endlocal