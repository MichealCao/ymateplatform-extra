@echo off
echo ----------------------------
echo 管理/配置Tomcat6（${service_name}）服务...
echo ----------------------------

set CATALINA_HOME=${java_home}
set CATALINA_BASE=${website_root_path}

call %CATALINA_HOME%\bin\tomcat6w.exe //MS//tomcat6${service_name}

pause