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

 Date: 24/02/2022 08:53:26
*/


-- ----------------------------
-- Table structure for ADMIN
-- ----------------------------
DROP TABLE "LIBRARY"."ADMIN";
CREATE TABLE "LIBRARY"."ADMIN" (
  "LOGINID" VARCHAR2(255 BYTE) VISIBLE NOT NULL,
  "PASSWORD" VARCHAR2(255 BYTE) VISIBLE NOT NULL,
  "PEOPLEID" VARCHAR2(255 BYTE) VISIBLE
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
-- Records of ADMIN
-- ----------------------------
INSERT INTO "LIBRARY"."ADMIN" VALUES ('admin2', 'admin2', '2');
INSERT INTO "LIBRARY"."ADMIN" VALUES ('admin3', 'admin3', '3');
INSERT INTO "LIBRARY"."ADMIN" VALUES ('admin', 'admin', '1');

-- ----------------------------
-- Primary Key structure for table ADMIN
-- ----------------------------
ALTER TABLE "LIBRARY"."ADMIN" ADD CONSTRAINT "SYS_C008065" PRIMARY KEY ("LOGINID");

-- ----------------------------
-- Checks structure for table ADMIN
-- ----------------------------
ALTER TABLE "LIBRARY"."ADMIN" ADD CONSTRAINT "SYS_C008064" CHECK ("LOGINID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "LIBRARY"."ADMIN" ADD CONSTRAINT "SYS_C008196" CHECK ("PASSWORD" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
