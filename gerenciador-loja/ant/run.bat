@echo off

set APP_CP=.;

for %%i in ("jre\lib\*.jar") do call "cpappend.bat" %%i
for %%i in ("app\*.jar") do call "cpappend.bat" %%i
for %%i in ("properties\*.properties") do call "cpappend.bat" %%i
for %%i in ("properties\*.xml") do call "cpappend.bat" %%i

SET CLASSPATH=%APP_CP%

start jre\bin\javaw -Xms256M -Xmx768M br.com.tecway.fx.launcher.GerenciadorLojaLauncher