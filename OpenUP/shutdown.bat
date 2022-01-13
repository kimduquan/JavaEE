setlocal
set cur_dir=%CD%
set cur_java=$JAVA_HOME
set kafka_dir="C:\kafka_2.13-2.8.1\bin\windows"
cd %kafka_dir%
call .\kafka-server-stop.bat &
Taskkill /IM jaeger-all-in-one.exe /F
Taskkill /IM geckodriver.exe /F
Taskkill /IM firefox.exe /F
set JAVA_HOME=C:\jdk8u312-b07
set portal_dir="C:\Program Files\pluto-3.1.0\bin\"
cd %portal_dir%
call .\shutdown.bat &
cd %cur_dir%
set glassfish_dir="C:\glassfish5\glassfish\bin\"
cd %glassfish_dir%
call .\stopserv.bat &
cd %cur_dir%
cd %kafka_dir%
call .\zookeeper-server-stop.bat &
cd %cur_dir%
endlocal
