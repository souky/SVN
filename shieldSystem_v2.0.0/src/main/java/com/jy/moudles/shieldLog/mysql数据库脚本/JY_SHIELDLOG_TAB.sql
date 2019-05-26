
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `SHIELDLOG`
-- ----------------------------
DROP TABLE IF EXISTS `JY_SHIELD_LOG_TAB`;
CREATE TABLE `JY_JY_SHIELD_LOG_TAB` (
 		`id` varchar(32) NOT NULL,
		`operation_moudle` varchar(64)  NOT NULL COMMENT '操作模块',
		`operation_type` tinyint(1)  NOT NULL COMMENT '操作类型：1：add；2：update；3：del',
		`operation_time` datetime  NOT NULL COMMENT '操作时间',
		`operation_user` varchar(64)  NOT NULL COMMENT '操作用户，32位UUID',
		`operation_info` varchar(64)  NOT NULL COMMENT '操作信息',
		`create_user`  varchar(64) NULL COMMENT '创建用户' ,
		`create_date`  datetime NULL  COMMENT '创建时间' ,
		`update_user`  varchar(64) NULL COMMENT '更新用户' ,
		`update_date`  datetime NULL COMMENT '更新时间' ,
  		PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='屏蔽日志表，记录屏蔽系统日志信息';
