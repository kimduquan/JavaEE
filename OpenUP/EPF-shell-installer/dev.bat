setlocal
call ../env.bat
call ../native_env.bat
set JAVA_HOME=%GRAALVM_HOME%
call mvn clean package -U -e -X
call rmdir /s /q ".\target\EPF-shell-installer-1.0.0\"
call mkdir target\EPF-shell-installer-1.0.0
call tar -xvzf ".\target\EPF-shell-installer-1.0.0.jar" -C ".\target\EPF-shell-installer-1.0.0"
call xcopy ".\target\EPF-shell-installer-1.0.0\META-INF\" ".\src\main\resources\META-INF\" /e /exclude:exclude.txt
call rmdir /s /q ".\src\main\resources\resources\"
call mkdir .\src\main\resources\resources
call xcopy ".\target\EPF-shell-installer-1.0.0\resources\" ".\src\main\resources\resources\" /e
call rmdir /s /q ".\src\main\resources\uninstaller-META-INF\"
call mkdir .\src\main\resources\uninstaller-META-INF
call xcopy ".\target\EPF-shell-installer-1.0.0\uninstaller-META-INF\" ".\src\main\resources\uninstaller-META-INF\" /e
call %GRAALVM_HOME%/bin/java.exe -agentlib:native-image-agent=config-merge-dir=src/main/resources/META-INF/native-image -jar target/EPF-shell-installer-1.0.0.jar
::call mvn clean package -U -e -X -Dnative
call mvn quarkus:build -e -X -Dnative 
endlocal