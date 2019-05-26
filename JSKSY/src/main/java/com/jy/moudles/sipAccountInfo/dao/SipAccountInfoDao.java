package com.jy.moudles.sipAccountInfo.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.sipAccountInfo.entity.SipAccountInfo;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * SipAccountInfo数据接口
 * 创建人：Administrator
 * 创建时间：2018-05-04
 */
@MyBatisDao
public interface SipAccountInfoDao {

	/**
	 * 新增SipAccountInfo对象
	 *
	 * @param SipAccountInfo
	 */
	public void insertSipAccountInfo(SipAccountInfo SipAccountInfo);
	
	/**
	 * 更新SipAccountInfo对象
	 *
	 * @param SipAccountInfo
	 */
	public void updateSipAccountInfo(SipAccountInfo SipAccountInfo);
	
	/**
	 * 根据ID获取SipAccountInfo对象
	 *
	 * @param id
	 */
	public SipAccountInfo getSipAccountInfoById(String id);
	
	/**
	 * 根据过滤条件获取SipAccountInfo列表对象
	 *
	 * @param filter
	 */
	public List<SipAccountInfo> querySipAccountInfosFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除SipAccountInfo列表对象
	 *
	 * @param id
	 */
	public void deleteSipAccountInfoById(String id);
	
	/**
	 * 根据Id集合批量删除SipAccountInfo列表对象
	 *
	 * @param ids
	 */
	public void deleteSipAccountInfos();
	
	/**
	 * 批量新增SipAccountInfo对象
	 *
	 * @param SipAccountInfo
	 */
	public void insertSipAccountInfos(List<SipAccountInfo> SipAccountInfos);
	
	
}



