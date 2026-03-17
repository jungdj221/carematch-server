-- ==========================================
-- DROP ALL TABLES IN REVERSE DEPENDENCY ORDER
-- ==========================================

-- 1. Drop 04-Post Domain (Depends on users)
DROP TABLE IF EXISTS post_applications; 
DROP TABLE IF EXISTS match_posts; 

-- 2. Drop 03-Payment Domain (Depends on users/subscriptions)
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS receipts;

-- 3. Drop 02-Auth Domain (Depends on users)
DROP TABLE IF EXISTS identifications;
DROP TABLE IF EXISTS tokens;
DROP TABLE IF EXISTS credentials;
DROP TABLE IF EXISTS social_logins;

-- 4. Drop 01-User Domain (Leaf nodes first, Root node last)
DROP TABLE IF EXISTS patient_profiles;
DROP TABLE IF EXISTS manager_profiles;
DROP TABLE IF EXISTS profiles;
DROP TABLE IF EXISTS subscription;
DROP TABLE IF EXISTS devices;
DROP TABLE IF EXISTS users;

-- User---------------------------------------------------------------------
CREATE TABLE users( -- 사용자의 기본 계정
	user_no int UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    user_name varchar(20),
	email varchar(128) NOT NULL, -- 필수, 암호화필수
    user_role varchar(10) NOT NULL DEFAULT "ROLE_USER",
    login_type ENUM('ID','KAKAO','GOOGLE')
);

CREATE TABLE profiles( -- 개인정보 분리
	profile_id int UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    user_no int UNSIGNED, -- FK
    image_url varchar(100),
    join_date datetime,
    update_date datetime
);
ALTER TABLE profiles ADD CONSTRAINT FK_PROFILES_USER_NO FOREIGN KEY (user_no) REFERENCES users(user_no);

CREATE TABLE manager_profiles(
	profile_id int UNSIGNED PRIMARY KEY, -- FK PK
    bio_content varchar(1000),
    career_info varchar(500),
    active_region varchar(500),
    is_premium_verified boolean
);
ALTER TABLE manager_profiles ADD CONSTRAINT FK_MANAGER_PROFILES_PROFILE_ID FOREIGN KEY (profile_id) REFERENCES profiles(profile_id);
/*
profile (부모 테이블):
id(PK, Auto Increment) - 예: 100번 프로필
user_id (FK)
name, gender, phone (공통 정보)
manager (자식 테이블):
profile_id (PK & FK) - 부모의 100번을 그대로 씁니다. (Auto Increment 아님!)
career_years, certifications (매니저 전용 정보)
patient (자식 테이블):
profile_id (PK & FK) - 부모의 100번을 그대로 씁니다.
disease_info, mobility_status (환자 전용 정보)
*/

CREATE TABLE patient_profiles(
	patient_profile_id int UNSIGNED PRIMARY KEY, -- PK
	profile_id int UNSIGNED, -- FK 
    patient_name varchar(10),
    patient_age tinyint, 
    patient_gender ENUM('M','F'),
    patient_condition VARCHAR(1000)
);
ALTER TABLE patient_profiles ADD CONSTRAINT FK_PATIENT_PROFILES_PROFILE_ID FOREIGN KEY (profile_id) REFERENCES profiles(profile_id);


-- 1:1
CREATE TABLE subscriptions( -- 구독 요금제
	subscription_id int UNSIGNED,
    user_no int UNSIGNED UNIQUE, -- FK
    subscription_plan tinyint UNSIGNED, -- 번호로 이용 서비스 기재
    purchase_date datetime,
    expiration_date datetime
);
ALTER TABLE subscriptions ADD CONSTRAINT FK_SUBSCRIPTIONS_USER_NO FOREIGN KEY (user_no) REFERENCES users(user_no);

/*
유저가 로그인하는 기기 정보를 담습니다. 어떤 기기가 많이 들어오는 통계 정보를 수집하고, 사용자 기기에 대한 대수 제한을 위함이며, 인증 되지 않은 기기의 사용을 제한하여 보안성을 높임
*/
-- 1:N 한명의 유저는 여러개의 디바이스를 가짐
CREATE TABLE devices( -- 사용자의 접속 보안과 멀티 디바이스 로그인 제어 담당.
	device_id int UNSIGNED PRIMARY KEY,
    user_no int UNSIGNED, -- FK
    in_use tinyint UNSIGNED, -- 0(로그아웃), 1(시용), 2.., 3.. 
    register_no tinyint UNSIGNED, -- 등록 순번. 한 유저가 여러 기기를 등록할 때 부여하는 순번(예: 1번 폰, 2번 태블릿)
    uuid varchar(36), -- 기기 고유값. 하드웨어 고유 식별자(UUID) 36자인 것으로 보아 표준 UUID 형식
    model varchar(30), -- 모델명. "iPhone 15", "Galaxy S24" 같은 물리적인 기기 이름
    os_type varchar(10), -- OS 종류. "iOS", "Android", "Windows"
    os_version varchar(10) -- OS 버전. "17.2", "14.0" 등 상세 버전
);
ALTER TABLE devices ADD CONSTRAINT FK_DEVICES_USER_NO FOREIGN KEY (user_no) REFERENCES users(user_no);
-- -------------------------------------------------------------------------------




