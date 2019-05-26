package com.jy.moudles.cheatFrequencyinfo.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.cheatFrequencyinfo.entity.CheatFrequencyinfo;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * CheatFrequencyinfo数据接口
 * 创建人：Administrator
 * 创建时间：2018-05-07
 */
@MyBatisDao
public interface CheatFrequencyinfoDao {

	/**
	 * 新增CheatFrequencyinfo对象
	 *
	 * @param CheatFrequencyinfo
	 */
	public void insertCheatFrequencyinfo(CheatFrequencyinfo CheatFrequencyinfo);
	
	/**
	 * 更新CheatFrequencyinfo对象
	 *
	 * @param CheatFrequencyinfo
	 */
	public void updateCheatFrequencyinfo(CheatFrequencyinfo CheatFrequencyinfo);
	
	/**
	 * 根据ID获取CheatFrequencyinfo对象
	 *
	 * @param id
	 */
	public CheatFrequencyinfo getCheatFrequencyinfoById(String id);
	
	/**
	 * 根据过滤条件获取CheatFrequencyinfo列表对象
	 *
	 * @param filter
	 */
	public List<CheatFrequencyinfo> queryCheatFrequencyinfosFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除CheatFrequencyinfo列表对象
	 *
	 * @param id
	 */
	public void deleteCheatFrequencyinfoById(String id);
	
	/**
	 * 根据Id集合批量删除CheatFrequencyinfo列表对象
	 *
	 * @param ids
	 */
	public void deleteCheatFrequencyinfos(List<String> ids);
	
}



