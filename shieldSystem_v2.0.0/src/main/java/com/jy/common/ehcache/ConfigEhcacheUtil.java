package com.jy.common.ehcache;

import com.jy.moudles.clockConfig.entity.ClockConfig;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class ConfigEhcacheUtil {
	private static ConfigEhcacheUtil configEhcacheUtil;

	private Cache configCache;

	public ConfigEhcacheUtil() {
		configCache = EhcacheUtil.getInstance().getCache("configCache");
	}
	
	public ConfigEhcacheUtil(CacheManager cacheManager) {
		configCache = cacheManager.getCache("configCache");
	}

	public static ConfigEhcacheUtil getInstance() {
		if (null == configEhcacheUtil) {
			configEhcacheUtil = new ConfigEhcacheUtil();
		}

		return configEhcacheUtil;
	}
	
	public void addConfig(ClockConfig config) {
		Element e = new Element(config.getSysKey(), config);
		configCache.put(e);
	}
	
	public void addConfig(String sysKey, String sysVal) {
		
		Element e = configCache.get(sysKey);
		
		if (null != e) {
			updateConfig(sysKey, sysVal);
		} else {
			ClockConfig config = new ClockConfig();
			config.setSysKey(sysKey);
			config.setSysVal(sysVal);
			addConfig(config);
		}
	}
	
	public String getConfig(String sysKey) {
		Element e = configCache.get(sysKey);
		return null == e ? null : ((ClockConfig)e.getObjectValue()).getSysVal();
	}
	
	public void updateConfig(String sysKey, String sysVal) {
		Element e = configCache.get(sysKey);
		
		if (null != e) {
			ClockConfig config = (ClockConfig) e.getObjectValue();
			config.setSysVal(sysVal);
		}
	}
}
