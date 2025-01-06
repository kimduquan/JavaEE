setlocal
call ../env.bat
call config.bat
copy %SOURCE_DIR%\public.pem .\src\main\jib\
call mvn clean install -U
call mvn quarkus:dev -Ddebug=5181
endlocal