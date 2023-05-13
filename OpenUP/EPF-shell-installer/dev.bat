setlocal
call ../env.bat
call ../native_env.bat
call mvn clean package -U
::call %GRAALVM_HOME%/bin/java.exe -agentlib:native-image-agent=config-merge-dir=src/main/resources/META-INF/native-image -jar target/EPF-shell-installer-1.0.0.jar
call mvn quarkus:build -U -Dnative -e
endlocal