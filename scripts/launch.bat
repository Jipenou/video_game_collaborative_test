@echo off
SET "JAVA_HOME=C:\Program Files\Java\jdk-25"
SET PATH=%JAVA_HOME%\bin;%PATH%

CD /D "%~dp0\.."

:RESTART
CLS
echo ===============================================
echo         Lancement de l'application
echo ===============================================
echo.

echo Répertoire courant : %CD%
echo.

echo Java utilise :
java -version
echo.
echo Programme en cours de lancement...
echo.

SET "JAR_PATH=bin\video_game_collaborative_plateform.jar"

java -jar "%JAR_PATH%"

echo.
echo ===============================================
echo Programme termine.
echo ===============================================

:CHOICE
choice /c RQ /n /m "Appuyez sur R pour relancer, Q pour quitter."

if errorlevel 2 goto QUIT
if errorlevel 1 goto RESTART

:QUIT
exit