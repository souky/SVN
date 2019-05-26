package com.jy.moudles.systemConfig.entity;

import com.jy.common.entity.BaseEntity;

public class SystemConfig extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8636718506817694772L;

	/**
	 * 键值
	 */
	private String keys;
	
	/**
	 * 值
	 */
	private String values;
	
	
	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}
	
	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}
	
}



