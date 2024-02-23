# 채용과제
## 주제: 호스트들의 Alive 상태 체크 및 모니터링 API 서버 개발

### 1. 구성 정보
* Spring Boot 3.2.3
* Java 17 (Amazon Corretto 17)
* MySQL 8.0.18 / MyBatis


### 2. 실행 방법
1. `cd {project}/gradlew bootjar` : jar 파일 생성
2. `cd {project}/build/libs` : jar 파일 위치
3. `java -jar recruit-0.0.1-SNAPSHOT.jar` : Application 실행


### 3. REST API 스펙
프로젝트 실행 후 아래 URL로 접근 가능 (Swagger)
* http://localhost:8080/swagger-ui/index.html

### 4. 기타사항
* DDL SQL 위치 : `{project}/ddl.sql`
* DB 주소 및 계정 변경 : `{project}/src/main/resources/application-local.properties` 수정
* 테스트 계정
  * 관리자 : adminid / 12345
  * 일반 : memberid / 12345

### 5. 추가사항
* Quartz를 사용하여 매 10초마다 등록된 호스트에 대한 상태체크를 수행하고 DB에 기록하도록 구현함.
* jar 폴더 위치 기준으로 `logs` 폴더 생성되며 로그파일 기록
  * Log 레벨별로 파일 저장 (info.log, error.log, debug.log)
  * 오래된 로그들은 was-logs 폴더에 별도 저장됨