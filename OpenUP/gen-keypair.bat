setlocal
call ./env.bat
set JAVA_HOME=%JAVA11_HOME%
set PATH=%PATH%;%JAVA_HOME%\bin
jshell .\gen-keypair.jsh
endlocal