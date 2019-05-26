package com.jy.listener;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

public class ClockDeviceCommandAnalysisUtils {
	
	/**
	 * byte数组反序输出
	 * 
	 * @param b
	 */
	public static void antitoneByteArray(byte[] b) {
		byte temp;
		// 开始对调
		for (int i = 0; i < b.length / 2; i++) {
			temp = b[i];
			b[i] = b[b.length - i - 1];
			b[b.length - i - 1] = temp;
		}
	}
	
	/**
	 * byte数组数字
	 * 
	 * @param b
	 * @return
	 */
	public static int byteArrayToInteger(byte[] b) {
		return b[3] & 0xFF | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16 | (b[0] & 0xFF) << 24;
	}
	
	/**
	 * byte数组转版本号
	 * 
	 * @param b
	 * @return
	 */
	public static String byteArrayToVersionNoOrIP(byte[] b) {
		StringBuffer sb = new StringBuffer();
		
		for (byte a : b) {
			sb.append(a & 0xFF);
			sb.append(".");
		}
		
		String versionNo = sb.toString();
		
		versionNo = versionNo.substring(0, versionNo.lastIndexOf("."));
		
		return versionNo;
	}
	
	/**
	 * 将字符串解码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String encode(String str) {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString().trim();
	}
	
	/**
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static String decode(String bytes) {
		String hexString = "0123456789ABCDEF";
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}
	
	/**
	 * 转换时钟自检信息
	 * 
	 * @param b
	 * @return
	 */
	public static String byteArrayToSelfExamationInfo(byte[] b) {
		
		if (4 != b.length) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		
		
		sb.append(StringUtils.leftPad(Integer.toBinaryString(b[0] & 0xff), 7, '0'));
		sb.append(";");
		sb.append(StringUtils.leftPad(Integer.toBinaryString(b[1] & 0xff), 7, '0'));
		sb.append(";");
		sb.append(";");
		sb.append(StringUtils.leftPad(Integer.toBinaryString(b[2] & 0xff), 7, '0'));
		sb.append(";");
		sb.append(StringUtils.leftPad(Integer.toBinaryString(b[3] & 0xff), 7, '0'));
		
		return sb.toString();
		
	}
	
	/**
	 * 时钟注册命令
	 * 
	 * @param ip
	 * @return
	 */
	public static String genServerRegisterCommand(String ip) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(ClockDeviceCommandContants.DEFAULT_COMMAND_START_WITH_CODE);
		sb.append(genLengthCommandCode(ClockDeviceCommandContants.CLOCK_REGISTER_COMMAND_LENGTH));
		sb.append(ClockDeviceCommandContants.COMMAND_SOURCE_SERVER_CODE);
		sb.append(ClockDeviceCommandContants.CLOCK_REGISTER_COMMAND_COOE_HEX_STRING);
		sb.append(ClockDeviceCommandContants.DEFAULT_VERIFICATION_COMMAND_CODE);
		sb.append(hexIp(ip));
		sb.append(ClockDeviceCommandContants.CLOCK_REGISTER_COMMAND_END_WITH_CODE);
		
		return sb.toString();
	}
	
	/**
	 * 秒钟显示剩下吗
	 * @param mac
	 * @return
	 */
	public static String genSecondIsDisplayCommand(String mac, int status) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(ClockDeviceCommandContants.DEFAULT_COMMAND_START_WITH_CODE);
		sb.append(genLengthCommandCode(ClockDeviceCommandContants.CLOCK_SECOND_IS_DISPLAY_COMMAND_LENGTH));
		sb.append(ClockDeviceCommandContants.COMMAND_SOURCE_SERVER_CODE);
		sb.append(ClockDeviceCommandContants.CLOCK_SECOND_IS_DISPLAY_COMMAND_COOE_HEX_STRING);
		sb.append(ClockDeviceCommandContants.DEFAULT_VERIFICATION_COMMAND_CODE);
		
		sb.append(mac);
		
		sb.append(StringUtils.leftPad(Integer.toHexString(status), 2, '0'));
		
		return sb.toString();
	}
	
	/**
	 * 生成NTP注册服务命令
	 * @param ntpIP
	 * @return
	 */
	public static String genNTPServerRegisterCommand(String ntpIP) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(ClockDeviceCommandContants.DEFAULT_COMMAND_START_WITH_CODE);
		sb.append(genLengthCommandCode(ClockDeviceCommandContants.CLOCK_NTP_SERVER_CONFIG_RESPOND_COMMAND_LENGTH));
		sb.append(ClockDeviceCommandContants.COMMAND_SOURCE_SERVER_CODE);
		sb.append(ClockDeviceCommandContants.CLOCK_NTP_SERVER_REGISTER_COMMAND_COOE_HEX_STRING);
		sb.append(ClockDeviceCommandContants.DEFAULT_VERIFICATION_COMMAND_CODE);
		
		sb.append(hexIp(ntpIP));
		
		return sb.toString();
	}
	
	/**
	 * 生成自检命令码
	 * 
	 * @param mac
	 * @return
	 */
	public static String genSelfExamationCommand(String mac) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(ClockDeviceCommandContants.DEFAULT_COMMAND_START_WITH_CODE);
		sb.append(genLengthCommandCode(ClockDeviceCommandContants.CLOCK_SELF_EXAMINATION_COMMAND_LENGTH));
		sb.append(ClockDeviceCommandContants.COMMAND_SOURCE_SERVER_CODE);
		sb.append(ClockDeviceCommandContants.CLOCK_SELF_EXAMINATION_COMMAND_COOE_HEX_STRING);
		
		sb.append(genVerificationCommandCode());
		
		sb.append(mac);
		
		sb.append(ClockDeviceCommandContants.CLOCK_SLEF_EXAMATION_COMMMAND_END_WITH_CODE);
		
		return sb.toString();
	}
	
	/**
	 * 生成关闭时钟自检命令
	 * 
	 * @param mac
	 * @param status
	 * @return
	 */
	public static String genIsDisplayCommand(String mac, int status) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(ClockDeviceCommandContants.DEFAULT_COMMAND_START_WITH_CODE);
		sb.append(genLengthCommandCode(ClockDeviceCommandContants.CLOCK_IS_DISPLAY_COMMAND_LENGTH));
		sb.append(ClockDeviceCommandContants.COMMAND_SOURCE_SERVER_CODE);
		sb.append(ClockDeviceCommandContants.CLOCK_IS_DISPLAY_COMMAND_COOE_HEX_STRING);
		
		sb.append(genVerificationCommandCode());
		
		sb.append(mac);
		
		sb.append(StringUtils.leftPad(Integer.toHexString(status), 2, '0'));
		return sb.toString();
	}
	
	/**
	 * 生成校验码
	 * 
	 * @return
	 */
	public static String genVerificationCommandCode () {
		Calendar c = Calendar.getInstance();
		StringBuffer sb = new StringBuffer();
		sb.append((c.get(Calendar.MONTH) + 1)).append(c.get(Calendar.DATE));
		int verificationCommandCode = (0 == (c.get(Calendar.DATE) % 2)) ? (c.get(Calendar.YEAR) | Integer.parseInt(sb.toString())) : (c.get(Calendar.YEAR) & Integer.parseInt(sb.toString()));
		
		return String.valueOf(verificationCommandCode);
	}
	
	/**
	 * String数组倒叙排列
	 * 
	 * @param b
	 */
	public static void antitoneStringArray(String[] b) {
		String temp;
		// 开始对调
		for (int i = 0; i < b.length / 2; i++) {
			temp = b[i];
			b[i] = b[b.length - i - 1];
			b[b.length - i - 1] = temp;
		}
	}
	
	public static String hexIp(String ip) {
		if (null == ip || "" == ip.trim()) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		String[] ips = ip.split("\\.");
		if (ips.length != 4) {
			return "";
		}
		sb.append(String.format("%02x", Integer.parseInt(ips[3])));
		sb.append(String.format("%02x", Integer.parseInt(ips[2])));
		sb.append(String.format("%02x", Integer.parseInt(ips[1])));
		sb.append(String.format("%02x", Integer.parseInt(ips[0])));

		return sb.toString();
	}
	
	public static String genLengthCommandCode(int length) {
		StringBuffer sb = new StringBuffer();
		String regex = "(.{2})";
		String [] ss = StringUtils.leftPad(Integer.toHexString(length), 8, '0').replaceAll(regex, "$1 ").split(" ");
		antitoneStringArray(ss);
		for (String s : ss) {
			sb.append(s);
		}
		return sb.toString();
	}
}
