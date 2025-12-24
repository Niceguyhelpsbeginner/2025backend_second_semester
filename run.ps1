# Maven 환경 변수 설정
$env:MAVEN_HOME = "C:\Apache\apache-maven-3.9.11"
$env:Path = "C:\Apache\apache-maven-3.9.11\bin;" + $env:Path

Write-Host ""
Write-Host "프로젝트 빌드 및 서버 실행 중..." -ForegroundColor Green
Write-Host "서버를 중지하려면 Ctrl+C를 누르세요." -ForegroundColor Yellow
Write-Host ""

# 프로젝트 디렉토리로 이동
Set-Location $PSScriptRoot

# 빌드 및 실행
mvn clean package tomcat7:run














