
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for jy_clock_config_tab
-- ----------------------------
DROP TABLE IF EXISTS `jy_clock_config_tab`;
CREATE TABLE `jy_clock_config_tab`  (
  `sys_key` varchar(64) NOT NULL COMMENT '配置健',
  `sys_val` varchar(64) NOT NULL COMMENT '配置值',
  PRIMARY KEY (`sys_key`)
) ENGINE = InnoDB  COMMENT = '时钟配置表';

-- ----------------------------
-- Records of jy_clock_config_tab
-- ----------------------------
INSERT INTO `jy_clock_config_tab` VALUES ('models', '1');
INSERT INTO `jy_clock_config_tab` VALUES ('power', '1');
INSERT INTO `jy_clock_config_tab` VALUES ('refresh', '30000');
INSERT INTO `jy_clock_config_tab` VALUES ('satellite', 'ttyS1');
INSERT INTO `jy_clock_config_tab` VALUES ('selfExam', '0');
INSERT INTO `jy_clock_config_tab` VALUES ('showSec', '1');

-- ----------------------------
-- Table structure for jy_clock_device_position_tab
-- ----------------------------
DROP TABLE IF EXISTS `jy_clock_device_position_tab`;
CREATE TABLE `jy_clock_device_position_tab`  (
  `id` varchar(32) NOT NULL,
  `clock_id` varchar(64) NULL DEFAULT NULL COMMENT '时钟ID；逻辑外健',
  `logical_exam_room` varchar(64) NULL DEFAULT NULL COMMENT '逻辑考场；该信息来源为公共数据库，根据幢-楼-房间信息查询逻辑考场和物理考场',
  `physical_exam_room` varchar(64) NULL DEFAULT NULL COMMENT '物理考场；该信息来源为公共数据库；根据幢-楼-房间信息查询逻辑考场和物理考场',
  `clock_position_builings` tinyint(1) NULL DEFAULT 0 COMMENT '时钟位置—幢（未绑定为0）',
  `clock_position_floor` tinyint(1) NULL DEFAULT 0 COMMENT '时钟位置—层（未绑定为0）',
  `clock_position_room` tinyint(1) NULL DEFAULT 0 COMMENT '时钟位置—房间（未绑定为0）',
  `create_user` varchar(64) NULL DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) NULL DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '时钟设备位置表，记录时钟设备位置信息，为时钟信息一个关联副表';



-- ----------------------------
-- Table structure for jy_clock_device_tab
-- ----------------------------
DROP TABLE IF EXISTS `jy_clock_device_tab`;
CREATE TABLE `jy_clock_device_tab`  (
  `id` varchar(32) NOT NULL,
  `clock_ip_addr` varchar(64) NULL DEFAULT NULL COMMENT 'IP地址',
  `clock_mac` varchar(64) NULL DEFAULT NULL COMMENT 'MAC地址，格式无特殊符号，字母大写；例：9C431EC00035',
  `is_show_sec` tinyint(1) NULL DEFAULT 0 COMMENT '是否显示秒钟；0：显示；1：不显示（默认值为0）',
  `clock_status` tinyint(1) NULL DEFAULT NULL COMMENT '时钟状态：0：正常；1离线；2：异常（时间同步正确）；3：异常（时钟自检有误）',
  `clock_on_off` tinyint(1) NULL DEFAULT NULL COMMENT '时钟开关状态：0：开；1：关',
  `self_check_info` varchar(64) NULL DEFAULT NULL COMMENT '当状态为3时，该字段不允许为空，其他情况下均为空',
  `clock_resource` tinyint(1) NULL DEFAULT NULL COMMENT '时钟来源：1：自动；2：手动',
  `clock_manufacturer` tinyint(1) NULL DEFAULT NULL COMMENT '时钟厂商：1：厚盟；2：普中……',
  `create_user` varchar(64) NULL DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) NULL DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '时钟设备表，记录时钟设备信息；时钟信息来源自动和手动添加两部分';


-- ----------------------------
-- Table structure for jy_clock_log_tab
-- ----------------------------
DROP TABLE IF EXISTS `jy_clock_log_tab`;
CREATE TABLE `jy_clock_log_tab`  (
  `id` varchar(32) NOT NULL,
  `operation_moudle` varchar(64) NULL DEFAULT NULL COMMENT '操作模块',
  `operation_type` tinyint(1) NULL DEFAULT NULL COMMENT '操作类型：1：add；2：update；3：del',
  `operation_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `operation_user` varchar(64) NULL DEFAULT NULL COMMENT '操作用户，32位UUID',
  `operation_info` varchar(64) NULL DEFAULT NULL COMMENT '操作信息',
  `create_user` varchar(64) NULL DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) NULL DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '时钟菜单表，记录时钟系统菜单信息；该信息同步至公共数据库';



-- ----------------------------
-- Table structure for jy_clock_menu_tab
-- ----------------------------
DROP TABLE IF EXISTS `jy_clock_menu_tab`;
CREATE TABLE `jy_clock_menu_tab`  (
  `id` varchar(32) NOT NULL,
  `menu_name_zh` varchar(64) NULL DEFAULT NULL COMMENT '菜单名称（中文）',
  `menu_level` tinyint(1) NULL DEFAULT NULL COMMENT '菜单层级',
  `menu_order` tinyint(1) NULL DEFAULT NULL COMMENT '菜单排序',
  `menu_url` varchar(64) NULL DEFAULT NULL COMMENT '菜单指向URL',
  `menu_img` varchar(64) NULL DEFAULT NULL COMMENT '菜单图片地址',
  `p_id` varchar(32) NULL DEFAULT NULL COMMENT '上级ID',
  `is_delete` tinyint(1) NULL DEFAULT NULL COMMENT '逻辑删除字段 1:删除，0:未删除',
  `is_open` tinyint(1) NULL DEFAULT NULL COMMENT '开放状态；是否开放该菜单',
  `create_user` varchar(64) NULL DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) NULL DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '时钟菜单表，记录时钟系统菜单信息；该信息同步至公共数据库';

-- ----------------------------
-- Records of jy_clock_menu_tab
-- ----------------------------
INSERT INTO `jy_clock_menu_tab` VALUES ('1', '网络时钟', 1, 1, NULL, 'systemCheck', NULL, 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO `jy_clock_menu_tab` VALUES ('10', '系统参数', 2, 2, '/systemSetting', NULL, '6', 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO `jy_clock_menu_tab` VALUES ('2', '时钟列表', 2, 1, '/clockList', NULL, '1', 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO `jy_clock_menu_tab` VALUES ('3', '时钟分布', 2, 2, '/clockLayout', NULL, '1', 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO `jy_clock_menu_tab` VALUES ('4', '个人设置', 1, 3, NULL, 'info', NULL, 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO `jy_clock_menu_tab` VALUES ('5', '密码管理', 2, 1, '/changePsw', NULL, '4', 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO `jy_clock_menu_tab` VALUES ('6', '系统设置', 1, 4, NULL, 'system', NULL, 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO `jy_clock_menu_tab` VALUES ('7', '查看日志', 2, 1, '/showLog', NULL, '6', 0, 1, NULL, NULL, NULL, NULL);
INSERT INTO `jy_clock_menu_tab` VALUES ('9', '卫星列表', 2, 3, '/sateList', NULL, '1', 0, 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for jy_clock_sat_tab
-- ----------------------------
DROP TABLE IF EXISTS `jy_clock_sat_tab`;
CREATE TABLE `jy_clock_sat_tab`  (
  `id` varchar(32) NOT NULL,
  `sat_no` int(10) NULL DEFAULT NULL COMMENT '卫星编号',
  `sat_type` int(10) NULL DEFAULT NULL COMMENT '卫星类型：0：BD；1：GP；2：GALILEO（欧盟）；3：GLONASS（俄罗斯）',
  `sat_elevation` int(10) NULL DEFAULT NULL COMMENT '卫星仰角',
  `sat_azimuth` int(10) NULL DEFAULT NULL COMMENT '卫星方位角',
  `sat_snr` int(10) NULL DEFAULT NULL COMMENT '卫星信噪比',
  `create_user` varchar(64) NULL DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) NULL DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '卫星信息表，记录根据串口抓取卫星信息';

-- ----------------------------
-- Table structure for jy_clock_user_tab
-- ----------------------------
DROP TABLE IF EXISTS `jy_clock_user_tab`;
CREATE TABLE `jy_clock_user_tab`  (
  `id` varchar(32) NOT NULL,
  `user_name` varchar(64) NOT NULL COMMENT '用户名称；该名称为显示名称',
  `login_name` varchar(64) NOT NULL COMMENT '登录名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `user_resource` tinyint(1) NOT NULL COMMENT '用户来源；0：预制；1：平台',
  `create_user` varchar(64) NULL DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) NULL DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT = '时钟用户表，该信息来源预制数据和考试管理平台同步，故主要信息保存在平台';

-- ----------------------------
-- Records of jy_clock_user_tab
-- ----------------------------
INSERT INTO `jy_clock_user_tab` VALUES ('1', 'goldsun', 'goldsun', 'e10adc3949ba59abbe56e057f20f883e', 0, '1', '2018-10-31 10:00:02', '1', '2018-10-31 10:00:05');

