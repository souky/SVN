package com.jy.common.persistence.annotation.logAspect;

import java.util.regex.Pattern;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jy.common.persistence.annotation.logAspect.exception.LogAspectOpTypeException;
import com.jy.moudles.clockLog.entity.ClockLog;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Aspect
@Component
public class LogAspect implements ApplicationListener<ContextRefreshedEvent>{
	
	private CacheManager cacheManager;
	
	private static final String PATTERN = "/\\{([1-9]|1[0-9]|20)\\}/g"; 
	
	/**
	 * 执行方法正常结束后插入日志
	 * 
	 * @param jp
	 * @param jryLoggoin
	 */
	@SuppressWarnings("unused")
	@AfterReturning (value = "@annotation(jryLogging)")
	public void addLogAfterSuccess(JoinPoint jp, JRYLogging jryLogging) {
		
		if (OpType.ADD != jryLogging.opType() && OpType.EDIT != jryLogging.opType() && OpType.DEL != jryLogging.opType()) {
			throw new LogAspectOpTypeException();
		}
		// 获取目标方法参数
		Object[] args = jp.getArgs();
		String opInfo = jryLogging.opInfo();
		for (Object arg : args) {
			
			if (isBaseType(arg)) {
				int i = 0;
				for (String params : jryLogging.opReplaceParams()) {
					opInfo = opInfo.replaceAll("\\{" + (i + 1) + "\\}", String.valueOf(arg));
					i++;
				}
			} else {
				String jsonString = JSONObject.toJSONString(arg);
				JSONObject jsonObject = JSONObject.parseObject(jsonString);
				int i = 0;
				for (String params : jryLogging.opReplaceParams()) {
					if (null != jsonObject.get(params)) {
						opInfo = opInfo.replaceAll("\\{" + (i + 1) + "\\}", String.valueOf(jsonObject.get(params)));
						i++;
					}
				}
			}
			
			if (!Pattern.matches(PATTERN, opInfo)) {
				break;
			}
		}
		
		// 获取目标方法类名称
		String className = jp.getTarget().getClass().toString();
		// 获取目标方法签名 
//		String signature = jp.getSignature().toString();
//		String methodName = signature.substring(signature.lastIndexOf(".") + 1, signature.indexOf("("));
		String operationMoudle = "";
		if (className.contains("ClockDevice")) {
			operationMoudle = "设备模块";
		} else if (className.contains("ClockLog")) {
			operationMoudle = "日志模块";
		} else if (className.contains("ClockDevicePosition")) {
			operationMoudle = "位置模块";
		} else if (className.contains("ClockConfig")) {
			operationMoudle = "配置模块";
		} else {
			operationMoudle = "其他模块";
		}
		
		putClockLog(jryLogging.opType(), opInfo, operationMoudle);
	}
	
	public ClockLog putClockLog(OpType opType, String opInfo, String operationMoudle) {
		
		ClockLog log = new ClockLog(opType, opInfo, operationMoudle);
		
		if (null != cacheManager) {
			Cache cache = cacheManager.getCache("logCache");
			
			if (null != cache) {
				Element e = new Element(log.getId(), log);
				cache.put(e);
			}
		}
		
		return log;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean isBaseType(Object object) {
		Class className = object.getClass();
		if (className.equals(java.lang.Integer.class) || className.equals(java.lang.Byte.class)
				|| className.equals(java.lang.Long.class) || className.equals(java.lang.Double.class)
				|| className.equals(java.lang.Float.class) || className.equals(java.lang.Character.class)
				|| className.equals(java.lang.Short.class) || className.equals(java.lang.Boolean.class)) {
			return true;
		}
		return false;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (null != event.getApplicationContext().getParent()) {
			cacheManager = (CacheManager) event.getApplicationContext().getBean("cacheManagerFactory");
		}
	}
}
