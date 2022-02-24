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

 Date: 24/02/2022 08:55:09
*/


-- ----------------------------
-- Table structure for BOOKS
-- ----------------------------
DROP TABLE "LIBRARY"."BOOKS";
CREATE TABLE "LIBRARY"."BOOKS" (
  "BOOK_ID" VARCHAR2(255 BYTE) VISIBLE NOT NULL,
  "NAME" VARCHAR2(255 BYTE) VISIBLE,
  "PUBLISHER" VARCHAR2(255 BYTE) VISIBLE,
  "PRICE" NUMBER VISIBLE,
  "PAGES" NUMBER VISIBLE,
  "LANGUAGE" VARCHAR2(255 BYTE) VISIBLE,
  "SHELFNO" VARCHAR2(255 BYTE) VISIBLE,
  "ROOMNO" VARCHAR2(255 BYTE) VISIBLE
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
-- Records of BOOKS
-- ----------------------------
INSERT INTO "LIBRARY"."BOOKS" VALUES ('7', 'Boss', 'Goodluck', '420', '420', 'bangla', '2', '3');
INSERT INTO "LIBRARY"."BOOKS" VALUES ('3', 'a', 'a', '11', '1', 'Bangla', '2', '1');
INSERT INTO "LIBRARY"."BOOKS" VALUES ('12', 'Himu Mama', 'Gyan', '300', '200', 'bangla', '1', '1');
INSERT INTO "LIBRARY"."BOOKS" VALUES ('4', 'Algorithms I', 'Stanford', '5000', '1200', 'English', '1', '2');
INSERT INTO "LIBRARY"."BOOKS" VALUES ('8', 'OneOne', 'Oxford', '5000', '420', 'bangla', '3', '1');
INSERT INTO "LIBRARY"."BOOKS" VALUES ('9', 'Pather Panchali', 'Ononna', '180', '90', 'bangla', '1', '1');
INSERT INTO "LIBRARY"."BOOKS" VALUES ('14', 'Ghonada Somogro 1', 'Ananda Publisher', '700', '500', 'bangla', '2', '1');
INSERT INTO "LIBRARY"."BOOKS" VALUES ('5', 'Himu Somogro 1', 'Ononna', '750', '900', 'Bangla', '2', '1');
INSERT INTO "LIBRARY"."BOOKS" VALUES ('6', 'Himu Somogro 2', 'Ononna', '600', '800', 'Bangla', '3', '3');
INSERT INTO "LIBRARY"."BOOKS" VALUES ('10', 'Feluda 1', 'Ananda Publisher', '600', '720', 'bangla', '2', '1');
INSERT INTO "LIBRARY"."BOOKS" VALUES ('11', 'Feluda 2', 'Ananda Publisher', '700', '400', 'bangla', '2', '1');
INSERT INTO "LIBRARY"."BOOKS" VALUES ('1', 'SRK Database', 'SRK Library', '40000', '2000', 'Hibru', '1', '1');
INSERT INTO "LIBRARY"."BOOKS" VALUES ('2', 'Introduction to Algorithm', 'Marks Library', '1200', '600', 'English', '2', '1');
INSERT INTO "LIBRARY"."BOOKS" VALUES ('13', 'Tenida', 'aaa', '500', '23', 'bangla', '2', '1');

-- ----------------------------
-- Primary Key structure for table BOOKS
-- ----------------------------
ALTER TABLE "LIBRARY"."BOOKS" ADD CONSTRAINT "SYS_C008052" PRIMARY KEY ("BOOK_ID");

-- ----------------------------
-- Checks structure for table BOOKS
-- ----------------------------
ALTER TABLE "LIBRARY"."BOOKS" ADD CONSTRAINT "SYS_C008051" CHECK ("BOOK_ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
