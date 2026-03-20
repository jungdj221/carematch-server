-- -- Auth------------------------------------------------------------------------
-- 1:1
CREATE TABLE social_logins(
	social_login_id int UNSIGNED PRIMARY KEY, -- 
    user_no int UNSIGNED UNIQUE, --  FK
    social_code tinyint UNSIGNED, -- 어떤 소셜 서비스를 사용하는지 구분하는 코드 ex) 1=카카오 2=네이버
    external_id varchar(64), -- 제일 중요한 컬럼, 카카오,네이버 등에서 해당 사용자에게 부여하는 고유 식별 번호
    access_token varchar(256), -- 이거는 외부 앱(카카오,네이버 등)에서 해당 유저한테 할당되는 access_token 자사 고유의 토큰이랑은 별개의 값
    update_date datetime -- 정보 업데이트 날짜 
);
ALTER TABLE social_logins ADD CONSTRAINT FK_SOCIAL_LOGINS_USER_NO FOREIGN KEY (user_no) REFERENCES users(user_no);

-- 1:1 drop 후 재생성
CREATE TABLE credentials(
	password_id int UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    user_no int UNSIGNED UNIQUE, -- FK
    salt varchar(128), -- 비번옆에 붙을 무작위 문자열
    password varchar(128),
    update_date datetime DEFAULT NOW()
);
ALTER TABLE credentials ADD CONSTRAINT FK_CREDENTIALS_USER_NO FOREIGN KEY (user_no) REFERENCES users(user_no);

-- 아마 1:n? 
CREATE TABLE tokens( -- device 테이블과 직접 조인 or 따로?
	token_id bigint UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    user_no int UNSIGNED, -- FK
    refresh_token varchar(512) UNIQUE NOT NULL,
    expires_at datetime NOT NULL,
    created_at datetime DEFAULT NOW(),
    device_info varchar(255) DEFAULT NULL-- 로그인 기기 정보. 없을 수도 있음.
);
ALTER TABLE tokens ADD CONSTRAINT FK_TOKENS_USER_NO FOREIGN KEY (user_no) REFERENCES users(user_no);

/*
CI : Connecting Information. 온라인에서 개인식별을 위해 주민등록번호에 기반하여 생성된 일종의 온라인 주민등록번호. 88 byte
온라인 서비스에서 개인식별을 위해 주민등록번호 사용 -> 번호유출우려로 2014년 8월부터 주민등록번호 수집금지제도 시행.
=> 서비스는 주민등록번호 대신 개인을 식별할 수 있는 키가 필요해짐. 대체수단으로 CI가 만들어짐
특징 : 주민등록번호 기반으로 생성되어 유일성 보장 + 복호화 불가. 본인확인기관에서 본인확인시 CI 전달해줌.

DI : Duplicated Joining Verification Information. CI는 개인의 주민등록번호를 hash화.
while DI는 주민등록번호와 각 웹사이트의 식별번호를 가지고 생성하는 방식. 66 byte
특징 : 특정 개인을 식별하가 위해 해당 기관에서 고유한 로컬 키값. 중복가입 확인용도. 특정 유저가 가입한 이력이 있는지 등을 확인위해.
=> 고유한 로컬 키값. 인증 업체마다 같은 개인이라도 다르게 발급. 다른 서비스와 연계 불가. 결국 중복가입 막는용도 -> 유저 차단도 가능

*/
CREATE TABLE identifications( -- 해당란의 개인정보들은 암호화 필요	
    identification_id int UNSIGNED PRIMARY KEY,
    user_no int UNSIGNED UNIQUE, -- FK
    ci varchar(88), -- 정보통신법에 따라서 쓸지말지 결정
    di varchar(64) -- 얘는 필수
);
ALTER TABLE identifications ADD CONSTRAINT FK_IDENTIFICATIONS_USER_NO FOREIGN KEY (user_no) REFERENCES users(user_no);
/*
가입&인증 시: 타업체 인증을 통해 정확한 개인정보(성별, 연령 등) 획득.
저장 시: 해당 정보를 member_authentication에 넣고 암호화, 인증 완료 시점을 auth_date에 기록.
서비스 운영: 일반적인 활동은 member.user 정보만 활용 (빠른 성능).
분석&통계 시: member_authentication을 복호화하여 정확한 사용자 층 분석.
*/-- 1:1
-- --------------------------------------------------------------------------- 