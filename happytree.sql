/*
 Navicat Premium Data Transfer

 Source Server         : aliyun
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : 47.94.233.32:3306
 Source Schema         : happytree

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 27/06/2018 16:09:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ORG
-- ----------------------------
DROP TABLE IF EXISTS `ORG`;
CREATE TABLE `ORG`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `p_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级机构id',
  `org_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构名称',
  `create_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for PERMISSION
-- ----------------------------
DROP TABLE IF EXISTS `PERMISSION`;
CREATE TABLE `PERMISSION`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `p_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上级id',
  `p_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `create_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for PUNCH_CLOCK
-- ----------------------------
DROP TABLE IF EXISTS `PUNCH_CLOCK`;
CREATE TABLE `PUNCH_CLOCK`  (
  `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `org_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构id',
  `punch_info` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打卡信息',
  `img_base` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片信息',
  `time_info` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打卡时间',
  `create_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_leave` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否请假',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of PUNCH_CLOCK
-- ----------------------------
INSERT INTO `PUNCH_CLOCK` VALUES ('1', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '0');
INSERT INTO `PUNCH_CLOCK` VALUES ('10', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '0');
INSERT INTO `PUNCH_CLOCK` VALUES ('102', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '1');
INSERT INTO `PUNCH_CLOCK` VALUES ('122', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '1');
INSERT INTO `PUNCH_CLOCK` VALUES ('13', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '0');
INSERT INTO `PUNCH_CLOCK` VALUES ('132', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '1');
INSERT INTO `PUNCH_CLOCK` VALUES ('14', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '0');
INSERT INTO `PUNCH_CLOCK` VALUES ('142', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '1');
INSERT INTO `PUNCH_CLOCK` VALUES ('15', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '0');
INSERT INTO `PUNCH_CLOCK` VALUES ('152', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '1');
INSERT INTO `PUNCH_CLOCK` VALUES ('16', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '0');
INSERT INTO `PUNCH_CLOCK` VALUES ('162', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '1');
INSERT INTO `PUNCH_CLOCK` VALUES ('17', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '0');
INSERT INTO `PUNCH_CLOCK` VALUES ('172', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '1');
INSERT INTO `PUNCH_CLOCK` VALUES ('18', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '0');
INSERT INTO `PUNCH_CLOCK` VALUES ('182', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '1');
INSERT INTO `PUNCH_CLOCK` VALUES ('19', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '0');
INSERT INTO `PUNCH_CLOCK` VALUES ('192', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '1');
INSERT INTO `PUNCH_CLOCK` VALUES ('2', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '0');
INSERT INTO `PUNCH_CLOCK` VALUES ('20180621_o9VYl0WXsheqXEfVnf9o6xiTKaDY', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, 'asdasd', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '0');
INSERT INTO `PUNCH_CLOCK` VALUES ('20180623_o9VYl0WXsheqXEfVnf9o6xiTKaDY', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', '', '就是想请假', '', '2018-06-23 14:22:17', '布朗斯基大王', '2018-06-23 14:22:18', NULL, NULL, '1');
INSERT INTO `PUNCH_CLOCK` VALUES ('20180625_o9VYl0WXsheqXEfVnf9o6xiTKaDY', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', '', '歌词是诗歌的一种，入乐的叫歌，不入乐的叫诗（或词）。入乐的歌在感情抒发、形象塑造上和诗没有任何区别，但在结构上、节奏上要受音乐的制约，在韵律上要照顾演唱的方便，在遣词炼字上要考虑听觉艺术的特点，因为它要入乐歌唱。歌词与诗的分别，主要是诗不一定要入乐（合乐），歌词是要合乐的。合乐成为歌曲。歌词一般是配合曲子旋律一同出现的，歌词是歌曲的本意所在。现代一般是配合音乐，便于哼唱的语句', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180625.jpg', '2018-06-25 21:34:09', '布朗斯基大王', '2018-06-25 21:34:09', NULL, NULL, '0');
INSERT INTO `PUNCH_CLOCK` VALUES ('20180626_o9VYl0WXsheqXEfVnf9o6xiTKaDY', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', '', '我这次就打的长一点吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧吧', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180626.jpg', '2018-06-26 13:58:35', '布朗斯基大王', '2018-06-26 13:58:35', NULL, NULL, '0');
INSERT INTO `PUNCH_CLOCK` VALUES ('22', 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', NULL, '若要使用下拉刷新，请使用页面的滚动，而不是 scroll-view ，这样也能通过点击顶部状态栏回到页面顶部', 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/20180621.jpg', '2018-06-21 20:47:25', '布朗斯基大王', '2018-06-21 20:47:25', NULL, NULL, '1');

-- ----------------------------
-- Table structure for TARGET_INFO
-- ----------------------------
DROP TABLE IF EXISTS `TARGET_INFO`;
CREATE TABLE `TARGET_INFO`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `message` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标说明',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `rate_progress` tinyint(2) NULL DEFAULT NULL COMMENT '目标进度',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '目标状态',
  `create_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for USER
-- ----------------------------
DROP TABLE IF EXISTS `USER`;
CREATE TABLE `USER`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `real_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `user_tel` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户电话',
  `user_sex` tinyint(1) NULL DEFAULT NULL COMMENT '用户性别',
  `login_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登陆名',
  `passwrod` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `user_img` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '用户头像',
  `org_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构代码',
  `open_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'wx openId',
  `create_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of USER
-- ----------------------------
INSERT INTO `USER` VALUES ('o9VYl0WXsheqXEfVnf9o6xiTKaDY', '孙嘉宁', '布朗斯基大王', '18713921233', 1, 'manager', NULL, 'http://192.168.1.89:8080/image/o9VYl0WXsheqXEfVnf9o6xiTKaDY/portrait.jpg', NULL, 'o9VYl0WXsheqXEfVnf9o6xiTKaDY', 'wx', '2018-06-21 20:46:34', 'wx', '2018-06-21 20:46:34');

SET FOREIGN_KEY_CHECKS = 1;
