package com.jy.moudles.clockSat.entity;

import com.jy.common.entity.BaseEntity;
/**
*卫星信息表，记录根据串口抓取卫星信息
*/
public class ClockSat extends BaseEntity{
	
	private static final long serialVersionUID = -1L;
	
	
	/**
	 * 卫星编号
	 */
	private int satNo;
	
	/**
	 * 卫星类型：0：BD；1：GP；2：GALILEO（欧盟）；3：GLONASS（俄罗斯）
	 */
	private int satType;
	
	/**
	 * 卫星仰角
	 */
	private int satElevation;
	
	/**
	 * 卫星方位角
	 */
	private int satAzimuth;
	
	/**
	 * 卫星信噪比
	 */
	private int satSnr;
	
	
	public int getSatNo() {
		return satNo;
	}

	public void setSatNo(int satNo) {
		this.satNo = satNo;
	}
	
	public int getSatType() {
		return satType;
	}

	public void setSatType(int satType) {
		this.satType = satType;
	}
	
	public int getSatElevation() {
		return satElevation;
	}

	public void setSatElevation(int satElevation) {
		this.satElevation = satElevation;
	}
	
	public int getSatAzimuth() {
		return satAzimuth;
	}

	public void setSatAzimuth(int satAzimuth) {
		this.satAzimuth = satAzimuth;
	}
	
	public int getSatSnr() {
		return satSnr;
	}

	public void setSatSnr(int satSnr) {
		this.satSnr = satSnr;
	}
	
}



