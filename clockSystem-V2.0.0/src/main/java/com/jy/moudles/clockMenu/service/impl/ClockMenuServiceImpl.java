package com.jy.moudles.clockMenu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.clockMenu.dao.ClockMenuDao;
import com.jy.moudles.clockMenu.entity.ClockMenu;
import com.jy.moudles.clockMenu.service.ClockMenuService;

/** 
 * clockMenu业务实现类
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
@Service
public class ClockMenuServiceImpl implements ClockMenuService {

	@Autowired
	private ClockMenuDao clockMenuDao;
	
	@Override
	public void insertClockMenu(ClockMenu clockMenu){
		clockMenu.setId(UUIDUtil.get32UUID());
		clockMenuDao.insertClockMenu(clockMenu);
	}
	
	@Override
	public void updateClockMenu(ClockMenu clockMenu){
		clockMenuDao.updateClockMenu(clockMenu);
	}
	
	@Override
	public ClockMenu getClockMenuById(String id){
		return clockMenuDao.getClockMenuById(id);
	}
	
	@Override
	public List<ClockMenu> queryClockMenusFilter(Map<String, Object> filter){
		return clockMenuDao.queryClockMenusFilter(filter);
	}
	
	@Override
	public void deleteClockMenuById(String id){
		clockMenuDao.deleteClockMenuById(id);
	}
	
	@Override
	public void deleteClockMenus(List<String> ids){
		clockMenuDao.deleteClockMenus(ids);
	}
	
}

