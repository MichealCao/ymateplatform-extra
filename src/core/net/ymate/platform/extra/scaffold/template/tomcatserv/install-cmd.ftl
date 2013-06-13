@echo off
echo ----------------------------
echo 安装并启动Tomcat6（${service_name}）服务...
echo ----------------------------

set CATALINA_HOME=${java_home}
set CATALINA_BASE=${website_root_path}

call %CATALINA_HOME%\bin\service.bat install tomcat6${service_name}

call net start tomcat6${service_name}

pause