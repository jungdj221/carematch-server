---
trigger: always_on
---

# 이정표 (CareMatching Platform) 백엔드 규칙

## 1. 프로젝트 정체성 및 인프라
- **팀명**: 대학생 창업팀 '이정표'
- **프로젝트**: 이정표 (CareMatching Platform)
- **인프라**: Cloudtype (Client & Server)
- **데이터베이스**: AWS RDS MySQL
- **형상 관리**: GitHub (Git Workflow: `feature/도메인-기능` -> `develop` -> `main`)

## 2. 핵심 기술 스택
- **언어**: Java 21 (LTS)
- **프레임워크**: Spring Boot 4.0.1
- **빌드 툴**: Gradle (Groovy)
- **보안**: Spring Boot Starter Security (테스트용 `security-test` 포함)
- **JPA/ORM**: Spring Boot Starter Data JPA, MySQL Connector-J
- **QueryDSL**: 버젼 5.0.0 (`querydsl-jpa:5.0.0:jakarta`, `querydsl-apt:5.0.0:jakarta` 필수 사용, `jakarta.persistence` 어노테이션 사용)
- **인증 (JWT)**: JJWT 0.12.6 (`jjwt-api`, `jjwt-impl`, `jjwt-jackson`)
- **Web 통신**: Spring Boot Starter Web, Spring Boot Starter WebFlux (WebClient)
- **엑셀 처리**: Apache POI 5.5.0 (`poi`, `poi-ooxml`)
- **유틸리티**: Lombok, JetBrains Annotations (`org.jetbrains:annotations:24.0.0`)

## 3. RESTful API 설계 규칙
- **URL 구조**: `/Service/API/Version/AccessScope/Resource/SubResource`
  - *예시*: `POST /user-service/api/v1/public/auth/tokens`
- **명명 규칙**: Resource(5단계)와 SubResource(6단계)는 반드시 **복수형 명사** 사용.
- **인증 범위 (AccessScope)**: `public`이면 비로그인 접근 가능. 생략되거나 다른 값이면 로그인 필수.

## 4. 협업 및 운영 원칙
- **PR 정책**: `develop` 및 `main` 브랜치는 최소 1명 이상의 Approve 필수.
- **빌드 관리**: QueryDSL Q클래스 생성 이슈 발생 시 `clean`, `compileJava`를 최우선으로 실행하여 대응.
- **코드 주석**: 코드 블록 내부 주석은 개발자 간 소통처럼 '음슴체' 사용. (예: `// NPE 방지용 추가함`)
- **답변 스타일**: 무리하게 코드를 짜주기보다는, '왜 이렇게 해야 하는지' 기술적 근거를 설명하며 멘토링 톤을 유지. 답변이 끝나면 다음에 사용자가 해야 할 고가치 동작을 제안.
