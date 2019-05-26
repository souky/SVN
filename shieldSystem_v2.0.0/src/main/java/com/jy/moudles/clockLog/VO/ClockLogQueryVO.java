package com.jy.moudles.clockLog.VO;

import com.jy.moudles.clockLog.entity.ClockLog;

public class ClockLogQueryVO extends ClockLog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2360042477057102307L;
	
	private int pageNum;
	
	private int pageSize;

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
	
	

}
