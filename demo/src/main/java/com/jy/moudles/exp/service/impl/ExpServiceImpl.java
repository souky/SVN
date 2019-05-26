package com.jy.moudles.exp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.exp.dao.ExpDao;
import com.jy.moudles.exp.entity.Exp;
import com.jy.moudles.exp.service.ExpService;

/** 
 * Exp业务实现类
 * 创建人：Administrator
 * 创建时间：2018-11-09
 */
@Service
public class ExpServiceImpl implements ExpService {

	@Autowired
	private ExpDao ExpDao;
	
	@Override
	public void insertExp(Exp Exp){
		Exp.setId(UUIDUtil.get32UUID());
		ExpDao.insertExp(Exp);
	}
	
	@Override
	public void updateExp(Exp Exp){
		ExpDao.updateExp(Exp);
	}
	
	@Override
	public Exp getExpById(String id){
		return ExpDao.getExpById(id);
	}
	
	@Override
	public List<Exp> queryExpsFilter(Map<String, Object> filter){
		return ExpDao.queryExpsFilter(filter);
	}
	
	@Override
	public void deleteExpById(String id){
		ExpDao.deleteExpById(id);
	}
	
	@Override
	public void deleteExps(List<String> ids){
		ExpDao.deleteExps(ids);
	}
	
}

