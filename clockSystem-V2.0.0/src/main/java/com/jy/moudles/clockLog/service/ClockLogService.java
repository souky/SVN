package com.jy.moudles.clockLog.service;

import com.jy.moudles.clockLog.entity.ClockLog;
import java.util.List;
import java.util.Map;

/** 
 * clockLog业务接口
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
public interface ClockLogService {

	/**
	 * 新增clockLog对象
	 *
	 * @param clockLog
	 */
	public void insertClockLog(ClockLog clockLog);
	
	/**
	 * 更新clockLog对象
	 *
	 * @param clockLog
	 */
	public void updateClockLog(ClockLog clockLog);
	
	/**
	 * 根据ID获取clockLog对象
	 *
	 * @param id
	 */
	public ClockLog getClockLogById(String id);
	
	/**
	 * 根据过滤条件获取clockLog列表对象
	 *
	 * @param filter
	 */
	public List<ClockLog> queryClockLogsFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除clockLog列表对象
	 *
	 * @param id
	 */
	public void deleteClockLogById(String id);
	
	/**
	 * 根据Id集合批量删除clockLog列表对象
	 *
	 * @param ids
	 */
	public void deleteClockLogs(List<String> ids);
	
}

