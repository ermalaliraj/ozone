@echo off

set JAR_TO_RUN="D:\Documents\OZone\workspace\OZoneDeploy\dist\OZoneEngine.jar"
set MAIN_CLASS=al.ozone.engine.batch.BatchEngineMain

echo Executing %JAR_TO_RUN%
echo.

if "%JAVA_HOME%" == "" goto noJavaHome
if not exist "%JAVA_HOME%\bin\java.exe" goto noJavaHome

java -cp %JAR_TO_RUN% %MAIN_CLASS% -newsletterEngine

pause

goto fine

:noJavaHome
echo.
echo JAVA_HOME must be configured correctly!
echo.
pause
:fine