package com.jy.common.utils.https;

import java.util.Map;

import com.alibaba.fastjson.JSON;

public class RequestPost {

	private Map<Object,Object> Certification;
	private Map<Object,Object> Data;

	public Map<Object, Object> getCertification() {
		return Certification;
	}

	public void setCertification(Map<Object, Object> certification) {
		Certification = certification;
	}
	
	public Map<Object,Object> getData() {
		return Data;
	}

	public void setData(Map<Object,Object> data) {
		Data = data;
	}
	
	@Override
	public String toString() {
		String returns = "";
		returns = JSON.toJSON(this).toString();
		returns = returns.replace("certification", "Certification");
		returns = returns.replace("data", "Data");
		return returns;
	}
}
