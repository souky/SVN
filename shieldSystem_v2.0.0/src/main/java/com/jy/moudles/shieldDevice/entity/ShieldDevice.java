package com.jy.moudles.shieldDevice.entity;

import com.jy.common.entity.BaseEntity;
/**
*屏蔽设备表，记录屏蔽设备信息；屏蔽信息来源自动和手动添加两部分
*/
public class ShieldDevice extends BaseEntity{
	
	private static final long serialVersionUID = -1L;
	
	
	/**
	 * IP地址
	 */
	private String shieldIpAddr;
	
	/**
	 * MAC地址，格式无特殊符号，字母大写；例：9C431EC00035
	 */
	private String shieldMac;
	
	/**
	 * 屏蔽状态：0：正常；1离线；
	 */
	private int shieldStatus;
	
	/**
	 * 屏蔽开关状态：0：开；1：关
	 */
	private int shieldOnOff;
	
	/**
	 * 模块信息
	 */
	private String modelsInfo;
	
	/**
	 * 软件版本号
	 */
	private String softwareVersion;
	
	/**
	 * 硬件版本号
	 */
	private String hardwareVersion;
	
	/**
	 * 屏蔽来源：1：自动；2：手动
	 */
	private int shieldResource;
	
	// 加入分页
	private int pageNum;
	private int pageSize;
	
	// 关联字段
	private String logicalExamRoom;
	private String physicalExamRoom;
	
	
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
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getShieldIpAddr() {
		return shieldIpAddr;
	}

	public void setShieldIpAddr(String shieldIpAddr) {
		this.shieldIpAddr = shieldIpAddr;
	}
	
	public String getShieldMac() {
		return shieldMac;
	}

	public void setShieldMac(String shieldMac) {
		this.shieldMac = shieldMac;
	}
	
	public int getShieldStatus() {
		return shieldStatus;
	}

	public void setShieldStatus(int shieldStatus) {
		this.shieldStatus = shieldStatus;
	}
	
	public int getShieldOnOff() {
		return shieldOnOff;
	}

	public void setShieldOnOff(int shieldOnOff) {
		this.shieldOnOff = shieldOnOff;
	}
	
	public String getModelsInfo() {
		return modelsInfo;
	}

	public void setModelsInfo(String modelsInfo) {
		this.modelsInfo = modelsInfo;
	}
	
	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}
	
	public String getHardwareVersion() {
		return hardwareVersion;
	}

	public void setHardwareVersion(String hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}
	
	public int getShieldResource() {
		return shieldResource;
	}

	public void setShieldResource(int shieldResource) {
		this.shieldResource = shieldResource;
	}
	
}



