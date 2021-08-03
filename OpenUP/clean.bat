set cur_dir=%CD%
set portal_dir="C:\Program Files\pluto-3.1.0\webapps\"
cd %portal_dir%
del ".\*.war" /s /f /q
del ".\OpenUP-work-products-portlet" /s /f /q
del ".\OpenUP-tasks-portlet" /s /f /q
del ".\OpenUP-roles-portlet" /s /f /q
del ".\OpenUP-delivery-processes-portlet" /s /f /q
del ".\EPF-security-portlet" /s /f /q
del ".\EPF-persistence-portlet" /s /f /q
del ".\EPF-messaging-portlet" /s /f /q
del ".\EPF-file-portlet" /s /f /q
cd %cur_dir%