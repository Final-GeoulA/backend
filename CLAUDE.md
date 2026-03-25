# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 빌드 및 실행 명령어

```bash
# 빌드
./gradlew build

# 실행
./gradlew bootRun

# 테스트
./gradlew test
# 단일 테스트 실행
./gradlew test --tests "kr.co.ictedu.geoulA.ClassName.methodName"
```

- **서버 포트:** 80
- **Context Path:** `/geoulA`
- **Java 버전:** 21

## 아키텍처 개요

Spring Boot 3.5.11 기반 REST API 서버. **Controller → Service → DAO → MyBatis Mapper(XML)** 패턴을 따른다.

### 주요 모듈

| 패키지 | 역할 |
|--------|------|
| `users/` | 회원가입, 이메일 인증, 프로필 관리 |
| `board/` | 게시글 CRUD, 댓글, 좋아요, 페이지네이션 |
| `login/` | 세션 기반 로그인 + AOP 로그인 로깅 |
| `passwordless/` | QR 기반 패스워드리스 인증 (외부 API 연동) |
| `vo/` | 공유 Value Object/DTO 클래스 |

### 세션 로깅 (AOP)

`login/aop/LoginAdvice`가 `@Around` 어드바이스로 로그인 성공 시 IP, User-Agent, 타임스탬프를 `login_log` 테이블에 기록한다.

### 패스워드리스 인증 흐름

외부 Passwordless API(`config.properties`에 자격증명 설정)를 사용하며, QR 등록 → WebSocket 실시간 승인 알림 → AES 암호화 토큰 검증 → 5분 만료 순서로 동작한다.

### 페이지네이션

`PageVO` 클래스가 페이지 번호, 블록 단위 페이지, 레코드 수를 관리한다. 게시글 목록과 댓글 모두에서 사용된다.

## 데이터베이스

- **Oracle XE** — `jdbc:oracle:thin:@localhost:1521/XEPDB1` (username/password: `geoula`)
- **MyBatis** ORM, mapper XML: `src/main/resources/mappers/*.xml`
- **Redis** (localhost:6379) — 이메일 인증번호 임시 저장

## 주요 설정 파일

- `src/main/resources/application.properties` — DB, Redis, SMTP(Naver), 파일 업로드 경로
- `src/main/resources/properties/config.properties` — 패스워드리스 API 자격증명 및 엔드포인트

## CORS

허용 Origin: `localhost:3000`, `localhost:3001`, `192.168.56.1:3000`, `192.168.56.1:3001` (credentials 허용)

## 파일 업로드

`application.properties`의 업로드 경로가 Windows 절대경로로 하드코딩되어 있으니, 로컬 환경에 맞게 수정 필요.
