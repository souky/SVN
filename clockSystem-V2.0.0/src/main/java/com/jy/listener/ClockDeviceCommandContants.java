package com.jy.listener;

public class ClockDeviceCommandContants {
	
	/**
	 * 时钟响应命令长度
	 */
	public final static int CLOCK_REGISTER_COMMAND_LENGTH = 23;
	
	/**
	 * 时钟响应命令长度
	 */
	public final static int CLOCK_REGISTER_RESPOND_COMMAND_LENGTH = 50;
	
	/**
	 * 时钟上传信息
	 */
	public final static int CLOCK_INFO_CALLBACK_COMMAND_LENGTH = 37;
	
	/**
	 * 时钟NTP服务器配置响应命令
	 */
	public final static int CLOCK_NTP_SERVER_CONFIG_RESPOND_COMMAND_LENGTH = 14;
	
	/**
	 * 时钟自检
	 */
	public final static int CLOCK_SELF_EXAMINATION_COMMAND_LENGTH = 20;
	
	/**
	 * 时钟自检响应
	 */
	public final static int CLOCK_SELF_EXAMINATION_RESPOND_COMMAND_LENGTH = 21;
	
	/**
	 * 数码管显示命令
	 */
	public final static int CLOCK_IS_DISPLAY_COMMAND_LENGTH = 17;
	
	/**
	 * 全部数码管显示响应
	 */
	public final static int CLOCK_IS_DISPLAY_RESPOND_COMMAND_LENGTH = 17;
	
	/**
	 * 命令长度字节长度
	 */
	public final static int COMMAND_LENGTH_BYTE_LENGTH = 4;
	
	/**
	 * 命令码索引位置
	 */
	public final static int COMMAND_CODE_INDEX = 7;
	
	/**
	 * 秒钟显示命令码长度
	 */
	public final static int CLOCK_SECOND_IS_DISPLAY_COMMAND_LENGTH = 17;
	
	/**
	 * 秒钟显示命令码长度
	 */
	public final static int CLOCK_SECOND_IS_DISPLAY_RESPOND_COMMAND_LENGTH = 21;
	
	/**
	 * 时钟注册命令命令码
	 */
	public final static String CLOCK_REGISTER_COMMAND_COOE_HEX_STRING = "00";
	
	/**
	 * 时钟注册响应命令命令码
	 */
	public final static String CLOCK_REGISTER_RESPOND_COMMAND_COOE_HEX_STRING = "01";
	
	/**
	 * 时钟信息回传命令命令码
	 */
	public final static String CLOCK_INFO_CALLBACK_COMMAND_COOE_HEX_STRING = "03";
	
	/**
	 * 时钟秒针显示命令命令码
	 */
	public final static String CLOCK_SECOND_IS_DISPLAY_COMMAND_COOE_HEX_STRING = "04";
	
	/**
	 * 时钟秒针显示响应命令命令码
	 */
	public final static String CLOCK_SECOND_IS_DISPLAY_RESPOND_COMMAND_COOE_HEX_STRING = "05";
	
	/**
	 * 时钟NTP注册命令命令码
	 */
	public final static String CLOCK_NTP_SERVER_REGISTER_COMMAND_COOE_HEX_STRING = "12";
	
	/**
	 * 时钟NTP注册响应命令命令码
	 */
	public final static String CLOCK_NTP_SERVER_REGISTER_RESPOND_COMMAND_COOE_HEX_STRING = "13";
	
	/**
	 * 时钟自检命令命令码
	 */
	public final static String CLOCK_SELF_EXAMINATION_COMMAND_COOE_HEX_STRING = "20";
	
	/**
	 * 时钟自检响应命令命令码
	 */
	public final static String CLOCK_SELF_EXAMINATION_RESPOND_COMMAND_COOE_HEX_STRING = "21";
	
	/**
	 * 时钟显示命令命令命令码
	 */
	public final static String CLOCK_IS_DISPLAY_COMMAND_COOE_HEX_STRING = "24";
	
	/**
	 * 时钟显示响应命令命令命令码
	 */
	public final static String CLOCK_IS_DISPLAY_RESPOND_COMMAND_COOE_HEX_STRING = "25";
	
	/**
	 * 命令来源_服务器
	 */
	public final static String COMMAND_SOURCE_SERVER_CODE = "00";
	
	/**
	 * 命令来源_厚盟
	 */
	public final static String COMMAND_SOURCE_IHOME_CODE = "01";
	
	/**
	 * 命令来源_普中
	 */
	public final static String COMMAND_SOURCE_PRECHIN_CODE = "03";
	
	/**
	 * 默认命令起始命令码
	 */
	public final static String DEFAULT_COMMAND_START_WITH_CODE = "4A59";
	
	/**
	 * 检验位置默认命令码
	 */
	public final static String DEFAULT_VERIFICATION_COMMAND_CODE = "FFFF";
	
	/**
	 * 时钟注册结束命令码
	 */
	public final static String CLOCK_REGISTER_COMMAND_END_WITH_CODE = "343434353535363636";
	
	/**
	 * 时钟自检命令结束命令码
	 */
	public final static String CLOCK_SLEF_EXAMATION_COMMMAND_END_WITH_CODE = "81EA68C0";
	
	/**
	 * 时钟注册命令命令码
	 */
	public final static byte CLOCK_REGISTER_COMMAND_COOE_BYTE = 0;
	
	/**
	 * 时钟注册响应命令命令码
	 */
	public final static byte CLOCK_REGISTER_RESPOND_COMMAND_COOE_BYTE = 1;
	
	/**
	 * 时钟信息回传命令命令码
	 */
	public final static byte CLOCK_INFO_CALLBACK_COMMAND_COOE_BYTE = 3;
	
	/**
	 * 时钟秒针显示命令命令码
	 */
	public final static byte CLOCK_SECOND_IS_DISPLAY_COMMAND_COOE_BYTE = 4;
	
	/**
	 * 时钟秒针显示响应命令命令码
	 */
	public final static byte CLOCK_SECOND_IS_DISPLAY_RESPOND_COMMAND_COOE_BYTE = 5;
	
	/**
	 * 时钟NTP注册命令命令码
	 */
	public final static byte CLOCK_NTP_SERVER_REGISTER_COMMAND_COOE_BYTE = 18;
	
	/**
	 * 时钟NTP注册响应命令命令码
	 */
	public final static byte CLOCK_NTP_SERVER_REGISTER_RESPOND_COMMAND_COOE_BYTE = 19;
	
	/**
	 * 时钟自检命令命令码
	 */
	public final static byte CLOCK_SELF_EXAMINATION_COMMAND_COOE_BYTE = 32;
	
	/**
	 * 时钟自检响应命令命令码
	 */
	public final static byte CLOCK_SELF_EXAMINATION_RESPOND_COMMAND_COOE__BYTE = 33;
	
	/**
	 * 时钟显示命令命令命令码
	 */
	public final static byte CLOCK_IS_DISPLAY_COMMAND_COOE_BYTE = 36;
	
	/**
	 * 时钟显示响应命令命令命令码
	 */
	public final static byte CLOCK_IS_DISPLAY_RESPOND_COMMAND_COOE_BYTE = 37;
	
}
