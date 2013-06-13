@echo off
echo ----------------------------
echo 启动Tomcat6（${service_name}）服务...
echo ----------------------------

call net start tomcat6${service_name}

pause