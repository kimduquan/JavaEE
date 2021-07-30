setlocal
set cur_dir=%CD%
set JAVA_HOME=C:\graalvm-ee-java8-21.2.0
set portal_dir="C:\Program Files\pluto-3.1.0\bin\"
cd %portal_dir%
call .\startup.bat
cd %cur_dir%
endlocal
