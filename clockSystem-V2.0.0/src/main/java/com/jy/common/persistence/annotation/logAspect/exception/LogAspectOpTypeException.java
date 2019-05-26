package com.jy.common.persistence.annotation.logAspect.exception;

public class LogAspectOpTypeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void printStackTrace() {
		System.err.println("日志注解参数---参数类型错误！");
	}
}
