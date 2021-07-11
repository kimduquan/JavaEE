call .\copy_dependency.bat
echo on
call .\startup.bat
echo on
call git pull
echo on
cd EPF-persistence
call mvn clean install
echo on
cd ../
call mvn clean install
echo on
call .\shutdown.bat
echo on