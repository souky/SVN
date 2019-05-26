package com.jy.moudles.clockDevicePosition.entity;

import java.util.List;

import com.jy.common.entity.BaseEntity;
/**
*时钟设备位置表，记录时钟设备位置信息，为时钟信息一个关联副表
*/
public class ClockDevicePosition extends BaseEntity{
	
	private static final long serialVersionUID = -1L;
	
	
	/**
	 * 时钟ID；逻辑外健
	 */
	private String clockId;
	
	/**
	 * 逻辑考场；该信息来源为公共数据库，根据幢-楼-房间信息查询逻辑考场和物理考场
	 */
	private String logicalExamRoom;
	
	/**
	 * 物理考场；该信息来源为公共数据库；根据幢-楼-房间信息查询逻辑考场和物理考场
	 */
	private String physicalExamRoom;
	
	/**
	 * 时钟位置—幢（未绑定为0）
	 */
	private int clockPositionBuilings;
	
	/**
	 * 时钟位置—层（未绑定为0）
	 */
	private int clockPositionFloor;
	
	/**
	 * 时钟位置—房间（未绑定为0）
	 */
	private int clockPositionRoom;
	
	// 关联字段
	/**
	 * 时钟状态：0：正常；1离线；2：异常（时间同步正确）；3：异常（时钟自检有误）
	 */
	private int clockStatus;
	
	/**
	 * 当状态为3时，该字段不允许为空，其他情况下均为空 1000101;1000101;;1000101;1000101
	 */
	private String selfCheckInfo;
	
	// 自检状态拆分
	private List<char[]> selfArray;
	
	//时钟时间
	private String clockTime;
	

	public int getClockStatus() {
		return clockStatus;
	}

	public void setClockStatus(int clockStatus) {
		this.clockStatus = clockStatus;
	}

	public String getSelfCheckInfo() {
		return selfCheckInfo;
	}

	public void setSelfCheckInfo(String selfCheckInfo) {
		this.selfCheckInfo = selfCheckInfo;
	}

	public String getClockId() {
		return clockId;
	}

	public void setClockId(String clockId) {
		this.clockId = clockId;
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
	
	public int getClockPositionBuilings() {
		return clockPositionBuilings;
	}

	public void setClockPositionBuilings(int clockPositionBuilings) {
		this.clockPositionBuilings = clockPositionBuilings;
	}
	
	public int getClockPositionFloor () {
		return clockPositionFloor ;
	}

	public void setClockPositionFloor (int clockPositionFloor ) {
		this.clockPositionFloor  = clockPositionFloor ;
	}
	
	public int getClockPositionRoom() {
		return clockPositionRoom;
	}

	public void setClockPositionRoom(int clockPositionRoom) {
		this.clockPositionRoom = clockPositionRoom;
	}

	public List<char[]> getSelfArray() {
		return selfArray;
	}

	public void setSelfArray(List<char[]> selfArray) {
		this.selfArray = selfArray;
	}

	public String getClockTime() {
		return clockTime;
	}

	public void setClockTime(String clockTime) {
		this.clockTime = clockTime;
	}
	
}



