package com.jy.moudles.shieldConfig.entity;

import com.jy.common.entity.BaseEntity;
/**
*屏蔽配置表
*/
public class ShieldConfig extends BaseEntity{
	
	private static final long serialVersionUID = -1L;
	
	
	/**
	 * 配置健
	 */
	private String sysKey;
	
	/**
	 * 配置值
	 */
	private String sysVal;
	
	
	public String getSysKey() {
		return sysKey;
	}

	public void setSysKey(String sysKey) {
		this.sysKey = sysKey;
	}
	
	public String getSysVal() {
		return sysVal;
	}

	public void setSysVal(String sysVal) {
		this.sysVal = sysVal;
	}
	
}



