package com.jy.moudles.shieldExamPlan.entity;
import java.util.Date;

import com.jy.common.entity.BaseEntity;
/**
*考试计划表
*/
public class ShieldExamPlan extends BaseEntity{
	
	private static final long serialVersionUID = -1L;
	
	
	/**
	 * 考试计划名称
	 */
	private String examName;
	
	/**
	 * 考试计划编号
	 */
	private String examNo;
	
	/**
	 * 考试类型ID
	 */
	private String examTypeId;
	
	/**
	 * 考试计划状态 0：启用 1：禁用
	 */
	private int examStatus;
	
	/**
	 * 考试开始时间
	 */
	//@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	
	/**
	 * 考试结束时间
	 */
	//@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	
	
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

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}
	
	public String getExamNo() {
		return examNo;
	}

	public void setExamNo(String examNo) {
		this.examNo = examNo;
	}
	
	public String getExamTypeId() {
		return examTypeId;
	}

	public void setExamTypeId(String examTypeId) {
		this.examTypeId = examTypeId;
	}
	
	public int getExamStatus() {
		return examStatus;
	}

	public void setExamStatus(int examStatus) {
		this.examStatus = examStatus;
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}



