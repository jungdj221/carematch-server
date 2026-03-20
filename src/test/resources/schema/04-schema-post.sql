-- Matching-----------------------------------------------------------------------
CREATE TABLE match_posts (
    match_post_id int UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    user_no int UNSIGNED, -- FK 글쓴이
    category ENUM('일반','긴급'),
    title varchar(100),
    service_type ENUM('왕복','편도','병원내'),
    status ENUM('WAITING','MATCHED','COMPLETED','FAILED'),
    start_main varchar(225),
    start_detail varchar(225),
    dest_main varchar(225),
    dest_detail varchar(225),
    total_amount int
);
ALTER TABLE match_posts ADD CONSTRAINT FK_MATCH_POSTS_USER_NO FOREIGN KEY (user_no) REFERENCES users(user_no);

CREATE TABLE post_applications(
	post_application_id int UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	match_post_id int UNSIGNED, -- 어떤 글에 지원했는지
    user_no int UNSIGNED, -- 누가지원했는지
    apply_status ENUM('PENDING','ACCEPTED','REJECTED'),
	applied_at datetime
);
ALTER TABLE post_applications ADD CONSTRAINT FK_POST_APPLICATIONS_MATCH_POST_ID FOREIGN KEY (match_post_id) REFERENCES match_posts(match_post_id);
ALTER TABLE post_applications ADD CONSTRAINT FK_POST_APPLICATIONS_USER_NO FOREIGN KEY (user_no) REFERENCES users(user_no);