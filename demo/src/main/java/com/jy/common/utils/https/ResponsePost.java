package com.jy.common.utils.https;

import java.util.Map;


public class ResponsePost {

	private String Code;
	
	private String Data;
	
	private String Message;
	
	private String Method;
	
	private Map<Object,Object> RequestInfo;
	
	private boolean Result;
	
	private String Source;
	

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public String getMethod() {
		return Method;
	}

	public void setMethod(String method) {
		Method = method;
	}

	public Map<Object, Object> getRequestInfo() {
		return RequestInfo;
	}

	public void setRequestInfo(Map<Object, Object> requestInfo) {
		RequestInfo = requestInfo;
	}

	public boolean getResult() {
		return Result;
	}

	public void setResult(boolean result) {
		Result = result;
	}

	public String getSource() {
		return Source;
	}

	public void setSource(String source) {
		Source = source;
	}
	
//	public Object getDateToClass(Class<?> beanClass) throws Exception{
//	Object obj = beanClass.newInstance();  
//	Map<String,Object> map = this.Data;
//	Set<String> keyset = map.keySet();
//	for(String s : keyset) {
//		Method md = beanClass.getMethod("set"+s,String.class);
//		md.invoke(obj, map.get(s));
//	}
//	
//	return obj;
//}
	
}
