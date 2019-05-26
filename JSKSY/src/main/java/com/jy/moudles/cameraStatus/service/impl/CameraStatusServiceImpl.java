package com.jy.moudles.cameraStatus.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.cameraStatus.dao.CameraStatusDao;
import com.jy.moudles.cameraStatus.entity.CameraStatus;
import com.jy.moudles.cameraStatus.service.CameraStatusService;

@Service
public class CameraStatusServiceImpl implements CameraStatusService {

	@Autowired
	private CameraStatusDao CameraStatusDao;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return CameraStatusDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CameraStatus record) {
		record.setId(UUIDUtil.get32UUID());
		return CameraStatusDao.insert(record);
	}

	@Override
	public CameraStatus selectByPrimaryKey(String id) {
		return CameraStatusDao.selectByPrimaryKey(id);
	}

	@Override
	public List<CameraStatus> selectAll() {
		return CameraStatusDao.selectAll();
	}

	@Override
	public int updateByPrimaryKey(CameraStatus record) {
		return CameraStatusDao.updateByPrimaryKey(record);
	}

	@Override
	public List<CameraStatus> queryCameraStatusFilter(Map<String, Object> filter) {
		return CameraStatusDao.queryCameraStatusFilter(filter);
	}

	
}

