package com.jy.moudles.videoPlanStatus.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.videoPlanStatus.dao.VideoPlanStatusDao;
import com.jy.moudles.videoPlanStatus.entity.VideoPlanStatus;
import com.jy.moudles.videoPlanStatus.service.VideoPlanStatusService;

@Service
public class VideoPlanStatusServiceImpl implements VideoPlanStatusService {

	@Autowired
	private VideoPlanStatusDao VideoPlanStatusDao;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return VideoPlanStatusDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(VideoPlanStatus record) {
		record.setId(UUIDUtil.get32UUID());
		return VideoPlanStatusDao.insert(record);
	}

	@Override
	public VideoPlanStatus selectByPrimaryKey(String id) {
		return VideoPlanStatusDao.selectByPrimaryKey(id);
	}

	@Override
	public List<VideoPlanStatus> selectAll() {
		return VideoPlanStatusDao.selectAll();
	}

	@Override
	public int updateByPrimaryKey(VideoPlanStatus record) {
		return VideoPlanStatusDao.updateByPrimaryKey(record);
	}

	@Override
	public List<VideoPlanStatus> queryVideoPlanStatusFilter(Map<String, Object> filter) {
		return VideoPlanStatusDao.queryVideoPlanStatusFilter(filter);
	}

	
}

