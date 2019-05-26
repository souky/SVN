package com.jy.moudles.sipAccountInfo.entity;

import com.jy.common.entity.BaseEntity;

public class SipAccountInfo extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4470696054060732565L;

	/**
	 * 登录名
	 */
	private String dlmc;
	
	/**
	 * 登录密码
	 */
	private String dlmm;
	
	
	public String getDlmc() {
		return dlmc;
	}

	public void setDlmc(String dlmc) {
		this.dlmc = dlmc;
	}
	
	public String getDlmm() {
		return dlmm;
	}

	public void setDlmm(String dlmm) {
		this.dlmm = dlmm;
	}
	
}



