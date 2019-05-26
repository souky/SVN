package com.jy.moudles.shieldConfig.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.shieldConfig.dao.ShieldConfigDao;
import com.jy.moudles.shieldConfig.entity.ShieldConfig;
import com.jy.moudles.shieldConfig.service.ShieldConfigService;

/** 
 * ShieldConfig业务实现类
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@Service
public class ShieldConfigServiceImpl implements ShieldConfigService {

	@Autowired
	private ShieldConfigDao ShieldConfigDao;
	
	@Override
	public void insertShieldConfig(ShieldConfig ShieldConfig){
		ShieldConfig.setId(UUIDUtil.get32UUID());
		ShieldConfigDao.insertShieldConfig(ShieldConfig);
	}
	
	@Override
	public void updateShieldConfig(ShieldConfig ShieldConfig){
		ShieldConfigDao.updateShieldConfig(ShieldConfig);
	}
	
	@Override
	public ShieldConfig getShieldConfigById(String id){
		return ShieldConfigDao.getShieldConfigById(id);
	}
	
	@Override
	public List<ShieldConfig> queryShieldConfigsFilter(Map<String, Object> filter){
		return ShieldConfigDao.queryShieldConfigsFilter(filter);
	}
	
	@Override
	public void deleteShieldConfigById(String id){
		ShieldConfigDao.deleteShieldConfigById(id);
	}
	
	@Override
	public void deleteShieldConfigs(List<String> ids){
		ShieldConfigDao.deleteShieldConfigs(ids);
	}
	
}

