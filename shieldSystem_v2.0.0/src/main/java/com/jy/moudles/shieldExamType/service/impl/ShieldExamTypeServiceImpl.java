package com.jy.moudles.shieldExamType.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.shieldExamType.dao.ShieldExamTypeDao;
import com.jy.moudles.shieldExamType.entity.ShieldExamType;
import com.jy.moudles.shieldExamType.service.ShieldExamTypeService;

/** 
 * shieldExamType业务实现类
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@Service
public class ShieldExamTypeServiceImpl implements ShieldExamTypeService {

	@Autowired
	private ShieldExamTypeDao shieldExamTypeDao;
	
	@Override
	public void insertShieldExamType(ShieldExamType shieldExamType){
		shieldExamType.setId(UUIDUtil.get32UUID());
		shieldExamTypeDao.insertShieldExamType(shieldExamType);
	}
	
	@Override
	public void updateShieldExamType(ShieldExamType shieldExamType){
		shieldExamTypeDao.updateShieldExamType(shieldExamType);
	}
	
	@Override
	public ShieldExamType getShieldExamTypeById(String id){
		return shieldExamTypeDao.getShieldExamTypeById(id);
	}
	
	@Override
	public List<ShieldExamType> queryShieldExamTypesFilter(Map<String, Object> filter){
		return shieldExamTypeDao.queryShieldExamTypesFilter(filter);
	}
	
	@Override
	public void deleteShieldExamTypeById(String id){
		shieldExamTypeDao.deleteShieldExamTypeById(id);
	}
	
	@Override
	public void deleteShieldExamTypes(List<String> ids){
		shieldExamTypeDao.deleteShieldExamTypes(ids);
	}
	
}

