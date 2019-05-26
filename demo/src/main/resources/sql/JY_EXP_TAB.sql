
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `EXP`
-- ----------------------------
DROP TABLE IF EXISTS `JY_EXP_TAB`;
CREATE TABLE `JY_EXP_TAB` (
 		`id` varchar(32) NOT NULL,
		`names` varchar(64)  NOT NULL COMMENT '姓名',
		`ages` tinyint(1)  NOT NULL COMMENT '年龄',
		`times` datetime  NOT NULL COMMENT '时间',
		`create_user`  varchar(64) NULL COMMENT '创建用户' ,
		`create_date`  datetime NULL  COMMENT '创建时间' ,
		`update_user`  varchar(64) NULL COMMENT '更新用户' ,
		`update_date`  datetime NULL COMMENT '更新时间' ,
  		PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实例';
