
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `${objectName?upper_case}`
-- ----------------------------
DROP TABLE IF EXISTS `${tableName?upper_case}`;
CREATE TABLE `JY_${tableName?upper_case}` (
 		`id` varchar(32) NOT NULL,
		<#list fieldList as var>
		`${var[0]}` ${var[1]}${var[2]} <#if var[7] != ''> DEFAULT '${var[7]}'</#if> NOT NULL COMMENT '${var[5]}',
		</#list>
		`create_user`  varchar(64) NULL COMMENT '创建用户' ,
		`create_date`  datetime NULL  COMMENT '创建时间' ,
		`update_user`  varchar(64) NULL COMMENT '更新用户' ,
		`update_date`  datetime NULL COMMENT '更新时间' ,
  		PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='${(remark)!}';
