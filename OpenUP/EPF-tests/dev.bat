copy "C:\Users\PC\EPF-query.trace.db" EPF-query.trace.db
copy "C:\Users\PC\EPF-security.mv.db" EPF-security.mv.db
copy "C:\Users\PC\EPF-security.trace.db" EPF-security.trace.db
copy "C:\Users\PC\EPF-query.mv.db" EPF-query.mv.db

mkdir -p "./target/.libertyDevc/apps/"
copy "./target/servers/test/apps" "./target/.libertyDevc/apps/"
mkdir -p "./target/servers/test/epf.file.root/"

setlocal
call ../env.bat
call ../config.bat
copy %SOURCE_DIR%\dev.p12 .\
call mvn package -U -P Container
call docker build -t openup:1.0.0 ./
call stop.bat
call start.bat
endlocal