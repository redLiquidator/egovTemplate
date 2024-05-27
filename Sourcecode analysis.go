egovMSA-AuthServer
egovMSA-Customer
egovMSA-Catalogs
egovMSA-ConfigClient
egovMSA-ConfigClientBus
egovMSA-ConfigServer
egovMSA-docker-compose
egovMSA-EurekaServer 
egovMSA-GatewayServer
totally9 projects: AuthServer, Catalogs, ConfigClient, ConfigClientBus, ConfigServer, Customers, docker-compose, EurekaServer,GatewayServer
 with Docker, OpenSearch, RabbitMQ

Auth 인증 토근발급
localhost:8080/auth/authenticate?username=admin&password=admin

eyJ0eXBlIjoiand0IiwiYWxnIjoiSFM1MTIifQ.eyJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjoiUk9MRV9BRE1JTiIsImlhdCI6MTcxNjE4MjQ1OCwiZXhwIjoxNzE2MTgyNzU4fQ.FgqtI7TCgqoXRYBLdRK0UrPsy9uQaaSoFZFF08vmKWCgAVznjywL-lt-I3wCYBo3VDIiRKqEQsj1nz_d5aknzw

## React
# day1 2024/05/17
참고: https://codedthemes.gitbook.io/mantis/figma

workspace: C:\VueReact\mantis-material-react-3.1.0

--mentis seed 프로젝트-- 
-메뉴/화면 추가 방법
pages.jsx : 메뉴명 추가(view)
MainRoutes.jsx : 메인화면에서 클릭해서 진입하는 경로 추가

-egov template 프로젝트-

# day2
VSCODE에서 키워드로 검색: ctrl + shift + f
VSCODE에서 파일명으로 검색: ctrl + p
VSCODE에서 주석: ctrl + /

axios 사용참고: JWTContext.jsx

# project template

1.동작 프로세스
  1.1 로그인
   EgovLoginContect.jsx 에서 id,pwd 입력하여 로그인클릭 -> 
   <백엔드>
   EgovLoginApiController.java 의 /auth/login-jwt 으로 이동 ->
   EgovLoginServiceImpl.java 의 actionLogin 에서 로그인처리:
			1. 입력한 비밀번호를 암호화 
			2. 아이디와 암호화된 비밀번호가 DB와 일치하는지 확인
			3. 결과를 리턴

   1.2 비밀번호변경
   EgovAdminPasswordUpdate.jsx 에서 변경버튼 클릭 ->
   EgovSiteManagerApiController.java 의 /adminPassword 으로 이동
   메소드 updateAdminPassword 에서 비밀번호변경처리

   1.3 회원등록
   
   1.3.1 아이디 중복확인
   EgovRegister.jsx 에서 중복확인 버튼클릭
   EgovUserRegisterController.java 의 /idCheck 으로 이동
   userRegisterService.countId 메소드로 아이디 중복 체크 
   (userRegisterServiceImpl->LoginDAO.java->EgovLoginUsr_SQL_mysql.xml)

   1.3.2 회원등록신청
   EgovRegister.jsx 에서 회원등록신청 버튼클릭
   EgovUserRegisterController.java 의 /userRegister으로 이동
   EgovUserRegisterService.java 의 userRegister 에서 회원등록
2.frontend 
 2.1 이미지파일위치 /assets/images/
 2.2 url등록 
	url.js
	index.jsx : url.js 에서 등록한 코드화한 각 화면 진입 경로 추가
 2.3 화면개요
	메인화면 전체 프레임 index.jsx
	메인화면 중 body EgovMain.jsx 
	메인화면 중 header EgovHeader.jsx
	메인화면 중 footer EgovFooter.jsx

	왼쪽네이게이션 EgovLeftNavIntro.jsx



3.backend 
#hsql - local hssql 사용시에 적용 (내장 hsql은 정보 필요 없음)
Globals.hsql.DriverClassName=net.sf.log4jdbc.DriverSpy
Globals.hsql.Url=jdbc:log4jdbc:hsqldb:hsql://127.0.0.1/sampledb
Globals.hsql.UserName=sa
Globals.hsql.Password=


0.TEST 중...
eGovFrame > start > new eGovFrame MSA Template Project
MSA boot template project - service portal 기능 확인하기. React 와 연동 되는지?
gradle로 자동생성된다. JPA로 되어있음
1. 게시판서비스 BoardServiceClient.java, BoardResponseDto.java,
2. 배너지원 Banner.java
3. 공통코드 crud 지원 Code.java
4. 메뉴지원 Menu.java
5. 메뉴권한 MenuRole.java


*참고 from internet*
Board Service : 게시판관리, 게시물관리, 첨부파일관리 기능을 제공
Portal Service : 메뉴관리, 권한관리, 공통코드관리 및 컨텐츠관리 등의 기능을 제공
User Service : 로그인, 회원관리 및 관리자 기능을 제공
