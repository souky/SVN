package com.jy.moudles.systemCurrency.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.systemCurrency.dao.SystemCurrencyDao;
import com.jy.moudles.systemCurrency.entity.SystemCurrency;
import com.jy.moudles.systemCurrency.service.SystemCurrencyService;

/** 
 * SystemCurrency业务实现类
 * 创建人：Administrator
 * 创建时间：2018-05-03
 */
@Service
public class SystemCurrencyServiceImpl implements SystemCurrencyService {

	@Autowired
	private SystemCurrencyDao SystemCurrencyDao;
	
	@Override
	public void insertSystemCurrency(SystemCurrency SystemCurrency){
		SystemCurrency.setId(UUIDUtil.get32UUID());
		SystemCurrencyDao.insertSystemCurrency(SystemCurrency);
	}
	
	@Override
	public void updateSystemCurrency(SystemCurrency SystemCurrency){
		SystemCurrencyDao.updateSystemCurrency(SystemCurrency);
	}
	
	@Override
	public SystemCurrency getSystemCurrencyById(String id){
		return SystemCurrencyDao.getSystemCurrencyById(id);
	}
	
	@Override
	public List<SystemCurrency> querySystemCurrencysFilter(Map<String, Object> filter){
		return SystemCurrencyDao.querySystemCurrencysFilter(filter);
	}
	
	@Override
	public void deleteSystemCurrencyById(String id){
		SystemCurrencyDao.deleteSystemCurrencyById(id);
	}
	
	@Override
	public void deleteSystemCurrencys(List<String> ids){
		SystemCurrencyDao.deleteSystemCurrencys(ids);
	}
	
}

