set GRAALVM_HOME=C:\graalvm-ce-java11-21.3.0
set JAVA_HOME=C:\jdk-11.0.13+8
set PATH=%GRAALVM_HOME%/bin;%PATH%
call gu install native-image
echo on
call "C:\Program Files\Microsoft Visual Studio\2022\Community\VC\Auxiliary\Build\vcvars64.bat"
call mvn clean install -Pnative
endlocal