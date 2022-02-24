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

 Date: 24/02/2022 09:00:09
*/


-- ----------------------------
-- Table structure for USERS
-- ----------------------------
DROP TABLE "LIBRARY"."USERS";
CREATE TABLE "LIBRARY"."USERS" (
  "USERNAME" VARCHAR2(255 BYTE) VISIBLE NOT NULL,
  "PASSWORD" VARCHAR2(255 BYTE) VISIBLE,
  "PEOPLEID" VARCHAR2(20 BYTE) VISIBLE,
  "EXPIREDATE" DATE VISIBLE,
  "ISACTIVE" VARCHAR2(255 BYTE) VISIBLE
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
-- Records of USERS
-- ----------------------------
INSERT INTO "LIBRARY"."USERS" VALUES ('heman', 'heman', '10', TO_DATE('2022-02-23 23:00:39', 'SYYYY-MM-DD HH24:MI:SS'), 'n');
INSERT INTO "LIBRARY"."USERS" VALUES ('anik', 'anik', '11', TO_DATE('2022-04-24 23:02:40', 'SYYYY-MM-DD HH24:MI:SS'), 'y');
INSERT INTO "LIBRARY"."USERS" VALUES ('fineman', 'fine', '4', TO_DATE('2023-04-18 22:57:49', 'SYYYY-MM-DD HH24:MI:SS'), 'y');
INSERT INTO "LIBRARY"."USERS" VALUES ('inactiveman', 'inactive', '5', TO_DATE('2022-02-22 23:34:59', 'SYYYY-MM-DD HH24:MI:SS'), 'n');
INSERT INTO "LIBRARY"."USERS" VALUES ('activeman', 'active', '6', TO_DATE('2022-02-22 23:36:14', 'SYYYY-MM-DD HH24:MI:SS'), 'y');
INSERT INTO "LIBRARY"."USERS" VALUES ('superman', 'superman', '7', TO_DATE('2023-02-17 23:51:14', 'SYYYY-MM-DD HH24:MI:SS'), 'y');
INSERT INTO "LIBRARY"."USERS" VALUES ('spiderman', 'spiderman', '9', TO_DATE('2022-04-24 22:49:11', 'SYYYY-MM-DD HH24:MI:SS'), 'y');
INSERT INTO "LIBRARY"."USERS" VALUES ('legend', 'legend', '12', TO_DATE('2022-04-24 23:22:31', 'SYYYY-MM-DD HH24:MI:SS'), 'y');
INSERT INTO "LIBRARY"."USERS" VALUES ('nowman', 'nowman', '8', TO_DATE('2022-08-22 12:37:50', 'SYYYY-MM-DD HH24:MI:SS'), 'y');

-- ----------------------------
-- Primary Key structure for table USERS
-- ----------------------------
ALTER TABLE "LIBRARY"."USERS" ADD CONSTRAINT "SYS_C008050" PRIMARY KEY ("USERNAME");

-- ----------------------------
-- Checks structure for table USERS
-- ----------------------------
ALTER TABLE "LIBRARY"."USERS" ADD CONSTRAINT "SYS_C008049" CHECK ("USERNAME" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
