package com.jy.moudles.sipStatus.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.sipStatus.dao.SipStatusDao;
import com.jy.moudles.sipStatus.entity.SipStatus;
import com.jy.moudles.sipStatus.service.SipStatusService;

@Service
public class SipStatusServiceImpl implements SipStatusService {

	@Autowired
	private SipStatusDao SipStatusDao;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return SipStatusDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SipStatus record) {
		record.setId(UUIDUtil.get32UUID());
		return SipStatusDao.insert(record);
	}

	@Override
	public SipStatus selectByPrimaryKey(String id) {
		return SipStatusDao.selectByPrimaryKey(id);
	}

	@Override
	public List<SipStatus> selectAll() {
		return SipStatusDao.selectAll();
	}

	@Override
	public int updateByPrimaryKey(SipStatus record) {
		return SipStatusDao.updateByPrimaryKey(record);
	}
	
	@Override
	public List<SipStatus> querySipStatusFilter(Map<String, Object> filter){
		return SipStatusDao.querySipStatusFilter(filter);
	}
}

