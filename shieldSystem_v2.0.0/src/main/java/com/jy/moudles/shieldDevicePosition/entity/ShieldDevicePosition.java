package com.jy.moudles.shieldDevicePosition.entity;

import com.jy.common.entity.BaseEntity;
/**
*屏蔽设备位置表，记录屏蔽设备位置信息，为屏蔽信息一个关联副表
*/
public class ShieldDevicePosition extends BaseEntity{
	
	private static final long serialVersionUID = -1L;
	
	
	/**
	 * 屏蔽ID；逻辑外健
	 */
	private String shieldId;
	
	/**
	 * 逻辑考场；该信息来源为公共数据库，根据幢-楼-房间信息查询逻辑考场和物理考场
	 */
	private String logicalExamRoom;
	
	/**
	 * 物理考场；该信息来源为公共数据库；根据幢-楼-房间信息查询逻辑考场和物理考场
	 */
	private String physicalExamRoom;
	
	/**
	 * 屏蔽位置—幢（未绑定为0）
	 */
	private int shieldPositionBuilings;
	
	/**
	 * 屏蔽位置—层（未绑定为0）
	 */
	private int shieldPositionFloor;
	
	/**
	 * 屏蔽位置—房间（未绑定为0）
	 */
	private int shieldPositionRoom;
	
	
	public String getShieldId() {
		return shieldId;
	}

	public void setShieldId(String shieldId) {
		this.shieldId = shieldId;
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
	
	public int getShieldPositionBuilings() {
		return shieldPositionBuilings;
	}

	public void setShieldPositionBuilings(int shieldPositionBuilings) {
		this.shieldPositionBuilings = shieldPositionBuilings;
	}
	
	public int getShieldPositionFloor() {
		return shieldPositionFloor;
	}

	public void setShieldPositionFloor(int shieldPositionFloor) {
		this.shieldPositionFloor = shieldPositionFloor;
	}
	
	public int getShieldPositionRoom() {
		return shieldPositionRoom;
	}

	public void setShieldPositionRoom(int shieldPositionRoom) {
		this.shieldPositionRoom = shieldPositionRoom;
	}
	
}



