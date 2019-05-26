package com.jy.moudles.clockDevice.entity;

import java.util.Calendar;
import java.util.Date;

import com.jy.common.entity.BaseEntity;
import com.jy.common.utils.TypeConversionUtils;
import com.jy.listener.ClockDeviceCommandAnalysisUtils;
import com.jy.listener.ClockDeviceCommandContants;
/**
*时钟设备表，记录时钟设备信息；时钟信息来源自动和手动添加两部分
*/
public class ClockDevice extends BaseEntity{
	
	private static final long serialVersionUID = -1L;
	
	
	/**
	 * IP地址
	 */
	private String clockIpAddr;
	
	/**
	 * MAC地址，格式无特殊符号，字母大写；例：9C431EC00035
	 */
	private String clockMac;
	
	/**
	 * 是否显示秒钟；0：显示；1：不显示（默认值为0）
	 */
	private int isShowSec;
	
	/**
	 * 时钟状态：0：正常；1离线；2：异常（时间同步正确）；3：异常（时钟自检有误）;4：正在自检；
	 */
	private int clockStatus;
	
	/**
	 * 时钟开关状态：0：开；1：关
	 */
	private int clockOnOff;
	
	/**
	 * 当状态为3时，该字段不允许为空，其他情况下均为空
	 */
	private String selfCheckInfo;
	
	/**
	 * 时钟来源：1：自动；2：手动
	 */
	private int clockResource;
	
	/**
	 * 时钟厂商：1：厚盟；2：普中……
	 */
	private int clockManufacturer;
	
	// 关联位置信息
	private String logicalExamRoom;
	private String physicalExamRoom;
	
	/**
	 * 软件版本号
	 */
	private String softVersionNo;
	
	/**
	 * 硬件版本号
	 */
	private String hardwarVersionNo;
	
	/**
	 * SN码
	 */
	private String sn;
	
	/**
	 * 时钟时间
	 */
	private Date clockTime;
	
	/**
	 * ntp服务器地址
	 */
	private String ntpServer;
	
	public String getNtpServer() {
		return ntpServer;
	}

	public void setNtpServer(String ntpServer) {
		this.ntpServer = ntpServer;
	}

	public Date getClockTime() {
		return clockTime;
	}

	public void setClockTime(Date clockTime) {
		this.clockTime = clockTime;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getSoftVersionNo() {
		return softVersionNo;
	}

	public void setSoftVersionNo(String softVersionNo) {
		this.softVersionNo = softVersionNo;
	}

	public String getHardwarVersionNo() {
		return hardwarVersionNo;
	}

	public void setHardwarVersionNo(String hardwarVersionNo) {
		this.hardwarVersionNo = hardwarVersionNo;
	}

	public String getClockIpAddr() {
		return clockIpAddr;
	}

	public void setClockIpAddr(String clockIpAddr) {
		this.clockIpAddr = clockIpAddr;
	}
	
	public String getClockMac() {
		return clockMac;
	}

	public void setClockMac(String clockMac) {
		this.clockMac = clockMac;
	}
	
	public int getIsShowSec() {
		return isShowSec;
	}

	public String getLogicalExamRoom() {
		return logicalExamRoom;
	}

	public void setLogicalExamRoom(String logicalExamRoom) {
		this.logicalExamRoom = logicalExamRoom;
	}

	public String getPhysicalExamRoom() {
		return physicalExamRoom;
	}

	public void setPhysicalExamRoom(String physicalExamRoom) {
		this.physicalExamRoom = physicalExamRoom;
	}

	public void setIsShowSec(int isShowSec) {
		this.isShowSec = isShowSec;
	}
	
	public int getClockStatus() {
		return clockStatus;
	}

	public void setClockStatus(int clockStatus) {
		this.clockStatus = clockStatus;
	}
	
	public int getClockOnOff() {
		return clockOnOff;
	}

	public void setClockOnOff(int clockOnOff) {
		this.clockOnOff = clockOnOff;
	}
	
	public String getSelfCheckInfo() {
		return selfCheckInfo;
	}

	public void setSelfCheckInfo(String selfCheckInfo) {
		this.selfCheckInfo = selfCheckInfo;
	}
	
	public int getClockResource() {
		return clockResource;
	}

	public void setClockResource(int clockResource) {
		this.clockResource = clockResource;
	}
	
	public int getClockManufacturer() {
		return clockManufacturer;
	}

	public void setClockManufacturer(int clockManufacturer) {
		this.clockManufacturer = clockManufacturer;
	}

	public ClockDevice() {
	}
	
	public ClockDevice(String ip, byte[] b) {
		
		this.clockIpAddr = ip;
		
		this.clockResource = 1;
		
		int clockManufacturer = b[6] & 0xFF;
		
		this.clockManufacturer = clockManufacturer;
		
		byte[] commandLenBLength = new byte[ClockDeviceCommandContants.COMMAND_LENGTH_BYTE_LENGTH];
		
		System.arraycopy(b, 2, commandLenBLength, 0, 4);
		
		// 长度
		ClockDeviceCommandAnalysisUtils.antitoneByteArray(commandLenBLength);
		
		int length = ClockDeviceCommandAnalysisUtils.byteArrayToInteger(commandLenBLength);
		byte[] macCommand = new byte[6];
		byte[] selfExamationCommand = new byte[4];
		String mac = null;
		switch (length) {
		case ClockDeviceCommandContants.CLOCK_REGISTER_RESPOND_COMMAND_LENGTH:
			// mac
			System.arraycopy(b, 14, macCommand, 0, 6);
			mac = TypeConversionUtils.bytesToHexString(macCommand).replaceAll(" ", "").toUpperCase();
			this.clockMac = mac;
			
			// 软件版本号
			byte[] softVersionNoCommand = new byte[4];
			System.arraycopy(b, 21, softVersionNoCommand, 0, 4);
			String softVersionNo = ClockDeviceCommandAnalysisUtils.byteArrayToVersionNoOrIP(softVersionNoCommand);
			
			this.softVersionNo = softVersionNo;
			// 硬件版本号
			byte[] hardwareVersionNoCommand = new byte[4];
			System.arraycopy(b, 25, hardwareVersionNoCommand, 0, 4);
			String hardwareVersionNo = ClockDeviceCommandAnalysisUtils.byteArrayToVersionNoOrIP(hardwareVersionNoCommand);
			this.hardwarVersionNo = hardwareVersionNo;
			
			// SN
			byte[] snCommand = new byte[21];
			System.arraycopy(b, 29, snCommand, 0, 21);
			String snCommandString = TypeConversionUtils.bytesToHexString(snCommand).replaceAll(" ", "").toUpperCase();
			String sn = ClockDeviceCommandAnalysisUtils.decode(snCommandString);
			this.sn = sn;
			
			break;
		case ClockDeviceCommandContants.CLOCK_INFO_CALLBACK_COMMAND_LENGTH:
			System.arraycopy(b, 14, macCommand, 0, 6);
			mac = TypeConversionUtils.bytesToHexString(macCommand).replaceAll(" ", "").toUpperCase();
			this.clockMac = mac;
			// 时间
			byte[] dateCommand = new byte[6];
			System.arraycopy(b, 20, dateCommand, 0, 6);
			
			Calendar c = Calendar.getInstance();
			
			c.set(Calendar.YEAR, (2000 + (dateCommand[0] & 0xFF)));
			
			c.set((2000 + (dateCommand[0] & 0xFF)), ((dateCommand[1] & 0xFF) + 1), (dateCommand[2] & 0xFF), (dateCommand[3] & 0xFF), ((dateCommand[4] & 0xFF)), (dateCommand[5] & 0xFF));
			
			this.clockTime = c.getTime();
			
			int secondIsDisplay = b[26] & 0xFF;
			
			this.isShowSec = secondIsDisplay;
			
			int isDisplay = b[27] & 0xFF;
			
			this.clockOnOff = isDisplay;
			
			int selfExamation = b[28] & 0xFF;
			
			if (0 != selfExamation && 1 != selfExamation) {
				this.clockStatus = 4;
			} else if (1 == selfExamation){
				this.clockStatus = 3;
			} else {
				this.clockStatus = 1;
			}
			
			// 自检状态
			System.arraycopy(b, 29, selfExamationCommand, 0, 4);
			this.selfCheckInfo = ClockDeviceCommandAnalysisUtils.byteArrayToSelfExamationInfo(selfExamationCommand);
			
			// ntp服务器
			byte[] ntpServceCommand = new byte[4];
			System.arraycopy(b, 33, ntpServceCommand, 0, 4);
			ClockDeviceCommandAnalysisUtils.antitoneByteArray(ntpServceCommand);
			String ntp = ClockDeviceCommandAnalysisUtils.byteArrayToVersionNoOrIP(ntpServceCommand);
			this.ntpServer = ntp;
			
			break;
		case ClockDeviceCommandContants.CLOCK_NTP_SERVER_CONFIG_RESPOND_COMMAND_LENGTH:
			break;
		case ClockDeviceCommandContants.CLOCK_SELF_EXAMINATION_RESPOND_COMMAND_LENGTH:
			byte commandCode = b[ClockDeviceCommandContants.COMMAND_CODE_INDEX];
			if (ClockDeviceCommandContants.CLOCK_SECOND_IS_DISPLAY_RESPOND_COMMAND_COOE_BYTE == (commandCode & 0xFF)) {
				System.arraycopy(b, 14, macCommand, 0, 6);
				mac = TypeConversionUtils.bytesToHexString(macCommand).replaceAll(" ", "").toUpperCase();
				this.clockMac = mac;
				
				this.isShowSec = b[20] & 0xFF;
				
			} else if (ClockDeviceCommandContants.CLOCK_SELF_EXAMINATION_RESPOND_COMMAND_COOE__BYTE == (commandCode & 0xFF)) {
				System.arraycopy(b, 10, macCommand, 0, 6);
				mac = TypeConversionUtils.bytesToHexString(macCommand).replaceAll(" ", "").toUpperCase();
				this.clockMac = mac;
				if (1 == (b[16] & 0xFF) ) {
					this.clockStatus = 3;
				}
				
				System.arraycopy(b, 17, selfExamationCommand, 0, 4);
				this.selfCheckInfo = ClockDeviceCommandAnalysisUtils.byteArrayToSelfExamationInfo(selfExamationCommand);
			}
			
			break;
		case ClockDeviceCommandContants.CLOCK_IS_DISPLAY_RESPOND_COMMAND_LENGTH:
			System.arraycopy(b, 10, macCommand, 0, 6);
			mac = TypeConversionUtils.bytesToHexString(macCommand).replaceAll(" ", "").toUpperCase();
			this.clockMac = mac;
			
			this.clockOnOff = b[16] & 0xFF;
			
			break;
		default:
			break;
		}
	}
}



