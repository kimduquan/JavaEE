setlocal
set cur_dir=%CD%
set cur_java=$JAVA_HOME
set kafka_dir="C:\kafka_2.13-2.8.1\bin\windows"
cd %kafka_dir%
call .\kafka-server-stop.bat
set JAVA_HOME=C:\jdk8u292-b10
set portal_dir="C:\Program Files\pluto-3.1.0\bin\"
cd %portal_dir%
call .\shutdown.bat
cd %cur_dir%
cd %kafka_dir%
call .\zookeeper-server-stop.bat
cd %cur_dir%
endlocal
