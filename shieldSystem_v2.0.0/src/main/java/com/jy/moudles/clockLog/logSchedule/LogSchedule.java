package com.jy.moudles.clockLog.logSchedule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jy.moudles.clockLog.entity.ClockLog;
import com.jy.moudles.clockLog.service.ClockLogService;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;


@Component
public class LogSchedule implements ApplicationListener<ContextRefreshedEvent> {
	
	private CacheManager cacheManager;
	
	@Autowired
	private ClockLogService logService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LogSchedule.class);
	
	@Scheduled(fixedDelay = 10000)
	public void logInsertSchedule () {
		LOGGER.debug("写入日志服务");
		if (null != cacheManager) {
			Cache cache = cacheManager.getCache("logCache");
			
			if (null != cache) {
				List<?> keys = cache.getKeys();
				
				for (Object key : keys) {
					Element e = cache.get(key);
					
					ClockLog log = ((null == e) ? null : (ClockLog)e.getObjectValue());
					if(null != log) {
						logService.insertClockLog(log);
					}
				}
				cache.removeAll();
			}
		}
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (null != event.getApplicationContext().getParent()) {
			cacheManager = (CacheManager) event.getApplicationContext().getBean("cacheManagerFactory");
		}
	}

	
}
