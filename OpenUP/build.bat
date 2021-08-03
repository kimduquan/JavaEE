call git pull
call git clean -f -d
call .\clean.bat
echo on
call .\copy_dependency.bat
echo on
call .\startup.bat
echo on
cd EPF-image
call mvn clean install
echo on
cd ../
cd EPF-persistence
call mvn clean install
echo on
cd ../
call mvn clean install
echo on
call .\shutdown.bat
echo on