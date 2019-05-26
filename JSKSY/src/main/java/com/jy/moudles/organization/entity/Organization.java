package com.jy.moudles.organization.entity;

import com.jy.common.entity.BaseEntity;

public class Organization extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6884948389478266221L;

	/**
	 * 机构代码
	 */
	private String jgdm;
	
	/**
	 * 机构名称
	 */
	private String jgmc;
	
	/**
	 * 机构识别码
	 */
	private String jgszdm;
	
	/**
	 * 机构地址
	 */
	private String jgdz;
	
	/**
	 * 机构层级码
	 */
	private String jgcjm;
	
	
	public String getJgdm() {
		return jgdm;
	}

	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	
	public String getJgmc() {
		return jgmc;
	}

	public void setJgmc(String jgmc) {
		this.jgmc = jgmc;
	}
	
	public String getJgszdm() {
		return jgszdm;
	}

	public void setJgszdm(String jgszdm) {
		this.jgszdm = jgszdm;
	}
	
	public String getJgdz() {
		return jgdz;
	}

	public void setJgdz(String jgdz) {
		this.jgdz = jgdz;
	}
	
	public String getJgcjm() {
		return jgcjm;
	}

	public void setJgcjm(String jgcjm) {
		this.jgcjm = jgcjm;
	}
	
}



