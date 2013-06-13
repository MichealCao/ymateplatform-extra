@echo off
echo ----------------------------
echo 停止并移除Tomcat6（${service_name}）服务...
echo ----------------------------

set CATALINA_HOME=${java_home}
set CATALINA_BASE=${website_root_path}

call %CATALINA_HOME%\bin\service.bat remove tomcat6${service_name}

pause