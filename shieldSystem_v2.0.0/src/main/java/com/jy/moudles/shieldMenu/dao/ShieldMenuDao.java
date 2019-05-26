package com.jy.moudles.shieldMenu.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.shieldMenu.entity.ShieldMenu;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * shieldMenu数据接口
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@MyBatisDao
public interface ShieldMenuDao {

	/**
	 * 新增shieldMenu对象
	 *
	 * @param shieldMenu
	 */
	public void insertShieldMenu(ShieldMenu shieldMenu);
	
	/**
	 * 更新shieldMenu对象
	 *
	 * @param shieldMenu
	 */
	public void updateShieldMenu(ShieldMenu shieldMenu);
	
	/**
	 * 根据ID获取shieldMenu对象
	 *
	 * @param id
	 */
	public ShieldMenu getShieldMenuById(String id);
	
	/**
	 * 根据过滤条件获取shieldMenu列表对象
	 *
	 * @param filter
	 */
	public List<ShieldMenu> queryShieldMenusFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除shieldMenu列表对象
	 *
	 * @param id
	 */
	public void deleteShieldMenuById(String id);
	
	/**
	 * 根据Id集合批量删除shieldMenu列表对象
	 *
	 * @param ids
	 */
	public void deleteShieldMenus(List<String> ids);
	
}



