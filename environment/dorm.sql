-- Valentina Studio --
-- MySQL dump --
-- ---------------------------------------------------------


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
-- ---------------------------------------------------------


-- CREATE TABLE "dorm_question" --------------------------------
CREATE TABLE `dorm_question` ( 
	`qid` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`tips` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
	`introduction` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	`display_type` TinyInt( 255 ) NOT NULL,
	`choose_type` TinyInt( 255 ) NOT NULL,
	`option_json` LongText CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
	`question_type` TinyInt( 255 ) NOT NULL,
	`did` Int( 255 ) NOT NULL,
	`auto_weight` TinyInt( 4 ) NOT NULL DEFAULT 1,
	`weight` Double( 22, 5 ) NOT NULL DEFAULT -1.00000,
	CONSTRAINT `unique_uk_qid` UNIQUE( `qid` ) )
CHARACTER SET = utf8
COLLATE = utf8_general_ci
ENGINE = InnoDB
AUTO_INCREMENT = 7;
-- -------------------------------------------------------------


-- CREATE TABLE "dorm_user_chosen" -----------------------------
CREATE TABLE `dorm_user_chosen` ( 
	`cid` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`uid` Int( 255 ) NOT NULL,
	`chosen_json` LongText CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
	`pid` Int( 255 ) NOT NULL,
	CONSTRAINT `unique_cid` UNIQUE( `cid` ) )
CHARACTER SET = utf8
COLLATE = utf8_general_ci
ENGINE = InnoDB
AUTO_INCREMENT = 76;
-- -------------------------------------------------------------


-- CREATE TABLE "dorm_question_extra" --------------------------
CREATE TABLE `dorm_question_extra` ( 
	`eid` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`qid` Int( 255 ) NOT NULL,
	`ext_intro` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
	`ext_placeholder` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
	`ext_default_value` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
	`ext_add_button_value` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
	CONSTRAINT `unique_eid` UNIQUE( `eid` ) )
CHARACTER SET = utf8
COLLATE = utf8_general_ci
ENGINE = InnoDB
AUTO_INCREMENT = 3;
-- -------------------------------------------------------------


-- CREATE TABLE "dorm_account" ---------------------------------
CREATE TABLE `dorm_account` ( 
	`uid` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	`account` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	`password` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	`mail` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	`phone` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	`user_type` TinyInt( 255 ) NOT NULL,
	`name` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	`sex` TinyInt( 255 ) NOT NULL,
	`pid` Int( 255 ) NOT NULL,
	CONSTRAINT `unique_uid` UNIQUE( `uid` ) )
CHARACTER SET = utf8
COLLATE = utf8_general_ci
ENGINE = InnoDB;
-- -------------------------------------------------------------


-- CREATE TABLE "dorm_question_section" ------------------------
CREATE TABLE `dorm_question_section` ( 
	`sid` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`qid` Int( 255 ) NOT NULL,
	`section_json` LongText CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
	CONSTRAINT `unique_sid` UNIQUE( `sid` ) )
CHARACTER SET = utf8
COLLATE = utf8_general_ci
ENGINE = InnoDB
AUTO_INCREMENT = 5;
-- -------------------------------------------------------------


-- CREATE TABLE "dorm_question_direction" ----------------------
CREATE TABLE `dorm_question_direction` ( 
	`did` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`direction_name` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	`description` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	`weight` Double( 22, 0 ) NOT NULL,
	CONSTRAINT `unique_did` UNIQUE( `did` ) )
CHARACTER SET = utf8
COLLATE = utf8_general_ci
ENGINE = InnoDB
AUTO_INCREMENT = 1;
-- -------------------------------------------------------------


-- CREATE TABLE "dorm_question_plan" ---------------------------
CREATE TABLE `dorm_question_plan` ( 
	`pid` Int( 255 ) AUTO_INCREMENT NOT NULL,
	`qid_json` LongText CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
	`plan_name` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	`description` VarChar( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
	`done` TinyInt( 1 ) NOT NULL,
	CONSTRAINT `unique_pid` UNIQUE( `pid` ) )
CHARACTER SET = utf8
COLLATE = utf8_general_ci
ENGINE = InnoDB
AUTO_INCREMENT = 8;
-- -------------------------------------------------------------


-- Dump data of "dorm_question" ----------------------------
INSERT INTO `dorm_question`(`qid`,`tips`,`introduction`,`display_type`,`choose_type`,`option_json`,`question_type`,`did`,`auto_weight`,`weight`) VALUES 
( '1', '情根据您的个人习惯进行选择哦!', '我的学习方式?', '0', '1', '["独自学习","共同学习","都可以"]', '1', '0', '1', '-1.00000' ),
( '2', '可以选择优先和老乡一个寝室!', '我们来自哪里?', '1', '1', '["同样的城市","不同的城市","都可以"]', '1', '0', '1', '-1.00000' ),
( '3', '我们会为您分配作息时间相似的同学!', '我们的就寝时间?', '2', '0', NULL, '3', '0', '1', '-1.00000' ),
( '4', '大家一起玩才有趣!', '我的爱好有哪些?', '3', '2', '["篮球","羽毛球"]', '2', '0', '1', '-1.00000' ),
( '5', '这是大家想要的寝室的模样!', '你希望寝室是什么样子的?', '6', '2', '["热闹","安静","整洁明亮","归属感"]', '2', '0', '1', '-1.00000' ),
( '6', NULL, '你不希望寝室是什么样子的?', '6', '3', '["吵闹","冷清","杂乱","异味","阴暗","沉迷游戏"]', '2', '0', '1', '-1.00000' ),
( '7', '单选提示1', '单选题目1', '1', '1', '["单选选项1","单选选项2"]', '1', '0', '1', '0.50000' );
-- ---------------------------------------------------------


-- Dump data of "dorm_user_chosen" -------------------------
INSERT INTO `dorm_user_chosen`(`cid`,`uid`,`chosen_json`,`pid`) VALUES 
( '72', '0', '[{"qid":1,"radio":0},{"qid":3,"result":[{"stress":0,"word":"05:00 - 21:00"},{"stress":0,"word":"07:00 - 23:00"}]},{"qid":5,"result":[{"stress":0,"word":"热闹"},{"stress":0,"word":"安静"}]},{"qid":6,"result":[{"stress":0,"word":"异味"},{"stress":0,"word":"阴暗"},{"stress":0,"word":"沉迷游戏"}]}]', '1' ),
( '73', '1', '[{"qid":1,"radio":0},{"qid":3,"result":[{"stress":0,"word":"07:00 - 23:00"},{"stress":0,"word":"05:00 - 21:00"}]},{"qid":5,"result":[{"stress":0,"word":"热闹"},{"stress":0,"word":"安静"}]},{"qid":6,"result":[{"stress":1,"word":"异味"},{"stress":1,"word":"阴暗"},{"stress":1,"word":"沉迷游戏"}]}]', '1' ),
( '74', '2', '[{"qid":1,"radio":1},{"qid":3,"result":[{"stress":0,"word":"06:00 - 22:00"}]},{"qid":5,"result":[{"stress":1,"word":"热闹"},{"stress":2,"word":"安静"}]},{"qid":6,"result":[{"stress":1,"word":"异味"},{"stress":1,"word":"阴暗"},{"stress":1,"word":"沉迷游戏"}]}]', '1' ),
( '75', '3', '[{"qid":1,"radio":1},{"qid":3,"result":[{"stress":0,"word":"06:00 - 23:00"}]},{"qid":5,"result":[{"stress":1,"word":"热闹"},{"stress":2,"word":"安静"}]},{"qid":6,"result":[{"stress":2,"word":"异味"},{"stress":1,"word":"阴暗"},{"stress":1,"word":"沉迷游戏"}]}]', '1' );
-- ---------------------------------------------------------


-- Dump data of "dorm_question_extra" ----------------------
INSERT INTO `dorm_question_extra`(`eid`,`qid`,`ext_intro`,`ext_placeholder`,`ext_default_value`,`ext_add_button_value`) VALUES 
( '1', '2', '我来自', '格式: xxx省/xxx市', '辽宁省/沈阳市', '' ),
( '2', '4', '其他爱好', '爱好', '', '添加' ),
( '3', '7', '单选测试描述1', 'eph', 'eph', 'eav' );
-- ---------------------------------------------------------


-- Dump data of "dorm_account" -----------------------------
INSERT INTO `dorm_account`(`uid`,`account`,`password`,`mail`,`phone`,`user_type`,`name`,`sex`,`pid`) VALUES 
( '0001', '123', '202cb962ac59075b964b07152d234b70', '123', '123', '2', 'ads1', '1', '0' ),
( '001', '', '', '', '', '0', 'n1', '1', '0' ),
( 'dsds1', '', '', '', '', '0', 'ads4', '1', '6' ),
( 'dsds2', '', '', '', '', '0', 'ads5', '0', '7' ),
( 'dsds5', '', '', '', '', '0', 'ads6', '1', '5' );
-- ---------------------------------------------------------


-- Dump data of "dorm_question_section" --------------------
INSERT INTO `dorm_question_section`(`sid`,`qid`,`section_json`) VALUES 
( '3', '3', '[{"selectOption":["05:00","06:00","07:00"],"selectIntro":"起床"},{"selectOption":["21:00","22:00","23:00"],"selectIntro":"睡觉"}]' ),
( '4', '15', '[{"selectOption":["upup","up1"],"selectIntro":"UP"},{"selectOption":["down","d1"],"selectIntro":"Down"}]' );
-- ---------------------------------------------------------


-- Dump data of "dorm_question_direction" ------------------
-- ---------------------------------------------------------


-- Dump data of "dorm_question_plan" -----------------------
INSERT INTO `dorm_question_plan`(`pid`,`qid_json`,`plan_name`,`description`,`done`) VALUES 
( '1', '[3,4,5,6,1,2]', '测试计划', '测试用的计划', '0' ),
( '2', '[]', 'P123', '', '0' ),
( '3', '[]', 'P567', '', '0' ),
( '4', '[]', 'P908', '', '0' ),
( '5', '[]', 'addPlanName', '', '0' ),
( '6', '[]', '343', '', '0' ),
( '7', '[]', '121', '', '0' );
-- ---------------------------------------------------------


-- CREATE INDEX "index_pid" ------------------------------------
CREATE INDEX `index_pid` USING BTREE ON `dorm_user_chosen`( `pid` );
-- -------------------------------------------------------------


-- CREATE INDEX "index_qid" ------------------------------------
CREATE INDEX `index_qid` USING BTREE ON `dorm_question_extra`( `qid` );
-- -------------------------------------------------------------


-- CREATE INDEX "index_account" --------------------------------
CREATE INDEX `index_account` USING BTREE ON `dorm_account`( `account` );
-- -------------------------------------------------------------


-- CREATE INDEX "index_pid1" -----------------------------------
CREATE INDEX `index_pid1` USING BTREE ON `dorm_account`( `pid` );
-- -------------------------------------------------------------


-- CREATE INDEX "index_qid1" -----------------------------------
CREATE INDEX `index_qid1` USING BTREE ON `dorm_question_section`( `qid` );
-- -------------------------------------------------------------


-- CREATE INDEX "index_name" -----------------------------------
CREATE INDEX `index_name` USING BTREE ON `dorm_question_direction`( `direction_name` );
-- -------------------------------------------------------------


-- CREATE INDEX "index_plan_name" ------------------------------
CREATE INDEX `index_plan_name` USING BTREE ON `dorm_question_plan`( `plan_name` );
-- -------------------------------------------------------------


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
-- ---------------------------------------------------------


