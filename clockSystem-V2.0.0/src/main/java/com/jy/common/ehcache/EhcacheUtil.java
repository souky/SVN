package com.jy.common.ehcache;

import com.jy.common.springapplication.SpringContextHolder;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheUtil {

	private static final String appointPath = "cacheManager";

	private CacheManager cacheManager;

	private static EhcacheUtil ehcacheUtil;

	public EhcacheUtil(String appointPath) {
		cacheManager = ((CacheManager)SpringContextHolder.getBean(appointPath));
	}

	public static EhcacheUtil getInstance() {
		if (null == ehcacheUtil) {
			ehcacheUtil = new EhcacheUtil(appointPath);
		}

		return ehcacheUtil;
	}

	public Cache getCache(String cacheName) {
		return cacheManager.getCache(cacheName);
	}

	public void put(String cacheName, Object key, Object value) {
		Cache cache = this.getCache(cacheName);
		if (null == cache) {
			cache = new Cache(cacheName, 10000000, true, true, 0, 0);
			cacheManager.addCache(cache);
		}
		Element e = new Element(key, value);
		cache.put(e);
	}

	public void put(Object key, Object value) {

		Cache cache = this.getCache("logCache");

		Element e = new Element(key, value);
		cache.put(e);
	}

	public Object getValue(String cacheName, Object key) {
		Cache cache = this.getCache(cacheName);
		if (null != cache) {
			Element e = cache.get(key);
			return null == e ? null : e.getObjectValue();
		}
		return null;
	}

	public Object getValue(Object key) {
		Cache cache = this.getCache("logCache");
		if (null != cache) {
			Element e = cache.get(key);
			return null == e ? null : e.getObjectValue();
		}
		return null;
	}

	public void removeElement(String cacheName, Object key) {
		Cache cache = this.getCache(cacheName);
		if (null != cache) {
			cache.remove(key);
		}
	}

	public void removeCache(String cacheName) {
		Cache cache = this.getCache(cacheName);
		if (null != cache) {
			cacheManager.removeCache(cacheName);
		}
	}
}
