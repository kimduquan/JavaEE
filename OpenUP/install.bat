cd EPF-persistence-portlet
call mvn clean install -U
echo on
cd ../
cd EPF-security-portlet
call mvn clean install -U
echo on
cd ../
cd EPF-file-portlet
call mvn clean install -U
echo on
cd ../
cd EPF-messaging-portlet
call mvn clean install -U
echo on
cd ../