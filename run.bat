@echo off
echo Maven 환경 변수 설정 중...
set MAVEN_HOME=C:\Apache\apache-maven-3.9.11
set PATH=%MAVEN_HOME%\bin;%PATH%

echo.
echo 프로젝트 빌드 및 서버 실행 중...
echo 서버를 중지하려면 Ctrl+C를 누르세요.
echo.

cd /d "%~dp0"
call mvn clean package tomcat7:run

pause

