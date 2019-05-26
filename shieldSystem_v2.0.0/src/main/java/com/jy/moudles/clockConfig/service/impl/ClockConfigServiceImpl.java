package com.jy.moudles.clockConfig.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.utils.SendUdpUtils;
import com.jy.listener.ClockDeviceCommandAnalysisUtils;
import com.jy.moudles.clockConfig.dao.ClockConfigDao;
import com.jy.moudles.clockConfig.entity.ClockConfig;
import com.jy.moudles.clockConfig.service.ClockConfigService;

/** 
 * ClockConfig业务实现类
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
@Service
public class ClockConfigServiceImpl implements ClockConfigService {

	@Autowired
	private ClockConfigDao ClockConfigDao;
	
	@Override
	public void insertClockConfig(ClockConfig ClockConfig){
		ClockConfigDao.insertClockConfig(ClockConfig);
	}
	
	@Override
	public void updateClockConfig(ClockConfig clockConfig){
		//更新前查看数据库是否存在数据
		String key = clockConfig.getSysKey();
		ClockConfig ClockConfigs = ClockConfigDao.getClockConfigByKey(key);
		
		if("power".equals(clockConfig.getSysKey())) {
			SendUdpUtils.sendUdpNoRecv("255.255.255.255", ClockDeviceCommandAnalysisUtils.genIsDisplayCommand("5B59674E9773", Integer.parseInt(clockConfig.getSysVal())));
		}
		
		if("showSec".equals(clockConfig.getSysKey())) {
			SendUdpUtils.sendUdpNoRecv("255.255.255.255", ClockDeviceCommandAnalysisUtils.genSecondIsDisplayCommand("5B59674E9773", Integer.parseInt(clockConfig.getSysVal())));
		}
		
		if("selfExam".equals(clockConfig.getSysKey()) && !"0".equals(clockConfig.getSysVal())) {
			SendUdpUtils.sendUdpNoRecv("255.255.255.255", ClockDeviceCommandAnalysisUtils.genSelfExamationCommand("5B59674E9773"));
		}
		
		
		if(null == ClockConfigs) {
			ClockConfigDao.insertClockConfig(clockConfig);
		}else {
			ClockConfigDao.updateClockConfig(clockConfig);
		}
	}
	
	@Override
	public ClockConfig getClockConfigByKey(String key){
		return ClockConfigDao.getClockConfigByKey(key);
	}
	
	@Override
	public List<ClockConfig> queryClockConfigsFilter(Map<String, Object> filter){
		return ClockConfigDao.queryClockConfigsFilter(filter);
	}
	
	@Override
	public void deleteClockConfigByKey(String key){
		ClockConfigDao.deleteClockConfigByKey(key);
	}
	
	@Override
	public void deleteClockConfigs(List<String> ids){
		ClockConfigDao.deleteClockConfigs(ids);
	}
	
}

