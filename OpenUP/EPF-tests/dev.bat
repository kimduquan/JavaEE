copy "C:\Users\PC\EPF-query.trace.db" EPF-query.trace.db
copy "C:\Users\PC\EPF-security.mv.db" EPF-security.mv.db
copy "C:\Users\PC\EPF-security.trace.db" EPF-security.trace.db
copy "C:\Users\PC\EPF-query.mv.db" EPF-query.mv.db
copy ../dev.p12 dev.p12
setlocal
call ../env.bat
call ../config.bat
call mvn liberty:devc
endlocal