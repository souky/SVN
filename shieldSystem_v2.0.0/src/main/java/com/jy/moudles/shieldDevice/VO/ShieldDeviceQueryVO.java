package com.jy.moudles.shieldDevice.VO;

import com.jy.moudles.shieldDevice.entity.ShieldDevice;

public class ShieldDeviceQueryVO extends ShieldDevice{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	//物理考场
	private String physicalExamRoom;
	
	//逻辑考场
	private String logicalExamRoom;

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
