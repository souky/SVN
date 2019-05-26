-- ----------------------------
-- Table structure for jy_menu_tab
-- ----------------------------
CREATE TABLE `jy_menu_tab` (
  `id` varchar(32) NOT NULL,
  `bsid` varchar(29) NOT NULL COMMENT 'BSID（系统名称-系统类型（0：PC，1：安卓，2：IOS）-一级菜单-二级菜单-三级菜单）',
  `menu_name` varchar(100) NOT NULL COMMENT '菜单名称',
  `menu_order` tinyint(1) unsigned NOT NULL COMMENT '菜单排序值',
  `permission_id` varchar(32) DEFAULT NULL COMMENT '权限ID',
  `menu_level` tinyint(1) unsigned NOT NULL COMMENT '菜单等级',
  `menu_icon` varchar(255) DEFAULT '' COMMENT '菜单图片',
  `menu_jsp` varchar(255) DEFAULT NULL COMMENT '菜单jsp页面',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除；1是0否',
  `create_user` varchar(100) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(100) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Table structure for jy_role
-- ----------------------------
CREATE TABLE `jy_role` (
  `id` varchar(32) NOT NULL,
  `role_name` varchar(20) DEFAULT '' COMMENT '角色名称',
  `org_id` varchar(32) DEFAULT '' COMMENT '组织机构ID',
  `is_shown` tinyint(1) DEFAULT NULL,
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for jy_permission_tab
-- ----------------------------
CREATE TABLE `jy_permission_tab` (
  `id` varchar(32) NOT NULL,
  `bsid` varchar(29) NOT NULL COMMENT 'BSID',
  `permission_name` varchar(100) NOT NULL COMMENT '权限名称',
  `permission_level` tinyint(1) DEFAULT NULL COMMENT '权限等级',
  `permission_order` tinyint(3) unsigned NOT NULL COMMENT '权限显示顺序',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '权限父ID',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除；1是0否',
  `create_user` varchar(100) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(100) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Table structure for jy_resource_tab
-- ----------------------------
CREATE TABLE `jy_resource_tab` (
  `id` varchar(32) NOT NULL,
  `resource_name` varchar(100) DEFAULT NULL COMMENT '资源名称',
  `url` varchar(255) NOT NULL COMMENT '资源url',
  `permission_id` varchar(32) NOT NULL COMMENT '权限ID',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除；1是0否',
  `create_user` varchar(100) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(100) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Table structure for jy_permission_menu_relation
-- ----------------------------
CREATE TABLE `jy_permission_menu_relation` (
  `id` varchar(32) NOT NULL,
  `menu_id` varchar(32) NOT NULL,
  `permission_id` varchar(32) NOT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除；1是0否',
  `create_user` varchar(100) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(100) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for jy_role_permission_relation
-- ----------------------------
CREATE TABLE `jy_role_permission_relation` (
  `id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  `permission_id` varchar(32) NOT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除；1是0否',
  `create_user` varchar(100) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(100) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
