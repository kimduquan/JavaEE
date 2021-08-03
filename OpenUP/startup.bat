setlocal
set cur_dir=%CD%
set cur_java=$JAVA_HOME
set JAVA_HOME=C:\jdk8u292-b10
set portal_dir="C:\Program Files\pluto-3.1.0\bin\"
cd %portal_dir%
call .\startup.bat
cd %cur_dir%
set JAVA_HOME=%cur_java%
endlocal
