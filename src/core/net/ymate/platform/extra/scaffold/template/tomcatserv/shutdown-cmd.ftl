@echo off
echo ----------------------------
echo 停止Tomcat6（${service_name}）服务...
echo ----------------------------

call net stop tomcat6${service_name}

pause