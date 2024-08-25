call .\env.bat
call git pull
call git clean -f -d
copy .\settings.xml ~/.m2
call .\clean.bat
echo on
call .\copy_dependency.bat
echo on
call .\config.bat
call .\startup.bat
echo on
call mvn clean install -U -Depf-shell-native -Depf-gateway-native -Depf-persistence-native
echo on
call .\shutdown.bat
echo on