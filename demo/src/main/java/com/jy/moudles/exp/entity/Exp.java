package com.jy.moudles.exp.entity;
import java.util.Date;

import com.jy.common.entity.BaseEntity;
/**
*实例
*/
public class Exp extends BaseEntity{
	
	private static final long serialVersionUID = -1L;
	
	
	/**
	 * 姓名
	 */
	private String names;
	
	/**
	 * 年龄
	 */
	private int ages;
	
	/**
	 * 时间
	 */
	private Date times;
	
	
	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}
	
	public int getAges() {
		return ages;
	}

	public void setAges(int ages) {
		this.ages = ages;
	}
	
	public Date getTimes() {
		return times;
	}

	public void setTimes(Date times) {
		this.times = times;
	}
	
}



