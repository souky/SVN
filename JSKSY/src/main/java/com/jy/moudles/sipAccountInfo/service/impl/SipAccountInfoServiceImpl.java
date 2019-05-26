package com.jy.moudles.sipAccountInfo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.sipAccountInfo.dao.SipAccountInfoDao;
import com.jy.moudles.sipAccountInfo.entity.SipAccountInfo;
import com.jy.moudles.sipAccountInfo.service.SipAccountInfoService;

/** 
 * SipAccountInfo业务实现类
 * 创建人：Administrator
 * 创建时间：2018-05-04
 */
@Service
public class SipAccountInfoServiceImpl implements SipAccountInfoService {

	@Autowired
	private SipAccountInfoDao SipAccountInfoDao;
	
	@Override
	public void insertSipAccountInfo(SipAccountInfo SipAccountInfo){
		SipAccountInfo.setId(UUIDUtil.get32UUID());
		SipAccountInfoDao.insertSipAccountInfo(SipAccountInfo);
	}
	
	@Override
	public void updateSipAccountInfo(SipAccountInfo SipAccountInfo){
		SipAccountInfoDao.updateSipAccountInfo(SipAccountInfo);
	}
	
	@Override
	public SipAccountInfo getSipAccountInfoById(String id){
		return SipAccountInfoDao.getSipAccountInfoById(id);
	}
	
	@Override
	public List<SipAccountInfo> querySipAccountInfosFilter(Map<String, Object> filter){
		return SipAccountInfoDao.querySipAccountInfosFilter(filter);
	}
	
	@Override
	public void deleteSipAccountInfoById(String id){
		SipAccountInfoDao.deleteSipAccountInfoById(id);
	}
	
	@Override
	public void deleteSipAccountInfos(){
		SipAccountInfoDao.deleteSipAccountInfos();
	}

	@Override
	public void insertSipAccountInfos(List<SipAccountInfo> SipAccountInfos) {
		for(SipAccountInfo s : SipAccountInfos) {
			s.setId(UUIDUtil.get32UUID());
		}
		SipAccountInfoDao.insertSipAccountInfos(SipAccountInfos);
	}
	
}

