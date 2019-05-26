package com.jy.common.timer.constant;

public class JSKSYTestUrl {

	public static final String URL = "http://36.152.8.182:11181/openapi/";
	
	//获取token
	public static final String GETTOKEN = "auth/get-accesstoken";
	
	//获取码表信息
	public static final String GETALLCODE = "vis/get-all-code";
	
	//获取机构信息
	public static final String GETALLORGINFO = "Vis/get-all-orginfo";
	
	//获取考试计划信息
	public static final String GETALLEXAMPLAN = "Vis/get-all-examplan";
	
	//获取机构启用信息
	public static final String GETALLORGINFOENABLE = "Vis/get-all-orginfo-enable";
	
	//获取场所及设备启用信息
	public static final String GETALLPLACEINFOENABLE = "Vis/get-all-placeinfo-enable";
	
	//获取SIP服务器登录用户信息
	public static final String GETALLSIPACCOUNTINFO = "Vis/get-all-sipaccountinfo";
	
	//提交作弊防控设备基本信息
	public static final String SETCHEATDEVICEINFO = "vis/set-cheat-deviceinfo";
	
	//提交作弊防控设备工作状态
	public static final String SETCHEATDEVICESTATUS = "vis/set-cheat-device-status";
	
	//提交作弊防控频点信息
	public static final String SETCHEATFREQUENCYINFO = "vis/set-cheat-frequencyinfo";
	
	
	//提交网上巡查设备基本信息
	public static final String SETNETVIEWDEVICEINFO =  "Vis/set-netview-deviceinfo";
	
	//提交网上巡查SIP网关服务器工作状态
	public static final String SETNETVIEWDEVICESIPSTATUS =  "Vis/set-netview-device-sip-status";
	
	//提交网上巡查NVR存储服务器工作状态
	public static final String SETNETVIEWDEVICENVRSTATUS =  "Vis/set-netview-device-nvr-status";
	
	//提交网上巡查摄像机的工作状态
	public static final String SETNETVIEWDEVICECAMERASTATUS =  "Vis/set-netview-device-camera-status";
	
	//提交考场及保密室考前录像计划是否完整覆盖考试计划的状态
	public static final String SETNETVIEWDEVICEVIDEOPLANCOVERSTATUS =  "Vis/set-netview-device-videoplan-cover-status";
	
	//提交考场及保密室考后录像存储是否完整覆盖考试计划的状态
	public static final String SETNETVIEWDEVICEVIDEOSAVECOVERSTATUS =  "Vis/set-netview-device-videosave-cover-status";
	
	//提交OSD设置结果信息
	public static final String SETNETVIEWDEVICEOSDSETTINGSSTATUS =  "Vis/set-netview-device-osdsettings-status";
}
