package com.jy.moudles.systemCurrency.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.systemCurrency.entity.SystemCurrency;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * SystemCurrency数据接口
 * 创建人：Administrator
 * 创建时间：2018-05-03
 */
@MyBatisDao
public interface SystemCurrencyDao {

	/**
	 * 新增SystemCurrency对象
	 *
	 * @param SystemCurrency
	 */
	public void insertSystemCurrency(SystemCurrency SystemCurrency);
	
	/**
	 * 更新SystemCurrency对象
	 *
	 * @param SystemCurrency
	 */
	public void updateSystemCurrency(SystemCurrency SystemCurrency);
	
	/**
	 * 根据ID获取SystemCurrency对象
	 *
	 * @param id
	 */
	public SystemCurrency getSystemCurrencyById(String id);
	
	/**
	 * 根据过滤条件获取SystemCurrency列表对象
	 *
	 * @param filter
	 */
	public List<SystemCurrency> querySystemCurrencysFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除SystemCurrency列表对象
	 *
	 * @param id
	 */
	public void deleteSystemCurrencyById(String id);
	
	/**
	 * 根据Id集合批量删除SystemCurrency列表对象
	 *
	 * @param ids
	 */
	public void deleteSystemCurrencys(List<String> ids);
	
}



