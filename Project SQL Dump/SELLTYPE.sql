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

 Date: 24/02/2022 08:59:44
*/


-- ----------------------------
-- Table structure for SELLTYPE
-- ----------------------------
DROP TABLE "LIBRARY"."SELLTYPE";
CREATE TABLE "LIBRARY"."SELLTYPE" (
  "BOOK_ID" VARCHAR2(255 BYTE) VISIBLE NOT NULL,
  "COUNT" NUMBER VISIBLE
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
-- Records of SELLTYPE
-- ----------------------------
INSERT INTO "LIBRARY"."SELLTYPE" VALUES ('6', '4');
INSERT INTO "LIBRARY"."SELLTYPE" VALUES ('1', '498');
INSERT INTO "LIBRARY"."SELLTYPE" VALUES ('9', '50');
INSERT INTO "LIBRARY"."SELLTYPE" VALUES ('14', '50');
INSERT INTO "LIBRARY"."SELLTYPE" VALUES ('5', '99');
INSERT INTO "LIBRARY"."SELLTYPE" VALUES ('11', '40');
INSERT INTO "LIBRARY"."SELLTYPE" VALUES ('2', '96');
INSERT INTO "LIBRARY"."SELLTYPE" VALUES ('3', '100');
INSERT INTO "LIBRARY"."SELLTYPE" VALUES ('4', '96');
INSERT INTO "LIBRARY"."SELLTYPE" VALUES ('13', '100');

-- ----------------------------
-- Primary Key structure for table SELLTYPE
-- ----------------------------
ALTER TABLE "LIBRARY"."SELLTYPE" ADD CONSTRAINT "SYS_C008061" PRIMARY KEY ("BOOK_ID");

-- ----------------------------
-- Checks structure for table SELLTYPE
-- ----------------------------
ALTER TABLE "LIBRARY"."SELLTYPE" ADD CONSTRAINT "SYS_C008060" CHECK ("BOOK_ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
