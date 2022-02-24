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

 Date: 24/02/2022 08:58:13
*/


-- ----------------------------
-- Table structure for PEOPLE
-- ----------------------------
DROP TABLE "LIBRARY"."PEOPLE";
CREATE TABLE "LIBRARY"."PEOPLE" (
  "ID" VARCHAR2(20 BYTE) VISIBLE NOT NULL,
  "NAME" VARCHAR2(255 BYTE) VISIBLE NOT NULL,
  "EMAIL" VARCHAR2(255 BYTE) VISIBLE,
  "ADDRESS" VARCHAR2(255 BYTE) VISIBLE,
  "PHONE" VARCHAR2(255 BYTE) VISIBLE,
  "DATEOFBIRTH" DATE VISIBLE
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
-- Records of PEOPLE
-- ----------------------------
INSERT INTO "LIBRARY"."PEOPLE" VALUES ('2', 'Sohidul', 'sohid007@gmail.com', 'Dhaka, Bangladesh', '0193655416', TO_DATE('1999-09-01 22:23:14', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LIBRARY"."PEOPLE" VALUES ('3', 'Fahim Shahriar', 'FahimBoss@gmail.com', 'MIrpur', '156125', TO_DATE('1997-02-22 22:24:46', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LIBRARY"."PEOPLE" VALUES ('10', 'heman', 'heman', 'Dhaka', '002', TO_DATE('2022-02-03 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LIBRARY"."PEOPLE" VALUES ('11', 'anik', 'anik001224@gmail.com', 'Maniknagar, Dhaka', '0225245581512', TO_DATE('2022-02-03 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LIBRARY"."PEOPLE" VALUES ('4', 'fineman', 'fine@gmail.com', 'Dhaka', '01479566', TO_DATE('2022-02-10 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LIBRARY"."PEOPLE" VALUES ('5', 'inactiveman', 'inactive@gmail.com', 'Dhaka', '015622623652', TO_DATE('2021-09-02 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LIBRARY"."PEOPLE" VALUES ('6', 'activeman', 'activeman@gmail.com', 'U.S.A.', '14465659632', TO_DATE('2021-10-19 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LIBRARY"."PEOPLE" VALUES ('7', 'superman', 'superman@gmail.com', 'New York', '911', TO_DATE('2022-02-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LIBRARY"."PEOPLE" VALUES ('9', 'spiderman', 'anik001224@gmail.', 'Maniknagar, Dhaka', '01254', TO_DATE('2022-02-02 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LIBRARY"."PEOPLE" VALUES ('12', 'legend', 'legend0122@gmail.com', 'Manik Nagar, Dhaka', '12346987843512', TO_DATE('2010-02-04 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LIBRARY"."PEOPLE" VALUES ('8', 'nowman', 'nowman', 'Dhaka', '00444', TO_DATE('2022-02-02 00:00:00', 'SYYYY-MM-DD HH24:MI:SS'));
INSERT INTO "LIBRARY"."PEOPLE" VALUES ('1', 'Shahrukh', 'shahrukh007@gmail.com', 'Dhaka', '123456', TO_DATE('2021-11-09 23:47:47', 'SYYYY-MM-DD HH24:MI:SS'));

-- ----------------------------
-- Primary Key structure for table PEOPLE
-- ----------------------------
ALTER TABLE "LIBRARY"."PEOPLE" ADD CONSTRAINT "SYS_C008201" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table PEOPLE
-- ----------------------------
ALTER TABLE "LIBRARY"."PEOPLE" ADD CONSTRAINT "SYS_C008046" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "LIBRARY"."PEOPLE" ADD CONSTRAINT "SYS_C008047" CHECK ("NAME" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
