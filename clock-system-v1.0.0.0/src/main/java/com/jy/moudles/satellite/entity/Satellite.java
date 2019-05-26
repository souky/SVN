package com.jy.moudles.satellite.entity;

import com.jy.common.entity.BaseEntity;

public class Satellite extends BaseEntity{

	
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 卫星编号 GPS+40
	 */
	private String saNo;
	
	/**
	 * 卫星类型 1：GPS 2：北斗
	 */
	private int saType;
	
	/**
	 * 信噪比
	 */
	private String noiseSignal;
	
	/**
	 * 仰角
	 */
	private String elevation;
	
	/**
	 * 方位角
	 */
	private String azimuth;
	
	/**
	 * 是否参与计算
	 */
	private int isCalculation;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getSaNo() {
		return saNo;
	}

	public void setSaNo(String saNo) {
		this.saNo = saNo;
	}
	
	public int getSaType() {
		return saType;
	}

	public void setSaType(int saType) {
		this.saType = saType;
	}
	
	public String getNoiseSignal() {
		return noiseSignal;
	}

	public void setNoiseSignal(String noiseSignal) {
		this.noiseSignal = noiseSignal;
	}
	
	public String getElevation() {
		return elevation;
	}

	public void setElevation(String elevation) {
		this.elevation = elevation;
	}
	
	public String getAzimuth() {
		return azimuth;
	}

	public void setAzimuth(String azimuth) {
		this.azimuth = azimuth;
	}
	
	public int getIsCalculation() {
		return isCalculation;
	}

	public void setIsCalculation(int isCalculation) {
		this.isCalculation = isCalculation;
	}
	
}



