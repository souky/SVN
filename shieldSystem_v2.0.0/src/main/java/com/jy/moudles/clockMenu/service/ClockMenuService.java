package com.jy.moudles.clockMenu.service;

import com.jy.moudles.clockMenu.entity.ClockMenu;
import java.util.List;
import java.util.Map;

/** 
 * clockMenu业务接口
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
public interface ClockMenuService {

	/**
	 * 新增clockMenu对象
	 *
	 * @param clockMenu
	 */
	public void insertClockMenu(ClockMenu clockMenu);
	
	/**
	 * 更新clockMenu对象
	 *
	 * @param clockMenu
	 */
	public void updateClockMenu(ClockMenu clockMenu);
	
	/**
	 * 根据ID获取clockMenu对象
	 *
	 * @param id
	 */
	public ClockMenu getClockMenuById(String id);
	
	/**
	 * 根据过滤条件获取clockMenu列表对象
	 *
	 * @param filter
	 */
	public List<ClockMenu> queryClockMenusFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除clockMenu列表对象
	 *
	 * @param id
	 */
	public void deleteClockMenuById(String id);
	
	/**
	 * 根据Id集合批量删除clockMenu列表对象
	 *
	 * @param ids
	 */
	public void deleteClockMenus(List<String> ids);
	
}

