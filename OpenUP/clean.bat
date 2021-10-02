set cur_dir=%CD%
set portal_dir="C:\Program Files\pluto-3.1.0\webapps\"
cd %portal_dir%
del ".\*.war" /s /f /q
rmdir "OpenUP-work-products-portlet" /s /q
rmdir "OpenUP-tasks-portlet" /s /q
rmdir "OpenUP-roles-portlet" /s /q
rmdir "OpenUP-delivery-processes-portlet" /s /q
rmdir "EPF-security-portlet" /s /q
rmdir "EPF-persistence-portlet" /s /q
rmdir "EPF-messaging-portlet" /s /q
rmdir "EPF-file-portlet" /s /q
cd %cur_dir%