call .\env.bat
set CUR_DIR=%CD%
cd %PLUTO_HOME%\webapps
del ".\*.war" /s /f /q
rmdir "EPF-security-portlet" /s /q
rmdir "EPF-persistence-portlet" /s /q
rmdir "EPF-messaging-portlet" /s /q
rmdir "EPF-file-portlet" /s /q
cd %CUR_DIR%
rmdir "C:\tmp\kafka-logs" /s /q
rmdir "C:\tmp\zookeeper" /s /q