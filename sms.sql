/*
 Navicat MySQL Data Transfer

 Source Server         : mine
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : sms

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 17/08/2022 16:32:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `classNO` bigint(20) NOT NULL AUTO_INCREMENT,
  `facultyNO` bigint(20) NULL DEFAULT NULL,
  `classSize` int(11) NULL DEFAULT NULL,
  `year` date NULL DEFAULT NULL,
  `counsellorNO` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`classNO`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 756 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES (2, 14, 56, '2021-09-01', 18);
INSERT INTO `class` VALUES (322, 3, 34545, '2021-12-04', 17);
INSERT INTO `class` VALUES (755, 4, 63, '2021-04-01', 24);

-- ----------------------------
-- Table structure for classroom
-- ----------------------------
DROP TABLE IF EXISTS `classroom`;
CREATE TABLE `classroom`  (
  `classRoomNO` bigint(20) NOT NULL AUTO_INCREMENT,
  `capacity` int(11) NULL DEFAULT NULL,
  `isMultimedia` int(11) NULL DEFAULT NULL,
  `state` int(11) NULL DEFAULT NULL,
  `locationNo` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`classRoomNO`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1011 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of classroom
-- ----------------------------
INSERT INTO `classroom` VALUES (101, 60, 1, 0, 1);
INSERT INTO `classroom` VALUES (102, 40, 0, 1, 33);
INSERT INTO `classroom` VALUES (103, 60, 1, 0, 1);
INSERT INTO `classroom` VALUES (104, 60, 1, 0, 1);
INSERT INTO `classroom` VALUES (105, 120, 1, 0, 1);
INSERT INTO `classroom` VALUES (201, 60, 1, 0, 1);
INSERT INTO `classroom` VALUES (202, 60, 1, 0, 1);
INSERT INTO `classroom` VALUES (203, 60, 1, 0, 1);
INSERT INTO `classroom` VALUES (204, 60, 1, 0, 1);
INSERT INTO `classroom` VALUES (301, 60, 1, 0, 1);
INSERT INTO `classroom` VALUES (302, 60, 1, 0, 1);
INSERT INTO `classroom` VALUES (303, 60, 1, 0, 1);
INSERT INTO `classroom` VALUES (1010, 66, 1, 1, 32);

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `courseID` bigint(20) NOT NULL AUTO_INCREMENT,
  `courseName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hour` int(11) NULL DEFAULT NULL,
  `credit` double NULL DEFAULT NULL,
  PRIMARY KEY (`courseID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, '???????????????????????????Java???', 64, 3.5);
INSERT INTO `course` VALUES (2, 'JavaScript????????????', 48, 2.5);
INSERT INTO `course` VALUES (3, 'JSP????????????', 56, 3);
INSERT INTO `course` VALUES (4, 'VC++????????????', 56, 3.5);
INSERT INTO `course` VALUES (5, 'Python????????????', 40, 2);
INSERT INTO `course` VALUES (6, '???????????????', 48, 3.5);
INSERT INTO `course` VALUES (7, 'IOS????????????', 36, 2);
INSERT INTO `course` VALUES (8, '????????????????????????', 32, 2);
INSERT INTO `course` VALUES (9, '????????????????????????', 60, 3.5);
INSERT INTO `course` VALUES (10, '??????????????????', 40, 2);
INSERT INTO `course` VALUES (11, '?????????????????????????????????	', 40, 3);
INSERT INTO `course` VALUES (12, '????????????????????????????????????????????????????????????	', 40, 4);
INSERT INTO `course` VALUES (13, '???????????????	', 40, 1);
INSERT INTO `course` VALUES (14, '?????????????????????	', 40, 3.5);
INSERT INTO `course` VALUES (15, '?????????????????????	', 40, 3.5);
INSERT INTO `course` VALUES (16, '????????????????????????????????????	', 20, 2);
INSERT INTO `course` VALUES (17, '?????????	', 48, 2);
INSERT INTO `course` VALUES (18, '?????????	', 48, 2);
INSERT INTO `course` VALUES (19, '????????????(???)	', 48, 3.5);
INSERT INTO `course` VALUES (20, '?????????????????????	', 60, 5);
INSERT INTO `course` VALUES (21, '???????????????Linux???	', 60, 3);
INSERT INTO `course` VALUES (22, 'css+div?????????????????????	', 54, 3);
INSERT INTO `course` VALUES (23, 'css+div?????????????????????	', 54, 3);
INSERT INTO `course` VALUES (24, 'PHP????????????	', 54, 3);
INSERT INTO `course` VALUES (25, '?????????????????????????????????????????????	', 54, 3);
INSERT INTO `course` VALUES (26, '???????????????????????????	', 54, 3.5);
INSERT INTO `course` VALUES (27, '??????????????????	', 54, 3);
INSERT INTO `course` VALUES (28, '??????????????????????????????	', 30, 4);
INSERT INTO `course` VALUES (29, '?????????????????????MySQL	', 54, 3);
INSERT INTO `course` VALUES (30, 'Javascript????????????????????????	', 54, 3);
INSERT INTO `course` VALUES (31, '??????????????????	', 40, 3);
INSERT INTO `course` VALUES (32, 'Bootstrap UI????????????	', 40, 3);
INSERT INTO `course` VALUES (33, 'VueJS????????????	', 54, 4);
INSERT INTO `course` VALUES (34, '????????????	', 40, 3);
INSERT INTO `course` VALUES (35, 'PHP???????????????????????????????????????	', 40, 2);
INSERT INTO `course` VALUES (36, 'HTML 5	', 40, 2);
INSERT INTO `course` VALUES (37, '??????????????????	', 40, 3);
INSERT INTO `course` VALUES (38, '????????????????????????	', 40, 3);
INSERT INTO `course` VALUES (39, '????????????	', 60, 20);
INSERT INTO `course` VALUES (40, 'Web??????????????????	', 40, 3);
INSERT INTO `course` VALUES (41, 'Windows??????????????????????????????	', 40, 3);
INSERT INTO `course` VALUES (55, '????????????', 50, 5);

-- ----------------------------
-- Table structure for curriculum
-- ----------------------------
DROP TABLE IF EXISTS `curriculum`;
CREATE TABLE `curriculum`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `courseNO` bigint(20) NULL DEFAULT NULL,
  `classNO` bigint(20) NULL DEFAULT NULL,
  `teacherID` bigint(20) NULL DEFAULT NULL,
  `schoolYear` int(11) NULL DEFAULT NULL,
  `term` int(11) NULL DEFAULT NULL,
  `startWeek` int(11) NULL DEFAULT NULL,
  `endWeek` int(11) NULL DEFAULT NULL,
  `weekday` int(11) NULL DEFAULT NULL,
  `section` int(11) NULL DEFAULT NULL,
  `locationNo` bigint(20) NULL DEFAULT NULL,
  `classRoomNO` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20210104 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of curriculum
-- ----------------------------
INSERT INTO `curriculum` VALUES (1, 1, 1, 13, 2021, 0, 1, 18, 1, 3, 1, 101);
INSERT INTO `curriculum` VALUES (2, 2, 1, 14, 2021, 0, 1, 18, 2, 5, 2, 202);
INSERT INTO `curriculum` VALUES (3, 3, 2, 15, 2021, 1, 1, 18, 4, 7, 1, 101);
INSERT INTO `curriculum` VALUES (4, 4, 1, 16, 2021, 0, 1, 18, 5, 6, 1, 301);
INSERT INTO `curriculum` VALUES (5, 5, 2, 17, 2021, 0, 1, 18, 2, 1, 1, 204);
INSERT INTO `curriculum` VALUES (11, 2, 1, 14, 2021, 0, 1, 18, 2, 6, 2, 202);
INSERT INTO `curriculum` VALUES (12, 3, 2, 15, 2021, 1, 1, 18, 4, 8, 1, 101);
INSERT INTO `curriculum` VALUES (13, 4, 1, 16, 2021, 0, 1, 18, 5, 5, 1, 301);
INSERT INTO `curriculum` VALUES (14, 5, 2, 17, 2021, 0, 1, 18, 2, 2, 1, 204);
INSERT INTO `curriculum` VALUES (15, 1, 1, 13, 2021, 0, 1, 18, 1, 4, 1, 101);
INSERT INTO `curriculum` VALUES (16, 2, 1, 14, 2018, 0, 1, 18, 2, 6, 2, 202);
INSERT INTO `curriculum` VALUES (17, 3, 2, 15, 2019, 0, 1, 18, 4, 8, 1, 101);
INSERT INTO `curriculum` VALUES (18, 4, 1, 16, 2033, 0, 1, 18, 5, 5, 1, 301);
INSERT INTO `curriculum` VALUES (19, 5, 2, 17, 2021, 0, 1, 18, 2, 2, 1, 204);
INSERT INTO `curriculum` VALUES (20, 1, 1, 13, 2020, 0, 1, 18, 1, 4, 1, 101);
INSERT INTO `curriculum` VALUES (100, 3, 1, 13, 2021, 0, 1, 18, 3, 5, 1, 101);
INSERT INTO `curriculum` VALUES (101, 3, 1, 13, 2021, 0, 1, 18, 3, 6, 1, 101);
INSERT INTO `curriculum` VALUES (102, 30, 1, 21, 2021, 0, 1, 20, 4, 1, 7, 203);
INSERT INTO `curriculum` VALUES (111, 7, 1, 17, 2021, 1, 5, 20, 1, 1, 2, 101);
INSERT INTO `curriculum` VALUES (112, 7, 1, 17, 2021, 1, 5, 20, 1, 2, 2, 101);
INSERT INTO `curriculum` VALUES (200, 6, 322, 18, 2021, 0, 1, 18, 1, 7, 1, 201);
INSERT INTO `curriculum` VALUES (5060, 40, 1, 12, 2021, 0, 5, 18, 6, 5, 22, 301);
INSERT INTO `curriculum` VALUES (20210103, 10, 1, 18, 2021, 0, 5, 18, 5, 3, 1, 302);

-- ----------------------------
-- Table structure for faculty
-- ----------------------------
DROP TABLE IF EXISTS `faculty`;
CREATE TABLE `faculty`  (
  `facultyNO` bigint(20) NOT NULL AUTO_INCREMENT,
  `facultyName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`facultyNO`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of faculty
-- ----------------------------
INSERT INTO `faculty` VALUES (1, '???????????????');
INSERT INTO `faculty` VALUES (2, '?????????????????????');
INSERT INTO `faculty` VALUES (3, '?????????????????????');
INSERT INTO `faculty` VALUES (4, '?????????????????????');
INSERT INTO `faculty` VALUES (5, '?????????????????????');
INSERT INTO `faculty` VALUES (6, '????????????');
INSERT INTO `faculty` VALUES (7, '?????????????????????');
INSERT INTO `faculty` VALUES (8, '??????????????????');
INSERT INTO `faculty` VALUES (9, '?????????????????????');
INSERT INTO `faculty` VALUES (10, '?????????????????????????????????');
INSERT INTO `faculty` VALUES (11, '????????????????????????');
INSERT INTO `faculty` VALUES (12, '?????????');
INSERT INTO `faculty` VALUES (13, '??????????????????');
INSERT INTO `faculty` VALUES (14, '??????????????????????????????');
INSERT INTO `faculty` VALUES (15, '??????????????????');
INSERT INTO `faculty` VALUES (16, '????????????');
INSERT INTO `faculty` VALUES (100, '?????????');

-- ----------------------------
-- Table structure for gaoli_test
-- ----------------------------
DROP TABLE IF EXISTS `gaoli_test`;
CREATE TABLE `gaoli_test`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` int(1) NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  `tel` int(13) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `test_index`(`id`, `tel`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gaoli_test
-- ----------------------------
INSERT INTO `gaoli_test` VALUES (5, 'xx', 2, '2022-08-01', 1234567);
INSERT INTO `gaoli_test` VALUES (6, '122', 1, '2022-08-25', 2);
INSERT INTO `gaoli_test` VALUES (7, 'g', 1, '2022-08-11', 1);
INSERT INTO `gaoli_test` VALUES (8, 'g', 2, '2022-08-01', 233);

-- ----------------------------
-- Table structure for location
-- ----------------------------
DROP TABLE IF EXISTS `location`;
CREATE TABLE `location`  (
  `locationNo` bigint(20) NOT NULL AUTO_INCREMENT,
  `locationName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`locationNo`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of location
-- ----------------------------
INSERT INTO `location` VALUES (1, '???1???');
INSERT INTO `location` VALUES (2, '???2???');
INSERT INTO `location` VALUES (4, '???4???');
INSERT INTO `location` VALUES (6, '?????????');
INSERT INTO `location` VALUES (7, '?????????');
INSERT INTO `location` VALUES (8, '?????????');
INSERT INTO `location` VALUES (22, '?????????');
INSERT INTO `location` VALUES (32, '?????????');
INSERT INTO `location` VALUES (33, '?????????');
INSERT INTO `location` VALUES (34, '?????????');
INSERT INTO `location` VALUES (55, '????????????');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `studentID` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `birthday` date NULL DEFAULT NULL,
  `facultyNO` bigint(20) NULL DEFAULT NULL,
  `classNO` bigint(20) NULL DEFAULT NULL,
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`studentID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1913013 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (191301, '??????1', '2010-12-31', 14, 1, '??????????????????', '1343888888', '??????????????????????????????4334', 0);
INSERT INTO `student` VALUES (191302, '??????    ', '1996-02-01', 14, 2, '??????????????????', '13437777777', '??????', 1);
INSERT INTO `student` VALUES (191303, '??????    ', '1994-10-06', 14, 2, '??????????????????', '13437777777', '?????????', 0);
INSERT INTO `student` VALUES (191304, '?????????  ', '1995-08-26', 5, 1, '??????????????????', '13437777777', 'C++???????????????????????????', 1);
INSERT INTO `student` VALUES (191306, '?????????  ', '1995-11-20', 14, 2, '??????????????????', '13437777777', 'C++???????????????????????????', 1);
INSERT INTO `student` VALUES (191307, '??????    ', '1995-05-01', 14, 1, '??????????????????', '13437777777', '?????????', 1);
INSERT INTO `student` VALUES (191308, '?????????  ', '1994-08-05', 14, 2, '??????????????????', '13437777777', '?????????????????????', 1);
INSERT INTO `student` VALUES (191309, '?????????  ', '1994-08-11', 14, 1, '??????????????????', '13437777777', '?????????????????????', 1);
INSERT INTO `student` VALUES (191310, '??????    ', '1996-07-22', 14, 2, '??????????????????', '13437777777', NULL, 0);
INSERT INTO `student` VALUES (191311, '??????    ', '1995-03-18', 14, 1, '??????????????????', '13437777777', NULL, 0);
INSERT INTO `student` VALUES (191313, '??????    ', '1994-08-11', 14, 2, '??????????????????', '13437777777', NULL, 0);
INSERT INTO `student` VALUES (191314, '?????????  ', '1995-01-30', 14, 2, '??????????????????', '13437777777', NULL, 0);
INSERT INTO `student` VALUES (221301, '??????    ', '1994-06-10', 14, 1, '??????????????????', '13437777777', NULL, 1);
INSERT INTO `student` VALUES (221302, '??????    ', '1994-01-29', 14, 2, '??????????????????', '13437777777', NULL, 1);
INSERT INTO `student` VALUES (221303, '?????????  ', '1995-03-26', 14, 1, '??????????????????', '13437777777', NULL, 1);
INSERT INTO `student` VALUES (221304, '?????????  ', '1995-02-10', 14, 1, '??????????????????', '13437777777', NULL, 0);
INSERT INTO `student` VALUES (221306, '??????    ', '1995-09-20', 14, 2, '??????????????????', '13437777777', NULL, 1);
INSERT INTO `student` VALUES (221310, '?????????  ', '1994-05-01', 14, 2, '??????????????????', '13437777777', NULL, 1);
INSERT INTO `student` VALUES (221316, '?????????  ', '1994-03-19', 14, 1, '??????????????????', '13437777777', NULL, 1);
INSERT INTO `student` VALUES (221318, '??????    ', '1995-10-09', 14, 1, '??????????????????', '13437777777', NULL, 1);
INSERT INTO `student` VALUES (221320, '??????32???  ', '1995-03-22', 6, 1, '??????????????????32', '13437773223', '3232', 0);
INSERT INTO `student` VALUES (221321, '?????????  ', '1994-11-12', 14, 1, '??????????????????', '13437777777', NULL, 0);
INSERT INTO `student` VALUES (221341, '?????????  ', '1995-01-30', 14, 1, '??????????????????', '13437777777', NULL, 0);
INSERT INTO `student` VALUES (221343, '??????1', '2021-12-01', 4, 2, '??????', '1343444444', '3232fgregre', 0);
INSERT INTO `student` VALUES (1913012, '?????????', '2021-12-03', 100, 1, '????????????????????????', '1343333333', '??????????????????????????????', 0);

-- ----------------------------
-- Table structure for sys_sql
-- ----------------------------
DROP TABLE IF EXISTS `sys_sql`;
CREATE TABLE `sys_sql`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sql` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_sql
-- ----------------------------
INSERT INTO `sys_sql` VALUES (1, ': DELETE FROM gaoli_test WHERE id=4', 4);
INSERT INTO `sys_sql` VALUES (2, ' DELETE FROM gaoli_test WHERE id=4', 4);
INSERT INTO `sys_sql` VALUES (3, ' DELETE FROM gaoli_test WHERE id=5', 4);
INSERT INTO `sys_sql` VALUES (4, ' DELETE FROM gaoli_test WHERE id=5', 4);
INSERT INTO `sys_sql` VALUES (5, ' DELETE FROM gaoli_test WHERE id=5', 4);
INSERT INTO `sys_sql` VALUES (6, ' DELETE FROM gaoli_test WHERE id=5', 4);
INSERT INTO `sys_sql` VALUES (7, ' INSERT INTO gaoli_test  ( id,\nname,\nsex,\nbirthday,\ntel )  VALUES  ( 0,\n\'??????\',\n2,\n\'3918-08-01\',\n0 )', 1);
INSERT INTO `sys_sql` VALUES (8, ' DELETE FROM gaoli_test WHERE id=1', 4);
INSERT INTO `sys_sql` VALUES (9, ' DELETE FROM gaoli_test WHERE id=7', 4);
INSERT INTO `sys_sql` VALUES (10, ' SELECT  id,username,password,type,disabled,contrastStudentID,contrastTeacherID  FROM user \n \n WHERE (username = \'shf\')', 4);
INSERT INTO `sys_sql` VALUES (11, ' DELETE FROM gaoli_test WHERE id=null', 4);
INSERT INTO `sys_sql` VALUES (12, ' delete from gaoli_test where id > 1', 4);
INSERT INTO `sys_sql` VALUES (13, ' delete from gaoli_test where id > 1', 4);
INSERT INTO `sys_sql` VALUES (14, ' delete from gaoli_test where id > 1', 4);
INSERT INTO `sys_sql` VALUES (15, ' delete from gaoli_test where id > 1', 4);
INSERT INTO `sys_sql` VALUES (16, ' delete from gaoli_test where id > 1', 4);
INSERT INTO `sys_sql` VALUES (17, ' delete from gaoli_test where id > 1', 4);
INSERT INTO `sys_sql` VALUES (18, ' delete from gaoli_test where id > 1', 4);
INSERT INTO `sys_sql` VALUES (19, ' delete from gaoli_test where id > 1', 4);
INSERT INTO `sys_sql` VALUES (20, ' delete from gaoli_test where id > 1', 4);
INSERT INTO `sys_sql` VALUES (21, ' select a.classNO , b.`name`, c.capacity, t.table_name, e.courseName from class a, teacher b, classroom c, table_index t, course e', 2);
INSERT INTO `sys_sql` VALUES (22, ' select a.classNO , b.`name`, c.capacity, t.table_name, e.courseName from class a, teacher b, classroom c, table_index t, course e', 2);
INSERT INTO `sys_sql` VALUES (23, ' select a.classNO , b.`name`, c.capacity, t.table_name, e.courseName from class a, teacher b, classroom c, table_index t, course e', 2);
INSERT INTO `sys_sql` VALUES (24, ' select a.classNO , b.`name`, c.capacity, t.table_name, e.courseName from class a, teacher b, classroom c, table_index t, course e', 2);
INSERT INTO `sys_sql` VALUES (25, ' select a.classNO , b.`name`, c.capacity, t.table_name, e.courseName from class a, teacher b, classroom c, table_index t, course e', 2);
INSERT INTO `sys_sql` VALUES (26, ' select a.classNO , b.`name`, c.capacity, t.table_name, e.courseName from class a, teacher b, classroom c, table_index t, course e', 2);
INSERT INTO `sys_sql` VALUES (27, ' select a.classNO , b.`name`, c.capacity, t.table_name, e.courseName from class a, teacher b, classroom c, table_index t, course e', 2);
INSERT INTO `sys_sql` VALUES (28, ' SELECT  id,username,password,type,disabled,contrastStudentID,contrastTeacherID  FROM user \n \n WHERE (username = \'shf\')', 2);
INSERT INTO `sys_sql` VALUES (29, ' SELECT  id,username,password,type,disabled,contrastStudentID,contrastTeacherID  FROM user \n \n WHERE (username = \'shf\')', 2);
INSERT INTO `sys_sql` VALUES (30, ' select * from gaoli_test', 2);
INSERT INTO `sys_sql` VALUES (31, ' select * from gaoli_test', 2);
INSERT INTO `sys_sql` VALUES (32, ' SELECT id,name,sex,birthday,tel FROM gaoli_test WHERE id=2 ', 2);
INSERT INTO `sys_sql` VALUES (33, ' select name, sex from gaoli_test where id = 7 order by name,sex', 2);
INSERT INTO `sys_sql` VALUES (34, ' select name, sex from gaoli_test where id = 7 order by name', 2);
INSERT INTO `sys_sql` VALUES (35, ' select * from gaoli_test', 2);
INSERT INTO `sys_sql` VALUES (36, ' SELECT  id,username,password,type,disabled,contrastStudentID,contrastTeacherID  FROM user \n \n WHERE (username = \'shf\')', 2);
INSERT INTO `sys_sql` VALUES (37, ' SELECT  id,username,password,type,disabled,contrastStudentID,contrastTeacherID  FROM user \n \n WHERE (username = \'shf\')', 2);
INSERT INTO `sys_sql` VALUES (38, ' SELECT id,name,sex,birthday,tel FROM gaoli_test WHERE id=2 ', 2);
INSERT INTO `sys_sql` VALUES (39, ' select * from gaoli_test', 2);
INSERT INTO `sys_sql` VALUES (40, ' select name, sex from gaoli_test where id = 7 order by name,sex', 2);
INSERT INTO `sys_sql` VALUES (41, ' DELETE FROM gaoli_test WHERE id=7', 4);
INSERT INTO `sys_sql` VALUES (42, ' delete from gaoli_test where id > 1', 4);
INSERT INTO `sys_sql` VALUES (43, ' select name, sex from gaoli_test where id = 7 order by name', 2);
INSERT INTO `sys_sql` VALUES (44, ' SELECT  id,username,password,type,disabled,contrastStudentID,contrastTeacherID  FROM user \n \n WHERE (username = \'shf\')', 2);
INSERT INTO `sys_sql` VALUES (45, ' SELECT  id,username,password,type,disabled,contrastStudentID,contrastTeacherID  FROM user \n \n WHERE (username = \'shf\')', 2);
INSERT INTO `sys_sql` VALUES (46, ' SELECT id,name,sex,birthday,tel FROM gaoli_test WHERE id=2 ', 2);
INSERT INTO `sys_sql` VALUES (47, ' select * from gaoli_test', 2);
INSERT INTO `sys_sql` VALUES (48, ' select name, sex from gaoli_test where id = 7 order by name,sex', 2);
INSERT INTO `sys_sql` VALUES (49, ' select name, sex from gaoli_test where id = 7 order by name', 2);
INSERT INTO `sys_sql` VALUES (50, ' select a.classNO , b.`name`, c.capacity, t.table_name, e.courseName from class a, teacher b, classroom c, table_index t, course e', 2);
INSERT INTO `sys_sql` VALUES (51, ' SELECT  id,username,password,type,disabled,contrastStudentID,contrastTeacherID  FROM user \n \n WHERE (username = \'shf\')', 2);
INSERT INTO `sys_sql` VALUES (52, ' SELECT  id,username,password,type,disabled,contrastStudentID,contrastTeacherID  FROM user \n \n WHERE (username = \'shf\')', 2);
INSERT INTO `sys_sql` VALUES (53, ' SELECT id,name,sex,birthday,tel FROM gaoli_test WHERE id=2 ', 2);
INSERT INTO `sys_sql` VALUES (54, ' select * from gaoli_test', 2);

-- ----------------------------
-- Table structure for table_index
-- ----------------------------
DROP TABLE IF EXISTS `table_index`;
CREATE TABLE `table_index`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `index_column_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of table_index
-- ----------------------------
INSERT INTO `table_index` VALUES (1, 'gaoli_test', 'id');
INSERT INTO `table_index` VALUES (2, 'gaoli_test', 'name,sex');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `teacherID` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `facultyNO` bigint(20) NULL DEFAULT NULL,
  `speciality` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`teacherID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (12, '?????????', 100, '??????', '??????');
INSERT INTO `teacher` VALUES (13, '?????????', 14, '?????????java', '??????');
INSERT INTO `teacher` VALUES (14, '????????????42342', 2, '??????', '???');
INSERT INTO `teacher` VALUES (15, '?????????', 14, '?????????', '??????');
INSERT INTO `teacher` VALUES (16, '?????????', 14, '?????????', '??????');
INSERT INTO `teacher` VALUES (17, '?????????', 14, '?????????', '??????');
INSERT INTO `teacher` VALUES (18, '?????????', 14, '?????????', '??????');
INSERT INTO `teacher` VALUES (19, '?????????', 14, '?????????', '??????');
INSERT INTO `teacher` VALUES (20, '?????????432', 3, '?????????32423', '??????3232');
INSERT INTO `teacher` VALUES (21, '?????????', 14, '?????????', '??????');
INSERT INTO `teacher` VALUES (22, '?????????', 14, '?????????', '??????');
INSERT INTO `teacher` VALUES (23, '??????', 14, '?????????', '??????');
INSERT INTO `teacher` VALUES (24, '?????????', 14, '?????????', '??????');
INSERT INTO `teacher` VALUES (25, '??????', 3, '?????????', '??????');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` int(11) NOT NULL,
  `disabled` int(11) NULL DEFAULT 0,
  `contrastStudentID` int(11) NULL DEFAULT NULL,
  `contrastTeacherID` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'tom', 'tom', 0, 0, 191301, NULL);
INSERT INTO `user` VALUES (4, 'shf', 'shf', 2, 0, NULL, NULL);
INSERT INTO `user` VALUES (5, 'zhangsan', 'zhangsan', 2, 0, NULL, NULL);
INSERT INTO `user` VALUES (8, 'abc', 'abc', 0, 0, 191302, NULL);
INSERT INTO `user` VALUES (9, 'lisi', 'lisi', 0, 0, 191303, NULL);
INSERT INTO `user` VALUES (11, 'class', 'class', 1, 0, NULL, 13);
INSERT INTO `user` VALUES (12, 'zhangsan3333333', '123456', 1, 1, NULL, 13);
INSERT INTO `user` VALUES (13, 'superbaby1', 'superbaby1', 0, 0, 191304, NULL);
INSERT INTO `user` VALUES (14, 'superbaby2', 'superbaby2', 0, 0, 191306, NULL);
INSERT INTO `user` VALUES (15, 'zhangsan', 'zhangsan', 1, 0, NULL, 14);
INSERT INTO `user` VALUES (18, 'zhangsan232', 'zhangsan232', 0, 0, 191307, NULL);
INSERT INTO `user` VALUES (19, 'zhangsan', '32', 0, 0, 191308, NULL);
INSERT INTO `user` VALUES (21, 'hahah', 'hahah', 0, 0, 191309, NULL);
INSERT INTO `user` VALUES (24, 'zhangsan324', '12345', 0, 0, 191310, NULL);
INSERT INTO `user` VALUES (25, 'jcbb', 'jcbb', 2, 0, NULL, NULL);
INSERT INTO `user` VALUES (26, 'xuesheng1', 'xs11', 0, 1, 191301, NULL);
INSERT INTO `user` VALUES (27, 'xue1', 'xue1', 0, 0, 191301, NULL);
INSERT INTO `user` VALUES (28, 'laosi1', 'laosi1', 1, 0, NULL, 12);

SET FOREIGN_KEY_CHECKS = 1;
