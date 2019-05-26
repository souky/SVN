package com.jy.moudles.shieldMenu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.shieldMenu.dao.ShieldMenuDao;
import com.jy.moudles.shieldMenu.entity.ShieldMenu;
import com.jy.moudles.shieldMenu.service.ShieldMenuService;

/** 
 * shieldMenu业务实现类
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@Service
public class ShieldMenuServiceImpl implements ShieldMenuService {

	@Autowired
	private ShieldMenuDao shieldMenuDao;
	
	@Override
	public void insertShieldMenu(ShieldMenu shieldMenu){
		shieldMenu.setId(UUIDUtil.get32UUID());
		shieldMenuDao.insertShieldMenu(shieldMenu);
	}
	
	@Override
	public void updateShieldMenu(ShieldMenu shieldMenu){
		shieldMenuDao.updateShieldMenu(shieldMenu);
	}
	
	@Override
	public ShieldMenu getShieldMenuById(String id){
		return shieldMenuDao.getShieldMenuById(id);
	}
	
	@Override
	public List<ShieldMenu> queryShieldMenusFilter(Map<String, Object> filter){
		return shieldMenuDao.queryShieldMenusFilter(filter);
	}
	
	@Override
	public void deleteShieldMenuById(String id){
		shieldMenuDao.deleteShieldMenuById(id);
	}
	
	@Override
	public void deleteShieldMenus(List<String> ids){
		shieldMenuDao.deleteShieldMenus(ids);
	}
	
}

