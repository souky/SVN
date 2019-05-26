package com.jy.moudles.cheatFrequencyinfo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.cheatFrequencyinfo.dao.CheatFrequencyinfoDao;
import com.jy.moudles.cheatFrequencyinfo.entity.CheatFrequencyinfo;
import com.jy.moudles.cheatFrequencyinfo.service.CheatFrequencyinfoService;

/** 
 * CheatFrequencyinfo业务实现类
 * 创建人：Administrator
 * 创建时间：2018-05-07
 */
@Service
public class CheatFrequencyinfoServiceImpl implements CheatFrequencyinfoService {

	@Autowired
	private CheatFrequencyinfoDao CheatFrequencyinfoDao;
	
	@Override
	public void insertCheatFrequencyinfo(CheatFrequencyinfo CheatFrequencyinfo){
		CheatFrequencyinfo.setId(UUIDUtil.get32UUID());
		CheatFrequencyinfoDao.insertCheatFrequencyinfo(CheatFrequencyinfo);
	}
	
	@Override
	public void updateCheatFrequencyinfo(CheatFrequencyinfo CheatFrequencyinfo){
		CheatFrequencyinfoDao.updateCheatFrequencyinfo(CheatFrequencyinfo);
	}
	
	@Override
	public CheatFrequencyinfo getCheatFrequencyinfoById(String id){
		return CheatFrequencyinfoDao.getCheatFrequencyinfoById(id);
	}
	
	@Override
	public List<CheatFrequencyinfo> queryCheatFrequencyinfosFilter(Map<String, Object> filter){
		return CheatFrequencyinfoDao.queryCheatFrequencyinfosFilter(filter);
	}
	
	@Override
	public void deleteCheatFrequencyinfoById(String id){
		CheatFrequencyinfoDao.deleteCheatFrequencyinfoById(id);
	}
	
	@Override
	public void deleteCheatFrequencyinfos(List<String> ids){
		CheatFrequencyinfoDao.deleteCheatFrequencyinfos(ids);
	}
	
}

