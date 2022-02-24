/*
 Navicat Premium Data Transfer

 Source Server         : Library
 Source Server Type    : Oracle
 Source Server Version : 190000
 Source Host           : localhost:1521
 Source Schema         : LIBRARY

 Target Server Type    : Oracle
 Target Server Version : 190000
 File Encoding         : 65001

 Date: 24/02/2022 08:59:20
*/


-- ----------------------------
-- Table structure for REVIEW
-- ----------------------------
DROP TABLE "LIBRARY"."REVIEW";
CREATE TABLE "LIBRARY"."REVIEW" (
  "BOOK_ID" VARCHAR2(255 BYTE) VISIBLE NOT NULL,
  "USERNAME" VARCHAR2(255 BYTE) VISIBLE NOT NULL,
  "STAR" NUMBER VISIBLE,
  "COMMONT" VARCHAR2(255 BYTE) VISIBLE
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;

-- ----------------------------
-- Records of REVIEW
-- ----------------------------
INSERT INTO "LIBRARY"."REVIEW" VALUES ('13', 'anik', '5', 'Nice. ');
INSERT INTO "LIBRARY"."REVIEW" VALUES ('10', 'nowman', '5', 'Nice.');
INSERT INTO "LIBRARY"."REVIEW" VALUES ('13', 'spiderman', '5', 'Nice ashare golpo. ');
INSERT INTO "LIBRARY"."REVIEW" VALUES ('2', 'legend', '4', 'Very good. ');
INSERT INTO "LIBRARY"."REVIEW" VALUES ('1', 'fineman', '5', 'Inpopular book. But deserves 5 star.');
INSERT INTO "LIBRARY"."REVIEW" VALUES ('9', 'fineman', '5', 'Great novel describing nature. ');
INSERT INTO "LIBRARY"."REVIEW" VALUES ('2', 'fineman', '1', 'Hard book. Waste of time.');
INSERT INTO "LIBRARY"."REVIEW" VALUES ('2', 'superman', '5', 'Saved my ugrad life. ');
INSERT INTO "LIBRARY"."REVIEW" VALUES ('4', 'superman', '1', 'Price is so high.');
INSERT INTO "LIBRARY"."REVIEW" VALUES ('1', 'superman', '4', 'Avg database book.');
INSERT INTO "LIBRARY"."REVIEW" VALUES ('8', 'superman', '1', 'Another high price.');

-- ----------------------------
-- Primary Key structure for table REVIEW
-- ----------------------------
ALTER TABLE "LIBRARY"."REVIEW" ADD CONSTRAINT "SYS_C008115" PRIMARY KEY ("BOOK_ID", "USERNAME");

-- ----------------------------
-- Checks structure for table REVIEW
-- ----------------------------
ALTER TABLE "LIBRARY"."REVIEW" ADD CONSTRAINT "SYS_C008113" CHECK ("BOOK_ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "LIBRARY"."REVIEW" ADD CONSTRAINT "SYS_C008114" CHECK ("USERNAME" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
