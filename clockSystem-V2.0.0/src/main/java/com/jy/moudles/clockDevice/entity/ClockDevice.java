package com.jy.moudles.clockDevice.entity;

import java.util.Date;

import com.jy.common.entity.BaseEntity;

public class ClockDevice extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6903233661643756715L;

	/**
	 * 
	 */
	private String mac;
	
	/**
	 * 
	 */
	private String ip;
	
	/**
	 * 
	 */
	private String port;
	
	/**
	 * 
	 */
	private String address;
	
	/**
	 * 
	 */
	private String status;
	
	/**
	 * 
	 */
	private String listOrder;
	
	private Date clockTime;
	
	private String time;
	
	//  是否显示秒表  1:显示  0:不显示
	private int showSec;
	
	//  时钟来源  1:厚盟  2：不是厚盟
	private int source;
	
	
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getListOrder() {
		return listOrder;
	}

	public void setListOrder(String listOrder) {
		this.listOrder = listOrder;
	}

	public Date getClockTime() {
		return clockTime;
	}

	public void setClockTime(Date clockTime) {
		this.clockTime = clockTime;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getShowSec() {
		return showSec;
	}

	public void setShowSec(int showSec) {
		this.showSec = showSec;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}
	
}



