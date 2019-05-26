package com.jy.moudles.shieldExamPlan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.shieldExamPlan.dao.ShieldExamPlanDao;
import com.jy.moudles.shieldExamPlan.entity.ShieldExamPlan;
import com.jy.moudles.shieldExamPlan.service.ShieldExamPlanService;

/** 
 * shieldExamPlan业务实现类
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@Service
public class ShieldExamPlanServiceImpl implements ShieldExamPlanService {

	@Autowired
	private ShieldExamPlanDao shieldExamPlanDao;
	
	@Override
	public void insertShieldExamPlan(ShieldExamPlan shieldExamPlan){
		shieldExamPlan.setId(UUIDUtil.get32UUID());
		shieldExamPlanDao.insertShieldExamPlan(shieldExamPlan);
	}
	
	@Override
	public void updateShieldExamPlan(ShieldExamPlan shieldExamPlan){
		shieldExamPlanDao.updateShieldExamPlan(shieldExamPlan);
	}
	
	@Override
	public ShieldExamPlan getShieldExamPlanById(String id){
		return shieldExamPlanDao.getShieldExamPlanById(id);
	}
	
	@Override
	public List<ShieldExamPlan> queryShieldExamPlansFilter(Map<String, Object> filter){
		return shieldExamPlanDao.queryShieldExamPlansFilter(filter);
	}
	
	@Override
	public void deleteShieldExamPlanById(String id){
		shieldExamPlanDao.deleteShieldExamPlanById(id);
	}
	
	@Override
	public void deleteShieldExamPlans(List<String> ids){
		shieldExamPlanDao.deleteShieldExamPlans(ids);
	}
	
}

