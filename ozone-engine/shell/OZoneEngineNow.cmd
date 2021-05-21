@echo off

set JAR_TO_RUN="D:\Documenti Ermal\Projects\OZoneDeploy\dist\OZoneEngine.jar"
echo Executing %JAR_TO_RUN%
echo.

if "%JAVA_HOME%" == "" goto noJavaHome
if not exist "%JAVA_HOME%\bin\java.exe" goto noJavaHome

java -jar %JAR_TO_RUN%

pause

goto fine

:noJavaHome
echo.
echo JAVA_HOME must be configured correctly!
echo.

:fine