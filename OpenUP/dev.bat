call ./shutdown.bat
call ./clean.bat
call ./startup.bat
cd EPF-shell
call "C:\Program Files\Microsoft Visual Studio\2022\Community\VC\Auxiliary\Build\vcvars64.bat"
call mvn clean install -Depf-shell-native
cd ../
cd EPF-tests
call mvn liberty:dev