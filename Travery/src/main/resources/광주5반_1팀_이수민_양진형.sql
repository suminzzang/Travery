CREATE SCHEMA IF NOT EXISTS `ssafyproject` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `ssafyproject` ;
-- 멤버 테이블
DROP TABLE IF EXISTS `ssafyproject`.`members` ;
CREATE TABLE IF NOT EXISTS `ssafyproject`.`members` (
  `user_id` VARCHAR(16) NOT NULL,
  `user_name` VARCHAR(20) NOT NULL,
  `user_password` VARCHAR(100) NOT NULL,
  `email_id` VARCHAR(20) NULL DEFAULT NULL,
  `email_domain` VARCHAR(45) NULL DEFAULT NULL,
  `phone_number` CHAR(11) NULL DEFAULT NULL,
  `user_sido` INT NULL DEFAULT NULL,
  `user_gugun` INT NULL DEFAULT NULL,
  `join_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
  `token` VARCHAR(1000) NULL DEFAULT NULL,
  `salt` CHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- 메일인증 테이블
DROP TABLE IF EXISTS `ssafyproject`.`mail` ;
CREATE TABLE IF NOT EXISTS `ssafyproject`.`mail` (
  `token` VARCHAR(1000) NOT NULL,
  `mail_key` CHAR(6) NOT NULL,
  `salt` CHAR(20) NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- 공지사항 테이블
DROP TABLE IF EXISTS `ssafyproject`.`adminboard` ;
CREATE TABLE IF NOT EXISTS `ssafyproject`.`adminboard` (
  `article_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(16) NULL DEFAULT NULL,
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `hit` INT NULL DEFAULT 0,
  `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`article_no`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- 핫플레이스 테이블
DROP TABLE IF EXISTS `ssafyproject`.`photourl` ;
DROP TABLE IF EXISTS `ssafyproject`.`hotplaceboard` ;
CREATE TABLE IF NOT EXISTS `ssafyproject`.`hotplaceboard` (
  `article_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(16) NULL DEFAULT NULL,
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `content` text NULL DEFAULT NULL,
  `hit` INT NULL DEFAULT 0,
  `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `first_img` MediumBLOB NULL DEFAULT NULL,
   `content_id` INT NOT NULL,
  `title` VARCHAR(200) NULL DEFAULT NULL,
  `addr1` VARCHAR(300) NULL DEFAULT NULL,
  `overview` VARCHAR(10000) NULL DEFAULT NULL,
  PRIMARY KEY (`article_no`)
  )
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- 일반 게시글 테이블
DROP TABLE IF EXISTS `ssafyproject`.`userboard` ;
CREATE TABLE IF NOT EXISTS `ssafyproject`.`userboard` (
  `article_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(16) NULL DEFAULT NULL,
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `hit` INT NULL DEFAULT 0,
  `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`article_no`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- qna 게시글 본문 테이블
DROP TABLE IF EXISTS `ssafyproject`.`qnaboardreply` ;
DROP TABLE IF EXISTS `ssafyproject`.`qnaboard` ;
CREATE TABLE IF NOT EXISTS `ssafyproject`.`qnaboard` (
  `article_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(16) NULL DEFAULT NULL,
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `type` INT NULL default 0,
  `hit` INT NULL DEFAULT 0,
  `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`article_no`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- qna 게시글 본문 답글 테이블
DROP TABLE IF EXISTS `ssafyproject`.`qnaboardreply` ;
CREATE TABLE IF NOT EXISTS `ssafyproject`.`qnaboardreply` (
  `article_no` INT NOT NULL AUTO_INCREMENT,
  `parent_no` INT NULL default null,
  `user_id` VARCHAR(16) NULL DEFAULT NULL,
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `type` INT NULL default 0,
  `hit` INT NULL DEFAULT 0,
  `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`article_no`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `ssafyproject`.`qnaboardreply`
ADD CONSTRAINT `fk_qnaboardreply_qnaboard`
FOREIGN KEY (`parent_no`)
REFERENCES `ssafyproject`.`qnaboard` (`article_no`)
ON DELETE CASCADE
ON UPDATE CASCADE;

DROP TABLE IF EXISTS `ssafyproject`.`file_info` ;

CREATE TABLE IF NOT EXISTS `ssafyproject`.`file_info` (
  `idx` INT NOT NULL AUTO_INCREMENT,
  `article_no` INT NULL,
  `file_type` INT NULL,
  `save_folder` VARCHAR(45) NULL,
  `original_file` VARCHAR(50) NULL,
  `save_file` VARCHAR(50) NULL,
  PRIMARY KEY (`idx`))
ENGINE = InnoDB;

select * from userboard;
select * from file_info;

select save_folder, original_file, save_file, file_type
from file_info
where article_no = 1 AND file_type=2;

DROP TABLE IF EXISTS `ssafyproject`.`tripdetail` ;
DROP TABLE IF EXISTS `ssafyproject`.`tripplanBoard` ;
CREATE TABLE IF NOT EXISTS `ssafyproject`.`tripplanBoard` (
  `article_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(16) NULL DEFAULT NULL,
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `start_date` VARCHAR(50) NULL DEFAULT NULL,
  `end_date` VARCHAR(50) NULL DEFAULT NULL,
  `hit` INT NULL DEFAULT 0,
  `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`article_no`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- qna 게시글 본문 답글 테이블
DROP TABLE IF EXISTS `ssafyproject`.`tripdetail` ;
CREATE TABLE IF NOT EXISTS `ssafyproject`.`tripdetail` (
  `article_no` INT NOT NULL AUTO_INCREMENT,
  `parent_no` INT NOT NULL,
  `content_id` INT NOT NULL,
  `title` VARCHAR(200) NULL DEFAULT NULL,
  `addr1` VARCHAR(300) NULL DEFAULT NULL,
  `overview` VARCHAR(10000) NULL DEFAULT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `start_date` VARCHAR(50) NULL DEFAULT NULL,
  `end_date` VARCHAR(50) NULL DEFAULT NULL,
  PRIMARY KEY (`article_no`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `ssafyproject`.`tripdetail`
ADD CONSTRAINT `fk_tripdetail_tripplanBoard`
FOREIGN KEY (`parent_no`)
REFERENCES `ssafyproject`.`tripplanBoard` (`article_no`)
ON DELETE CASCADE
ON UPDATE CASCADE;

DROP TABLE IF EXISTS `ssafyproject`.`photourl` ;
CREATE TABLE IF NOT EXISTS `ssafyproject`.`photourl` (
  `article_no` INT NOT NULL AUTO_INCREMENT,
  `parent_no` INT NOT NULL,
  `photo_url` MediumBLOB NULL DEFAULT NULL,
  PRIMARY KEY (`article_no`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `ssafyproject`.`photourl`
ADD CONSTRAINT `fk_photourl_hotplaceboard`
FOREIGN KEY (`parent_no`)
REFERENCES `ssafyproject`.`hotplaceboard` (`article_no`)
ON DELETE CASCADE
ON UPDATE CASCADE;