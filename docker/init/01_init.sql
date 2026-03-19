-- =============================================
-- GeoulA 프로젝트 초기 테이블 및 시퀀스 생성
-- APP_USER(geoula) 권한으로 실행됨
-- =============================================

-- 1. users 테이블
CREATE TABLE users (
    user_id    NUMBER PRIMARY KEY,
    email      VARCHAR2(100) UNIQUE NOT NULL,
    password   VARCHAR2(200) NOT NULL,
    nickname   VARCHAR2(50),
    age        NUMBER,
    skin_type  VARCHAR2(20),
    gender     VARCHAR2(10)
);

CREATE SEQUENCE seq_users START WITH 1 INCREMENT BY 1 NOCACHE;

-- 2. board_skin 테이블
CREATE TABLE board_skin (
    num        NUMBER PRIMARY KEY,
    title      VARCHAR2(200),
    writer     VARCHAR2(100),
    content    CLOB,
    imgn       VARCHAR2(200),
    hit        NUMBER DEFAULT 0,
    elike      NUMBER DEFAULT 0,
    reip       VARCHAR2(50),
    bdate      DATE DEFAULT SYSDATE,
    member_num NUMBER
);

CREATE SEQUENCE SEQ_BOARD_SKIN START WITH 1 INCREMENT BY 1 NOCACHE;

-- 3. board_skin_comm 테이블 (댓글)
CREATE TABLE board_skin_comm (
    num     NUMBER PRIMARY KEY,
    ucode   NUMBER,           -- 게시글 번호 (board_skin.num 참조)
    uwriter VARCHAR2(100),
    ucontent VARCHAR2(1000),
    reip    VARCHAR2(50),
    bcdate  DATE DEFAULT SYSDATE
);

CREATE SEQUENCE board_comm_seq START WITH 1 INCREMENT BY 1 NOCACHE;

-- 4. login_log 테이블
CREATE TABLE login_log (
    login_log_id NUMBER PRIMARY KEY,
    idn          NUMBER,           -- users.user_id 참조
    reip         VARCHAR2(50),
    uagent       VARCHAR2(500),
    status       VARCHAR2(20),
    sstime       VARCHAR2(50),
    eetime       VARCHAR2(50)
);

CREATE SEQUENCE seq_login_log START WITH 1 INCREMENT BY 1 NOCACHE;
