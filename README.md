# Spring MVC 커뮤니티 사이트 프로젝트

Spring MVC 프레임워크를 사용한 Java 웹 애플리케이션 프로젝트입니다.

## 개발 환경

- **Java**: JDK 11 이상
- **프레임워크**: Spring Framework 5.3.21
- **빌드 도구**: Maven 3.6 이상
- **데이터베이스**: MariaDB
- **서버**: Apache Tomcat 7 이상

## STS(Spring Tool Suite)에서 실행하는 방법

### 1단계: 프로젝트 임포트

1. **STS 실행**
   - Spring Tool Suite를 실행합니다.

2. **프로젝트 임포트**
   - `File` → `Import` 선택
   - `Maven` → `Existing Maven Projects` 선택
   - `Next` 클릭

3. **프로젝트 디렉토리 선택**
   - `Browse` 버튼 클릭
   - 프로젝트 루트 디렉토리(`backend_project`) 선택
   - `pom.xml` 파일이 자동으로 인식됩니다
   - `Finish` 클릭

4. **Maven 프로젝트 업데이트**
   - 프로젝트를 우클릭 → `Maven` → `Update Project...`
   - `Force Update of Snapshots/Releases` 체크
   - `OK` 클릭하여 의존성 다운로드

### 2단계: 데이터베이스 설정

1. **MariaDB 데이터베이스 생성**
   ```sql
   CREATE DATABASE communitydb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. **데이터베이스 연결 설정 확인**
   - `src/main/webapp/WEB-INF/spring/root-context.xml` 파일 확인
   - 데이터베이스 URL, 사용자명, 비밀번호가 올바른지 확인

### 3단계: 서버 설정

1. **Tomcat 서버 추가**
   - `Servers` 탭에서 우클릭 → `New` → `Server`
   - `Apache` → `Tomcat v7.0 Server` (또는 설치된 버전) 선택
   - `Next` 클릭
   - Tomcat 설치 경로 선택 (없으면 다운로드)
   - `Finish` 클릭

2. **프로젝트를 서버에 추가**
   - `Servers` 탭에서 생성한 Tomcat 서버를 우클릭
   - `Add and Remove...` 선택
   - 왼쪽에서 `community-site` 프로젝트 선택
   - `Add >` 버튼 클릭하여 오른쪽으로 이동
   - `Finish` 클릭

3. **서버 설정**
   - 서버를 우클릭 → `Properties`
   - `General` → `Runtime Environment`에서 Java 11 이상 선택
   - `Ports`에서 HTTP 포트 확인 (기본값: 8080)

### 4단계: 프로젝트 빌드

1. **프로젝트 빌드**
   - 프로젝트를 우클릭 → `Run As` → `Maven build...`
   - `Goals`에 `clean package` 입력
   - `Run` 클릭
   - 빌드가 완료될 때까지 대기

2. **빌드 확인**
   - `target` 폴더에 `community-site-1.0.0.war` 파일이 생성되었는지 확인

### 5단계: 서버 실행

1. **서버 시작**
   - `Servers` 탭에서 Tomcat 서버를 우클릭
   - `Start` 선택
   - 또는 서버를 선택하고 상단의 재생 버튼(▶) 클릭

2. **실행 확인**
   - 콘솔에 서버 시작 메시지 확인
   - 다음과 같은 메시지가 보이면 성공:
     ```
     Server startup in [시간] milliseconds
     ```

### 6단계: 브라우저에서 접속

1. **웹 브라우저 열기**
   - 브라우저에서 다음 주소로 접속:
     ```
     http://localhost:8080
     ```

2. **홈페이지 확인**
   - 게시글 목록이 표시되면 정상 작동합니다

## 주요 기능

- **회원 관리**: 회원가입, 로그인, 로그아웃, 프로필 조회
- **게시판**: 게시글 작성, 조회, 수정, 삭제, 목록 조회
- **댓글**: 댓글 작성, 수정, 삭제
- **게임 실행**: HTML/CSS/JavaScript 게임 코드 실행 기능
- **이미지 업로드**: 게시글 썸네일 이미지 업로드 및 표시

## 프로젝트 구조

```
backend_project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/community/
│   │   │       ├── controller/    # 컨트롤러
│   │   │       ├── service/       # 서비스 레이어
│   │   │       ├── dao/          # 데이터 접근 객체
│   │   │       ├── model/        # 모델 클래스
│   │   │       └── util/         # 유틸리티 클래스
│   │   ├── resources/
│   │   │   ├── schema.sql        # 데이터베이스 스키마
│   │   │   └── data.sql          # 초기 데이터
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── web.xml
│   │       │   ├── spring/
│   │       │   │   ├── root-context.xml
│   │       │   │   └── appServlet/
│   │       │   │       └── servlet-context.xml
│   │       │   └── views/        # JSP 뷰 파일
│   │       └── resources/        # 정적 리소스
│   └── test/
└── pom.xml                        # Maven 설정 파일
```

## 문제 해결

### 포트 8080이 이미 사용 중인 경우
- `Servers` 탭에서 서버를 우클릭 → `Properties`
- `Ports`에서 HTTP 포트를 `8081` 등으로 변경

### 컴파일 오류 발생 시
- 프로젝트를 우클릭 → `Maven` → `Update Project...`
- `Force Update of Snapshots/Releases` 체크 후 `OK`
- `Project` → `Clean...` → 프로젝트 선택 → `Clean`

### 데이터베이스 연결 오류 시
- `root-context.xml`에서 데이터베이스 연결 정보 확인
- MariaDB 서버가 실행 중인지 확인
- 데이터베이스가 생성되어 있는지 확인

### 서버가 시작되지 않는 경우
- `Servers` 탭에서 서버를 삭제하고 다시 추가
- `Window` → `Preferences` → `Server` → `Runtime Environments`에서 Tomcat 경로 확인

## 실행 스크립트 사용 (대안)

명령줄에서 실행하려면:

```bash
# Windows
run.bat

# PowerShell
run.ps1
```

또는 직접 Maven 명령어 사용:

```bash
mvn clean package tomcat7:run
```

## 라이선스

이 프로젝트는 교육 목적으로 제작되었습니다.
