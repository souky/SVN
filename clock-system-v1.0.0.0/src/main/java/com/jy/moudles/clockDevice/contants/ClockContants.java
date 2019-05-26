package com.jy.moudles.clockDevice.contants;

/**
 * 时钟常量类
 * 
 * @author jinxiaoxiang
 *
 */
public interface ClockContants {

	/**
	 * 帧头——固定帧头
	 */
	public final String IHOME_FRARM_HEAD_FIXED_FRAME = "4A59";
	
	/**
	 * 帧头——来源：服务器
	 */
	public final String IHOME_FRARM_HEAD_SOURCE_SERVER = "00";
	
	/**
	 * 帧头——来源（厚盟）
	 */
	public final String IHOME_FRARM_HEAD_SOURCE_IHOME = "01";
	
	/**
	 * 帧头——命令码：服务器注册
	 */
	public final String IHOME_FRARM_HEAD_COMMAND_CODE_REGISTER_REQUEST = "00";
	
	/**
	 * 帧头——命令码：时钟响应
	 */
	public final String IHOME_FRARM_HEAD_COMMAND_CODE_REGISTER_RESPOND = "01";
	
	/**
	 * 帧头——命令码：修改时钟IP
	 */
	public final String IHOME_FRARM_HEAD_COMMAND_CODE_CLOCK_MODIFY_IP = "02";
	
	/**
	 * 帧头——命令码：时钟信息回传
	 */
	public final String IHOME_FRARM_HEAD_COMMAND_CODE_CLOCK_INFO_CALLBACK = "03";
	
	/**
	 * 帧头——命令码：修改秒钟显示信息
	 */
	public final String IHOME_FRARM_HEAD_COMMAND_CODE_CLOCK_MODIFY_SECOND_DISPLAY_STATUS_REQUEST = "04";
	
	/**
	 * 帧头——命令码：秒钟显示响应
	 */
	public final String IHOME_FRARM_HEAD_COMMAND_CODE_CLOCK_MODIFY_SECOND_DISPLAY_STATUS_RESPOND = "05";
	
	/**
	 * 帧头——帧校验
	 */
	public final String IHOME_FRARM_HEAD_DEFAULT_FRARM_CHECK = "FFFF";
	
	/**
	 * 注册字段
	 */
	public final String IHOME_FIXED_REGISTER = "343434353535363636";
}
