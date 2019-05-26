/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : jsksy

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-05-05 12:59:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sync_camera_status
-- ----------------------------
DROP TABLE IF EXISTS `sync_camera_status`;
CREATE TABLE `sync_camera_status` (
  `id` varchar(32) NOT NULL,
  `SBBH` varchar(50) DEFAULT NULL COMMENT 'Ӳ�����к�',
  `SBCSDM` varchar(20) DEFAULT NULL COMMENT '���̴���',
  `GZZT` varchar(10) DEFAULT NULL COMMENT '����״̬  1:����  0������  -1���쳣',
  `SJPYMS` varchar(50) DEFAULT NULL COMMENT '���׼ʱ��(��������GMT+08:00)��ƫ�����������֣�',
  `SPZLZD` varchar(10) DEFAULT NULL COMMENT '��Ƶ������� 1:���� -1:����쳣-2:��Ƶ����-3:��Ƶ��ʧ-4:��Ƶ�ڵ�-5:������-6:�����쳣-99:�����쳣 ',
  `ZFBL` varchar(20) DEFAULT NULL COMMENT '���ֱ��ʣ��磺1920x1080��',
  `FFBL` varchar(20) DEFAULT NULL COMMENT '���ֱ��ʣ��磺1920x1080��',
  `ZML` varchar(10) DEFAULT NULL COMMENT '�����ʣ��磺4Mbps��',
  `FML` varchar(10) DEFAULT NULL COMMENT '�����ʣ��磺4Mbps��',
  `OrgCode` varchar(20) DEFAULT NULL COMMENT '��������',
  `OrgIdenCode` varchar(20) DEFAULT NULL COMMENT '����ʶ����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sync_device_info
-- ----------------------------
DROP TABLE IF EXISTS `sync_device_info`;
CREATE TABLE `sync_device_info` (
  `id` varchar(32) NOT NULL,
  `SBBH` varchar(50) DEFAULT NULL COMMENT 'Ӳ�����к�',
  `SBMC` varchar(20) DEFAULT NULL COMMENT '�豸����',
  `SBLXM` varchar(20) DEFAULT NULL COMMENT '�豸���ʹ��룬�μ�SBLXDM�豸���ʹ���',
  `SBXH` varchar(30) DEFAULT NULL COMMENT '�豸�ͺ�',
  `SIPDZ` varchar(255) DEFAULT NULL COMMENT '����Ѳ���豸����',
  `FJSIPDZ` varchar(255) DEFAULT NULL COMMENT '��������Ѳ���豸����',
  `OSDCD` varchar(10) DEFAULT NULL COMMENT 'OSD���ݵ���󳤶ȣ�һ������=2������',
  `SBTDXH` varchar(10) DEFAULT NULL COMMENT 'ͨ����ţ���������',
  `SBMAC` varchar(50) DEFAULT NULL COMMENT '�豸MAC��ַ����ģ�����������',
  `SBIP` varchar(20) DEFAULT NULL COMMENT '�豸IP����ģ�����������',
  `SBDK` varchar(10) DEFAULT NULL COMMENT '�豸�˿ڣ���ģ�����������',
  `SBYHM` varchar(30) DEFAULT NULL COMMENT '�豸��¼�û�������ģ�����������',
  `SBMM` varchar(50) DEFAULT NULL COMMENT '�豸��¼���룬Ҫ��ʹ�����Ĵ��䣬��ģ�����������',
  `SBCSDM` varchar(20) DEFAULT NULL COMMENT '���̴���',
  `SBRJBB` varchar(30) DEFAULT NULL COMMENT '����汾',
  `SBYJBB` varchar(30) DEFAULT NULL COMMENT 'Ӳ���汾',
  `SBGJBB` varchar(30) DEFAULT NULL COMMENT '�̼��汾',
  `OrgCode` varchar(20) DEFAULT NULL COMMENT '��������',
  `OrgIdenCode` varchar(20) DEFAULT NULL COMMENT '����ʶ����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sync_exam
-- ----------------------------
DROP TABLE IF EXISTS `sync_exam`;
CREATE TABLE `sync_exam` (
  `id` varchar(32) NOT NULL,
  `KSBH` varchar(200) DEFAULT NULL COMMENT '考试编号',
  `KSMC` varchar(200) DEFAULT NULL COMMENT '考试名称',
  `KSLXDM` varchar(200) DEFAULT NULL COMMENT '考试类型码',
  `KSKSRQ` varchar(32) DEFAULT NULL COMMENT '开始日期',
  `KSJSRQ` varchar(32) DEFAULT NULL COMMENT '结束日期',
  `ZGJGDM` varchar(200) DEFAULT NULL COMMENT '主管机构',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sync_exam_org_info
-- ----------------------------
DROP TABLE IF EXISTS `sync_exam_org_info`;
CREATE TABLE `sync_exam_org_info` (
  `id` varchar(32) NOT NULL,
  `exam_no` varchar(64) DEFAULT NULL COMMENT '考试编号',
  `JGDM` varchar(64) DEFAULT NULL COMMENT '机构代码',
  `JGMC` varchar(64) DEFAULT NULL COMMENT '机构名称',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sync_exam_place_info
-- ----------------------------
DROP TABLE IF EXISTS `sync_exam_place_info`;
CREATE TABLE `sync_exam_place_info` (
  `id` varchar(32) NOT NULL,
  `exam_no` varchar(64) DEFAULT NULL COMMENT '考试编号',
  `exam_season_no` varchar(64) NOT NULL COMMENT '考试场次编号',
  `CSBH` varchar(64) DEFAULT NULL COMMENT '场所编号',
  `SBBH` varchar(64) DEFAULT NULL COMMENT '设备编号',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sync_exam_season
-- ----------------------------
DROP TABLE IF EXISTS `sync_exam_season`;
CREATE TABLE `sync_exam_season` (
  `id` varchar(32) NOT NULL,
  `exam_id` varchar(64) DEFAULT NULL COMMENT '考试计划id',
  `exam_no` varchar(64) DEFAULT NULL COMMENT '考试计划编号',
  `CCBH` varchar(200) DEFAULT NULL COMMENT '场次编号',
  `CCKSSJ` varchar(32) DEFAULT NULL COMMENT '开始日期',
  `CCJSSJ` varchar(32) DEFAULT NULL COMMENT '结束日期',
  `CCMC` varchar(200) DEFAULT NULL COMMENT '场次名称',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sync_exam_season_subject
-- ----------------------------
DROP TABLE IF EXISTS `sync_exam_season_subject`;
CREATE TABLE `sync_exam_season_subject` (
  `id` varchar(32) NOT NULL,
  `exam_season_id` varchar(64) DEFAULT NULL COMMENT '考试场次id',
  `exam_season_no` varchar(64) DEFAULT NULL COMMENT '考试场次编号',
  `KMBH` varchar(200) DEFAULT NULL COMMENT '科目编号',
  `KMKSSJ` varchar(32) DEFAULT NULL COMMENT '开始日期',
  `KMJSSJ` varchar(32) DEFAULT NULL COMMENT '结束日期',
  `KMMC` varchar(200) DEFAULT NULL COMMENT '科目名称',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sync_nvr_status
-- ----------------------------
DROP TABLE IF EXISTS `sync_nvr_status`;
CREATE TABLE `sync_nvr_status` (
  `id` varchar(32) NOT NULL,
  `SBBH` varchar(50) DEFAULT NULL COMMENT 'Ӳ�����к�',
  `SBCSDM` varchar(20) DEFAULT NULL COMMENT '���̴���',
  `GZZT` varchar(10) DEFAULT NULL COMMENT '����״̬  1:����  0������  -1���쳣',
  `CCKGZT` varchar(10) DEFAULT NULL COMMENT '�洢����״̬ 1������  -1��δ����',
  `CPYJ` varchar(10) DEFAULT NULL COMMENT '����Ԥ��״̬ 1��������Ԥ�� -1��������������Ԥ��',
  `SJPYMS` varchar(50) DEFAULT NULL COMMENT '���׼ʱ��(��������GMT+08:00)��ƫ�����������֣�',
  `KJZDX` varchar(10) DEFAULT NULL COMMENT '���̿ռ��ܴ�С�����֣���λGB��',
  `KXKJDX` varchar(10) DEFAULT NULL COMMENT '���̿��пռ��С�����֣���λGB��',
  `OrgCode` varchar(20) DEFAULT NULL COMMENT '��������',
  `OrgIdenCode` varchar(20) DEFAULT NULL COMMENT '����ʶ����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sync_organization
-- ----------------------------
DROP TABLE IF EXISTS `sync_organization`;
CREATE TABLE `sync_organization` (
  `id` varchar(32) NOT NULL,
  `JGDM` varchar(200) DEFAULT NULL COMMENT '机构代码',
  `JGMC` varchar(200) DEFAULT NULL COMMENT '机构名称',
  `JGSBM` varchar(200) DEFAULT NULL COMMENT '机构识别码',
  `JGDZ` varchar(200) DEFAULT NULL COMMENT '机构地址',
  `JGCJM` varchar(200) DEFAULT NULL COMMENT '机构层级码',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sync_sip_account_info
-- ----------------------------
DROP TABLE IF EXISTS `sync_sip_account_info`;
CREATE TABLE `sync_sip_account_info` (
  `id` varchar(32) NOT NULL,
  `DLMC` varchar(64) DEFAULT NULL COMMENT '登录名',
  `DLMM` varchar(64) DEFAULT NULL COMMENT '登录密码',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sync_sip_status
-- ----------------------------
DROP TABLE IF EXISTS `sync_sip_status`;
CREATE TABLE `sync_sip_status` (
  `id` varchar(32) NOT NULL,
  `SBBH` varchar(50) DEFAULT NULL COMMENT 'Ӳ�����к�',
  `SBCSDM` varchar(20) DEFAULT NULL COMMENT '���̴���',
  `GZZT` varchar(1) DEFAULT NULL COMMENT '����״̬  1:����  0������  -1���쳣',
  `SIPZCZT` varchar(50) DEFAULT NULL COMMENT '��ʾSIP�������ϼ�SIPע���״̬��1����ʾ����������״̬����������ֱ������������Ϣ',
  `SJPYMS` varchar(50) DEFAULT NULL COMMENT '���׼ʱ��(��������GMT+08:00)��ƫ�����������֣�',
  `SJLLS` varchar(10) DEFAULT NULL COMMENT '��ǰ�ϼ�����·�������֣�',
  `BJLLS` varchar(10) DEFAULT NULL COMMENT '��ǰ��������·�������֣�',
  `SJLL` varchar(10) DEFAULT NULL COMMENT '�ϼ�������������С�������֣���λmbps/s ',
  `BJLL` varchar(10) DEFAULT NULL COMMENT '����������������С�������֣���λmbps/s ',
  `WLLLUP` varchar(10) DEFAULT NULL COMMENT '��ǰ��������������С�������֣���λmbps/s ',
  `WLLLDOWN` varchar(10) DEFAULT NULL COMMENT '��ǰ��������������С�������֣���λmbps/s',
  `OrgCode` varchar(20) DEFAULT NULL COMMENT '��������',
  `OrgIdenCode` varchar(20) DEFAULT NULL COMMENT '����ʶ����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sync_system_config
-- ----------------------------
DROP TABLE IF EXISTS `sync_system_config`;
CREATE TABLE `sync_system_config` (
  `id` varchar(32) NOT NULL,
  `keys` varchar(64) NOT NULL COMMENT '键值',
  `values` varchar(200) DEFAULT NULL COMMENT '值',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sync_system_currency
-- ----------------------------
DROP TABLE IF EXISTS `sync_system_currency`;
CREATE TABLE `sync_system_currency` (
  `id` varchar(32) NOT NULL,
  `XBDM` varchar(200) DEFAULT NULL COMMENT '性别',
  `CSLXDM` varchar(200) DEFAULT NULL COMMENT '场所类型代码',
  `JGCJDM` varchar(200) DEFAULT NULL COMMENT '机构层级代码',
  `KSLBDM` varchar(200) DEFAULT NULL COMMENT '考试类别代码',
  `SBLXDM` varchar(200) DEFAULT NULL COMMENT '设备类型代码',
  `XZQHDM` varchar(200) DEFAULT NULL COMMENT '行政区域代码',
  `YZFSDM` varchar(200) DEFAULT NULL COMMENT '(身份)验证方式代码',
  `create_user` varchar(64) DEFAULT NULL COMMENT '创建用户',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(64) DEFAULT NULL COMMENT '更新用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sync_video_plan_status
-- ----------------------------
DROP TABLE IF EXISTS `sync_video_plan_status`;
CREATE TABLE `sync_video_plan_status` (
  `id` varchar(32) NOT NULL,
  `CSBH` varchar(50) DEFAULT NULL COMMENT '����������',
  `FGQK` varchar(10) DEFAULT NULL COMMENT '�������1������ȫ���� -1:δ����¼��ƻ� -2��δ��ȫ����',
  `OrgCode` varchar(20) DEFAULT NULL COMMENT '��������',
  `OrgIdenCode` varchar(20) DEFAULT NULL COMMENT '����ʶ����',
  `ExamPlan` varchar(50) DEFAULT NULL COMMENT '���Լƻ����',
  `ExamSession` varchar(50) DEFAULT NULL COMMENT '���Գ������-�������޳���',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sync_video_save_status
-- ----------------------------
DROP TABLE IF EXISTS `sync_video_save_status`;
CREATE TABLE `sync_video_save_status` (
  `id` varchar(32) NOT NULL,
  `CSBH` varchar(50) DEFAULT NULL COMMENT '����������',
  `FGQK` varchar(10) DEFAULT NULL COMMENT '�������1������ȫ���� -2��δ��ȫ����',
  `OrgCode` varchar(20) DEFAULT NULL COMMENT '��������',
  `OrgIdenCode` varchar(20) DEFAULT NULL COMMENT '����ʶ����',
  `ExamPlan` varchar(50) DEFAULT NULL COMMENT '���Լƻ����',
  `ExamSession` varchar(50) DEFAULT NULL COMMENT '���Գ������-�������޳���',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
