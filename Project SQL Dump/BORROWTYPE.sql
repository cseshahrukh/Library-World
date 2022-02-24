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

 Date: 24/02/2022 08:56:44
*/


-- ----------------------------
-- Table structure for BORROWTYPE
-- ----------------------------
DROP TABLE "LIBRARY"."BORROWTYPE";
CREATE TABLE "LIBRARY"."BORROWTYPE" (
  "BOOK_ID" VARCHAR2(255 BYTE) VISIBLE NOT NULL,
  "TOTAL" NUMBER VISIBLE,
  "INLIBRARY" NUMBER VISIBLE
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
-- Records of BORROWTYPE
-- ----------------------------
INSERT INTO "LIBRARY"."BORROWTYPE" VALUES ('7', '1', '1');
INSERT INTO "LIBRARY"."BORROWTYPE" VALUES ('6', '100', '100');
INSERT INTO "LIBRARY"."BORROWTYPE" VALUES ('12', '10', '9');
INSERT INTO "LIBRARY"."BORROWTYPE" VALUES ('1', '500', '498');
INSERT INTO "LIBRARY"."BORROWTYPE" VALUES ('8', '1', '1');
INSERT INTO "LIBRARY"."BORROWTYPE" VALUES ('9', '10', '10');
INSERT INTO "LIBRARY"."BORROWTYPE" VALUES ('14', '10', '10');
INSERT INTO "LIBRARY"."BORROWTYPE" VALUES ('2', '10', '10');
INSERT INTO "LIBRARY"."BORROWTYPE" VALUES ('3', '2', '2');
INSERT INTO "LIBRARY"."BORROWTYPE" VALUES ('4', '420', '420');
INSERT INTO "LIBRARY"."BORROWTYPE" VALUES ('5', '12', '11');
INSERT INTO "LIBRARY"."BORROWTYPE" VALUES ('10', '30', '30');
INSERT INTO "LIBRARY"."BORROWTYPE" VALUES ('13', '20', '20');

-- ----------------------------
-- Primary Key structure for table BORROWTYPE
-- ----------------------------
ALTER TABLE "LIBRARY"."BORROWTYPE" ADD CONSTRAINT "SYS_C008063" PRIMARY KEY ("BOOK_ID");

-- ----------------------------
-- Checks structure for table BORROWTYPE
-- ----------------------------
ALTER TABLE "LIBRARY"."BORROWTYPE" ADD CONSTRAINT "SYS_C008062" CHECK ("BOOK_ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
