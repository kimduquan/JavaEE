set cur_dir=%CD%
set portal_dir="C:\Program Files\pluto-3.1.0\webapps\"
cd %portal_dir%
del ".\*.war" /s /f /q
rmdir "OpenUP-tasks-portlet" /s /q
rmdir "EPF-security-portlet" /s /q
rmdir "EPF-persistence-portlet" /s /q
rmdir "EPF-messaging-portlet" /s /q
rmdir "EPF-file-portlet" /s /q
cd %cur_dir%
rmdir "C:\tmp\kafka-logs" /s /q
rmdir "C:\tmp\zookeeper" /s /q