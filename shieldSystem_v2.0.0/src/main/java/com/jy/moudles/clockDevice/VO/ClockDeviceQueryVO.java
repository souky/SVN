package com.jy.moudles.clockDevice.VO;

import com.jy.moudles.clockDevice.entity.ClockDevice;

public class ClockDeviceQueryVO extends ClockDevice{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8677733424358215079L;

	// 当前页
	private int pageNum;
	
	// 每页数量
	private int pageSize;
	
	//物理考场
	private String physicalExamRoom;
	
	//逻辑考场
	private String logicalExamRoom;

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

	public String getPhysicalExamRoom() {
		return physicalExamRoom;
	}

	public void setPhysicalExamRoom(String physicalExamRoom) {
		this.physicalExamRoom = physicalExamRoom;
	}

	public String getLogicalExamRoom() {
		return logicalExamRoom;
	}

	public void setLogicalExamRoom(String logicalExamRoom) {
		this.logicalExamRoom = logicalExamRoom;
	}
	

	
}