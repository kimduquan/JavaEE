call git pull
call git clean -f -d
copy .\settings.xml ~/.m2
call .\clean.bat
echo on
call .\copy_dependency.bat
echo on
call .\startup.bat
echo on
cd EPF-persistence
call mvn clean install
echo on
cd ../
call mvn clean install -Depf-shell-native -Depf-gateway-native
echo on
call .\shutdown.bat
echo on