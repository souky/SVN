package com.jy.common.utils.protocol;

public class FrameStaticCode {

	//固定头部
	public static final String HEAD = "4A59";
	
	//帧校验
	public static final String FRAME_CHECK = "FFFF";
	
	//注册命令码
	public static final String REGISTER = "00";
	
	//修改IP命令码
	public static final String UPDATE_IP = "02";
	
	//修改秒钟命令码
	public static final String UPDATE_SEC = "04";
	
	//服务器发送
	public static final String SOURCE_SER = "00";
	
	//注册命令长度
	public static final String REGISTER_LENGTH = "19000000";
	
	//修改IP命令长度
	public static final String UPDATE_IP_LENGTH = "18000000";
	
	//修改秒钟命令长度
	public static final String UPDATE_SEC_LENGTH = "11000000";
	
	//注册命令后缀
	public static final String REGISTER_FOOT = "343434353535363636";
	
	public static final String SEC_SHOW = "00";
	
	public static final String SEC_HIDE = "01";
	
}
