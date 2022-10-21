setlocal
call .\env.bat
call .\config.bat
set CUR_DIR=%CD%
call kubectl apply -f postgresql.yml
call kubectl apply -f zookeeper.yml
set JAVA_HOME=%JAVA8_HOME%
cd %PLUTO_HOME%\bin
call .\startup.bat &
cd %CUR_DIR%
call kubectl apply -f wildfly.yml
call kubectl apply -f kafka.yml
endlocal
