setlocal
set MAVEN_HOME=C:\apache-maven-3.9.1\bin
set PATH=%MAVEN_HOME%;%PATH%
set GRAALVM_HOME=C:\graalvm-ce-java8-21.3.1
set PATH=%PATH%;%GRAALVM_HOME%/bin
call "D:\Installed\VisualStudio\VC\Auxiliary\Build\vcvars64.bat"
set JAVA_HOME=%GRAALVM_HOME%
call mvn clean package -U -e -X
call rmdir /s /q ".\target\EPF-installer-1.0.0\"
call mkdir target\EPF-installer-1.0.0
call tar -xvzf ".\target\EPF-installer-1.0.0.jar" -C ".\target\EPF-installer-1.0.0"
call xcopy ".\target\EPF-installer-1.0.0\META-INF\" ".\src\main\resources\META-INF\" /e /exclude:exclude.txt
call rmdir /s /q ".\src\main\resources\resources\"
call mkdir .\src\main\resources\resources
call xcopy ".\target\EPF-installer-1.0.0\resources\" ".\src\main\resources\resources\" /e
call rmdir /s /q ".\src\main\resources\uninstaller-META-INF\"
call mkdir .\src\main\resources\uninstaller-META-INF
call xcopy ".\target\EPF-installer-1.0.0\uninstaller-META-INF\" ".\src\main\resources\uninstaller-META-INF\" /e
call %GRAALVM_HOME%/bin/java.exe -agentlib:native-image-agent=config-merge-dir=src/main/resources/META-INF/native-image -jar target/EPF-installer-1.0.0.jar
call mvn clean package -U -e -X
call %GRAALVM_HOME%/bin/native-image.cmd -J-Dlogging.initial-configurator.min-level=500 -J-Duser.language=en -J-Duser.country=US -J-Dfile.encoding=UTF-8 -H:+PrintAnalysisCallTree -H:+CollectImageBuildStatistics -H:ImageBuildStatisticsFile=EPF-installer-1.0.0-timing-stats.json --static --initialize-at-run-time=sun.awt.dnd.SunDropTargetContextPeer\$EventDispatcher,sun.awt.AWTAutoShutdown,sun.awt.shell.ShellFolder,java.awt.event.MouseEvent,javax.swing.UIManager,sun.awt.shell.Win32ShellFolder2\$KnownLibraries,javax.swing.RepaintManager,sun.java2d.d3d.D3DGraphicsDevice,javax.swing.text.html.CSS\$LengthUnit,javax.swing.plaf.basic.BasicInternalFrameTitlePane,javax.swing.filechooser.WindowsFileSystemView -H:+AllowFoldMethods --auto-fallback -H:+ReportExceptionStackTraces -Dsun.misc.ProxyGenerator.saveGeneratedFiles=true -H:-AddAllCharsets --enable-url-protocols=http,https -H:+UseServiceLoaderFeature -H:+TraceServiceLoaderFeature -H:+StackTrace EPF-installer-1.0.0 -jar target/EPF-installer-1.0.0.jar
endlocal