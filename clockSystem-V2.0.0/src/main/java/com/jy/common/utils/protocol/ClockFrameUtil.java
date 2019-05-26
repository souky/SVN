package com.jy.common.utils.protocol;


import org.apache.commons.lang3.StringUtils;


public class ClockFrameUtil {

	
	//参数错误回传空字符串 服务器注册命令
	public static String clockRegister(String ip,int port) {
		StringBuffer returnString = new StringBuffer();
		String frameHead = getFrameHead(FrameStaticCode.REGISTER_LENGTH, FrameStaticCode.SOURCE_SER, FrameStaticCode.REGISTER);
		returnString.append(frameHead);
		String ipHex = hexIp(ip);
		if(StringUtils.isBlank(ipHex)) {
			return "";
		}
		returnString.append(ipHex);
		returnString.append(String.format("%04x", port));
		returnString.append(FrameStaticCode.REGISTER_FOOT);
		return returnString.toString();
	}
	
	//参数错误回传空字符串 时钟修改IP命令
	public static String clockUpdateIp(String newIp,String oldIp,String mac) {
		StringBuffer returnString = new StringBuffer();
		String frameHead = getFrameHead(FrameStaticCode.UPDATE_IP_LENGTH, FrameStaticCode.SOURCE_SER, FrameStaticCode.UPDATE_IP);
		returnString.append(frameHead);
		String newIpHex = hexIp(newIp);
		String oldIpHex = hexIp(oldIp);
		if(StringUtils.isBlank(newIpHex) || StringUtils.isBlank(oldIpHex)) {
			return "";
		}
		returnString.append(newIpHex);
		if(mac.indexOf("-") >= 0) {
			mac = mac.replaceAll("-","");
		}
		returnString.append(mac);
		returnString.append(oldIpHex);
		return returnString.toString();
	}
	
	// 时钟修改秒钟显示情况
	public static String clockUpdateSec(boolean isShow,String mac) {
		StringBuffer returnString = new StringBuffer();
		String frameHead = getFrameHead(FrameStaticCode.UPDATE_SEC_LENGTH, FrameStaticCode.SOURCE_SER, FrameStaticCode.UPDATE_SEC);
		returnString.append(frameHead);
		if(mac.indexOf("-") >= 0) {
			mac = mac.replaceAll("-","");
		}
		returnString.append(mac);
		returnString.append(isShow?FrameStaticCode.SEC_SHOW : FrameStaticCode.SEC_HIDE);
		return returnString.toString();
	}
	
	
	
	private static String getFrameHead(String frameLength,String source,String frameCode) {
		StringBuffer frameHead = new StringBuffer();
		frameHead.append(FrameStaticCode.HEAD);
		frameHead.append(frameLength);
		frameHead.append(source);
		frameHead.append(frameCode);
		frameHead.append(FrameStaticCode.FRAME_CHECK);
		return frameHead.toString();
	}
	
	
	
	/**
	 * 将传入的string反转,传入的string长度必须为偶数
	 * @param string
	 */
	public static String reverseHex(String string){
		
		if(string == null || string.length() == 0 || string.length() % 2 != 0){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = string.length()/2; i > 0; i--) {
			sb.append(string.substring((i-1)*2,i*2));
		}
		
		return sb.toString();
	}
	
	public static String hexIp(String ip) {
		StringBuffer sb = new StringBuffer();
		String[] ips = ip.split("\\.");
		if(ips.length != 4) {
			return "";
		}
		sb.append(String.format("%02x", Integer.parseInt(ips[3])));
		sb.append(String.format("%02x", Integer.parseInt(ips[2])));
		sb.append(String.format("%02x", Integer.parseInt(ips[1])));
		sb.append(String.format("%02x", Integer.parseInt(ips[0])));
		
		return sb.toString();
	}
}
