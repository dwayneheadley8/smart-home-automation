@echo off
REM Smart Home System - Javadoc Generation Script
REM This script generates Javadoc for the Smart Home Automation System

echo Generating Javadoc for Smart Home System...
echo.

REM Create output directory
if not exist target\javadoc mkdir target\javadoc

REM Generate Javadoc
javadoc -encoding UTF-8 -d target/javadoc -sourcepath src/main/java -subpackages com -quiet

echo.
echo Javadoc generation complete!
echo Documentation is available at: target/javadoc/index.html
echo.
pause
