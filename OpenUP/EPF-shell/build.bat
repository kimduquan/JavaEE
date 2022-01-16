setlocal
set PATH=%GRAALVM_HOME%/bin;%PATH%call gu install native-image
echo on
call "C:\Program Files\Microsoft Visual Studio\2022\Community\VC\Auxiliary\Build\vcvars64.bat"
call mvn clean install -Pnative
endlocal