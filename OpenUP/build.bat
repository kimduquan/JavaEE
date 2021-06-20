.\copy_dependency.bat
call git pull
cd EPF-persistence
call mvn clean install
echo on
cd ../
call mvn clean install
echo on